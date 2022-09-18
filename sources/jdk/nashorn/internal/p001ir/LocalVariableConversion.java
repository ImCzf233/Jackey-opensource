package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;

/* renamed from: jdk.nashorn.internal.ir.LocalVariableConversion */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/LocalVariableConversion.class */
public final class LocalVariableConversion {
    private final Symbol symbol;
    private final Type from;

    /* renamed from: to */
    private final Type f236to;
    private final LocalVariableConversion next;

    public LocalVariableConversion(Symbol symbol, Type from, Type to, LocalVariableConversion next) {
        this.symbol = symbol;
        this.from = from;
        this.f236to = to;
        this.next = next;
    }

    public Type getFrom() {
        return this.from;
    }

    public Type getTo() {
        return this.f236to;
    }

    public LocalVariableConversion getNext() {
        return this.next;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public boolean isLive() {
        return this.symbol.hasSlotFor(this.f236to);
    }

    public boolean isAnyLive() {
        return isLive() || isAnyLive(this.next);
    }

    public static boolean hasLiveConversion(JoinPredecessor jp) {
        return isAnyLive(jp.getLocalVariableConversion());
    }

    private static boolean isAnyLive(LocalVariableConversion conv) {
        return conv != null && conv.isAnyLive();
    }

    public String toString() {
        return toString(new StringBuilder()).toString();
    }

    public StringBuilder toString(StringBuilder sb) {
        if (isLive()) {
            return toStringNext(sb.append((char) 10214), true).append("⟧ ");
        }
        return this.next == null ? sb : this.next.toString(sb);
    }

    public static StringBuilder toString(LocalVariableConversion conv, StringBuilder sb) {
        return conv == null ? sb : conv.toString(sb);
    }

    private StringBuilder toStringNext(StringBuilder sb, boolean first) {
        if (!isLive()) {
            return this.next == null ? sb : this.next.toStringNext(sb, first);
        }
        if (!first) {
            sb.append(", ");
        }
        sb.append(this.symbol.getName()).append(':').append(getTypeChar(this.from)).append((char) 8594).append(getTypeChar(this.f236to));
        return this.next == null ? sb : this.next.toStringNext(sb, false);
    }

    private static char getTypeChar(Type type) {
        if (type == Type.UNDEFINED) {
            return 'U';
        }
        if (type.isObject()) {
            return 'O';
        }
        if (type == Type.BOOLEAN) {
            return 'Z';
        }
        return type.getBytecodeStackType();
    }
}
