package jdk.nashorn.internal.p001ir;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.options.Options;

/* renamed from: jdk.nashorn.internal.ir.Symbol */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/Symbol.class */
public final class Symbol implements Comparable<Symbol>, Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    public static final int IS_GLOBAL = 1;
    public static final int IS_VAR = 2;
    public static final int IS_PARAM = 3;
    public static final int KINDMASK = 3;
    public static final int IS_SCOPE = 4;
    public static final int IS_THIS = 8;
    public static final int IS_LET = 16;
    public static final int IS_CONST = 32;
    public static final int IS_INTERNAL = 64;
    public static final int IS_FUNCTION_SELF = 128;
    public static final int IS_FUNCTION_DECLARATION = 256;
    public static final int IS_PROGRAM_LEVEL = 512;
    public static final int HAS_SLOT = 1024;
    public static final int HAS_INT_VALUE = 2048;
    public static final int HAS_DOUBLE_VALUE = 4096;
    public static final int HAS_OBJECT_VALUE = 8192;
    public static final int HAS_BEEN_DECLARED = 16384;
    private final String name;
    private int flags;
    private transient int firstSlot = -1;
    private transient int fieldIndex = -1;
    private int useCount;
    private static final Set<String> TRACE_SYMBOLS;
    private static final Set<String> TRACE_SYMBOLS_STACKTRACE;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        String trace;
        $assertionsDisabled = !Symbol.class.desiredAssertionStatus();
        String stacktrace = Options.getStringProperty("nashorn.compiler.symbol.stacktrace", null);
        if (stacktrace != null) {
            trace = stacktrace;
            TRACE_SYMBOLS_STACKTRACE = new HashSet();
            StringTokenizer st = new StringTokenizer(stacktrace, ",");
            while (st.hasMoreTokens()) {
                TRACE_SYMBOLS_STACKTRACE.add(st.nextToken());
            }
        } else {
            trace = Options.getStringProperty("nashorn.compiler.symbol.trace", null);
            TRACE_SYMBOLS_STACKTRACE = null;
        }
        if (trace != null) {
            TRACE_SYMBOLS = new HashSet();
            StringTokenizer st2 = new StringTokenizer(trace, ",");
            while (st2.hasMoreTokens()) {
                TRACE_SYMBOLS.add(st2.nextToken());
            }
            return;
        }
        TRACE_SYMBOLS = null;
    }

    public Symbol(String name, int flags) {
        this.name = name;
        this.flags = flags;
        if (shouldTrace()) {
            trace("CREATE SYMBOL " + name);
        }
    }

    public Symbol clone() {
        try {
            return (Symbol) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    private static String align(String string, int max) {
        StringBuilder sb = new StringBuilder();
        sb.append(string.substring(0, Math.min(string.length(), max)));
        while (sb.length() < max) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public void print(PrintWriter stream) {
        StringBuilder sb = new StringBuilder();
        sb.append(align(this.name, 20)).append(": ").append(", ").append(align(this.firstSlot == -1 ? "none" : "" + this.firstSlot, 10));
        switch (this.flags & 3) {
            case 1:
                sb.append(" global");
                break;
            case 2:
                if (isConst()) {
                    sb.append(" const");
                    break;
                } else if (isLet()) {
                    sb.append(" let");
                    break;
                } else {
                    sb.append(" var");
                    break;
                }
            case 3:
                sb.append(" param");
                break;
        }
        if (isScope()) {
            sb.append(" scope");
        }
        if (isInternal()) {
            sb.append(" internal");
        }
        if (isThis()) {
            sb.append(" this");
        }
        if (isProgramLevel()) {
            sb.append(" program");
        }
        sb.append('\n');
        stream.print(sb.toString());
    }

    public boolean less(int other) {
        return (this.flags & 3) < (other & 3);
    }

    public Symbol setNeedsSlot(boolean needsSlot) {
        if (needsSlot) {
            if (!$assertionsDisabled && isScope()) {
                throw new AssertionError();
            }
            this.flags |= 1024;
        } else {
            this.flags &= -1025;
        }
        return this;
    }

    public int slotCount() {
        return ((this.flags & 2048) == 0 ? 0 : 1) + ((this.flags & 4096) == 0 ? 0 : 2) + ((this.flags & 8192) == 0 ? 0 : 1);
    }

    private boolean isSlotted() {
        return (this.firstSlot == -1 || (this.flags & 1024) == 0) ? false : true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append(' ');
        if (hasSlot()) {
            sb.append(' ').append('(').append("slot=").append(this.firstSlot).append(' ');
            if ((this.flags & 2048) != 0) {
                sb.append('I');
            }
            if ((this.flags & 4096) != 0) {
                sb.append('D');
            }
            if ((this.flags & 8192) != 0) {
                sb.append('O');
            }
            sb.append(')');
        }
        if (isScope()) {
            if (isGlobal()) {
                sb.append(" G");
            } else {
                sb.append(" S");
            }
        }
        return sb.toString();
    }

    public int compareTo(Symbol other) {
        return this.name.compareTo(other.name);
    }

    public boolean hasSlot() {
        return (this.flags & 1024) != 0;
    }

    public boolean isBytecodeLocal() {
        return hasSlot() && !isScope();
    }

    public boolean isDead() {
        return (this.flags & 1028) == 0;
    }

    public boolean isScope() {
        if ($assertionsDisabled || (this.flags & 3) != 1 || (this.flags & 4) == 4) {
            return (this.flags & 4) != 0;
        }
        throw new AssertionError("global without scope flag");
    }

    public boolean isFunctionDeclaration() {
        return (this.flags & 256) != 0;
    }

    public Symbol setIsScope() {
        if (!isScope()) {
            if (shouldTrace()) {
                trace("SET IS SCOPE");
            }
            this.flags |= 4;
            if (!isParam()) {
                this.flags &= -1025;
            }
        }
        return this;
    }

    public void setIsFunctionDeclaration() {
        if (!isFunctionDeclaration()) {
            if (shouldTrace()) {
                trace("SET IS FUNCTION DECLARATION");
            }
            this.flags |= 256;
        }
    }

    public boolean isVar() {
        return (this.flags & 3) == 2;
    }

    public boolean isGlobal() {
        return (this.flags & 3) == 1;
    }

    public boolean isParam() {
        return (this.flags & 3) == 3;
    }

    public boolean isProgramLevel() {
        return (this.flags & 512) != 0;
    }

    public boolean isConst() {
        return (this.flags & 32) != 0;
    }

    public boolean isInternal() {
        return (this.flags & 64) != 0;
    }

    public boolean isThis() {
        return (this.flags & 8) != 0;
    }

    public boolean isLet() {
        return (this.flags & 16) != 0;
    }

    public boolean isFunctionSelf() {
        return (this.flags & 128) != 0;
    }

    public boolean isBlockScoped() {
        return isLet() || isConst();
    }

    public boolean hasBeenDeclared() {
        return (this.flags & 16384) != 0;
    }

    public void setHasBeenDeclared() {
        if (!hasBeenDeclared()) {
            this.flags |= 16384;
        }
    }

    public int getFieldIndex() {
        if ($assertionsDisabled || this.fieldIndex != -1) {
            return this.fieldIndex;
        }
        throw new AssertionError("fieldIndex must be initialized " + this.fieldIndex);
    }

    public Symbol setFieldIndex(int fieldIndex) {
        if (this.fieldIndex != fieldIndex) {
            this.fieldIndex = fieldIndex;
        }
        return this;
    }

    public int getFlags() {
        return this.flags;
    }

    public Symbol setFlags(int flags) {
        if (this.flags != flags) {
            this.flags = flags;
        }
        return this;
    }

    public Symbol setFlag(int flag) {
        if ((this.flags & flag) == 0) {
            this.flags |= flag;
        }
        return this;
    }

    public Symbol clearFlag(int flag) {
        if ((this.flags & flag) != 0) {
            this.flags &= flag ^ (-1);
        }
        return this;
    }

    public String getName() {
        return this.name;
    }

    public int getFirstSlot() {
        if ($assertionsDisabled || isSlotted()) {
            return this.firstSlot;
        }
        throw new AssertionError();
    }

    public int getSlot(Type type) {
        if ($assertionsDisabled || isSlotted()) {
            int typeSlot = this.firstSlot;
            if (type.isBoolean() || type.isInteger()) {
                if (!$assertionsDisabled && (this.flags & 2048) == 0) {
                    throw new AssertionError();
                }
                return typeSlot;
            }
            int typeSlot2 = typeSlot + ((this.flags & 2048) == 0 ? 0 : 1);
            if (type.isNumber()) {
                if (!$assertionsDisabled && (this.flags & 4096) == 0) {
                    throw new AssertionError();
                }
                return typeSlot2;
            } else if (!$assertionsDisabled && !type.isObject()) {
                throw new AssertionError();
            } else {
                if (!$assertionsDisabled && (this.flags & 8192) == 0) {
                    throw new AssertionError(this.name);
                }
                return typeSlot2 + ((this.flags & 4096) == 0 ? 0 : 2);
            }
        }
        throw new AssertionError();
    }

    public boolean hasSlotFor(Type type) {
        if (type.isBoolean() || type.isInteger()) {
            return (this.flags & 2048) != 0;
        } else if (type.isNumber()) {
            return (this.flags & 4096) != 0;
        } else if (!$assertionsDisabled && !type.isObject()) {
            throw new AssertionError();
        } else {
            return (this.flags & 8192) != 0;
        }
    }

    public void setHasSlotFor(Type type) {
        if (type.isBoolean() || type.isInteger()) {
            setFlag(2048);
        } else if (type.isNumber()) {
            setFlag(4096);
        } else if (!$assertionsDisabled && !type.isObject()) {
            throw new AssertionError();
        } else {
            setFlag(8192);
        }
    }

    public void increaseUseCount() {
        if (isScope()) {
            this.useCount++;
        }
    }

    public int getUseCount() {
        return this.useCount;
    }

    public Symbol setFirstSlot(int firstSlot) {
        if ($assertionsDisabled || (firstSlot >= 0 && firstSlot <= 65535)) {
            if (firstSlot != this.firstSlot) {
                if (shouldTrace()) {
                    trace("SET SLOT " + firstSlot);
                }
                this.firstSlot = firstSlot;
            }
            return this;
        }
        throw new AssertionError();
    }

    public static Symbol setSymbolIsScope(LexicalContext lc, Symbol symbol) {
        symbol.setIsScope();
        if (!symbol.isGlobal()) {
            lc.setBlockNeedsScope(lc.getDefiningBlock(symbol));
        }
        return symbol;
    }

    private boolean shouldTrace() {
        return TRACE_SYMBOLS != null && (TRACE_SYMBOLS.isEmpty() || TRACE_SYMBOLS.contains(this.name));
    }

    private void trace(String desc) {
        Context.err(Debug.m67id(this) + " SYMBOL: '" + this.name + "' " + desc);
        if (TRACE_SYMBOLS_STACKTRACE != null) {
            if (TRACE_SYMBOLS_STACKTRACE.isEmpty() || TRACE_SYMBOLS_STACKTRACE.contains(this.name)) {
                new Throwable().printStackTrace(Context.getCurrentErr());
            }
        }
    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.firstSlot = -1;
        this.fieldIndex = -1;
    }
}
