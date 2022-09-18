package jdk.nashorn.internal.codegen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import jdk.nashorn.internal.codegen.types.Type;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/Label.class */
public final class Label implements Serializable {
    private static final long serialVersionUID = 1;
    private static int nextId;
    private final String name;
    private transient Stack stack;
    private transient jdk.internal.org.objectweb.asm.Label label;

    /* renamed from: id */
    private final int f231id;
    private transient boolean reachable;
    private transient boolean breakTarget;
    private String str;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Label.class.desiredAssertionStatus();
        nextId = 0;
    }

    /* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/Label$Stack.class */
    public static final class Stack implements Cloneable {
        static final int NON_LOAD = -1;

        /* renamed from: sp */
        int f232sp;
        int firstTemp;
        static final /* synthetic */ boolean $assertionsDisabled;
        Type[] data = new Type[8];
        int[] localLoads = new int[8];
        List<Type> localVariableTypes = new ArrayList(8);
        BitSet symbolBoundary = new BitSet();

        static {
            $assertionsDisabled = !Label.class.desiredAssertionStatus();
        }

        public boolean isEmpty() {
            return this.f232sp == 0;
        }

        public int size() {
            return this.f232sp;
        }

        void clear() {
            this.f232sp = 0;
        }

        public void push(Type type) {
            if (this.data.length == this.f232sp) {
                Type[] newData = new Type[this.f232sp * 2];
                int[] newLocalLoad = new int[this.f232sp * 2];
                System.arraycopy(this.data, 0, newData, 0, this.f232sp);
                System.arraycopy(this.localLoads, 0, newLocalLoad, 0, this.f232sp);
                this.data = newData;
                this.localLoads = newLocalLoad;
            }
            this.data[this.f232sp] = type;
            this.localLoads[this.f232sp] = -1;
            this.f232sp++;
        }

        public Type peek() {
            return peek(0);
        }

        public Type peek(int n) {
            int pos = (this.f232sp - 1) - n;
            if (pos < 0) {
                return null;
            }
            return this.data[pos];
        }

        public Type[] getTopTypes(int count) {
            Type[] topTypes = new Type[count];
            System.arraycopy(this.data, this.f232sp - count, topTypes, 0, count);
            return topTypes;
        }

        public int[] getLocalLoads(int from, int to) {
            int count = to - from;
            int[] topLocalLoads = new int[count];
            System.arraycopy(this.localLoads, from, topLocalLoads, 0, count);
            return topLocalLoads;
        }

        public int getUsedSlotsWithLiveTemporaries() {
            int afterSlot;
            int usedSlots = this.firstTemp;
            int i = this.f232sp;
            while (true) {
                int i2 = i;
                i--;
                if (i2 > 0) {
                    int slot = this.localLoads[i];
                    if (slot != -1 && (afterSlot = slot + this.localVariableTypes.get(slot).getSlots()) > usedSlots) {
                        usedSlots = afterSlot;
                    }
                } else {
                    return usedSlots;
                }
            }
        }

        void joinFrom(Stack joinOrigin, boolean breakTarget) {
            if ($assertionsDisabled || isStackCompatible(joinOrigin)) {
                if (breakTarget) {
                    this.firstTemp = Math.min(this.firstTemp, joinOrigin.firstTemp);
                } else if (!$assertionsDisabled && this.firstTemp != joinOrigin.firstTemp) {
                    throw new AssertionError();
                }
                int[] otherLoads = joinOrigin.localLoads;
                int firstDeadTemp = this.firstTemp;
                for (int i = 0; i < this.f232sp; i++) {
                    int localLoad = this.localLoads[i];
                    if (localLoad != otherLoads[i]) {
                        this.localLoads[i] = -1;
                    } else if (localLoad >= firstDeadTemp) {
                        firstDeadTemp = localLoad + this.localVariableTypes.get(localLoad).getSlots();
                    }
                }
                undefineLocalVariables(firstDeadTemp, false);
                if (!$assertionsDisabled && !isVariablePartitioningEqual(joinOrigin, firstDeadTemp)) {
                    throw new AssertionError();
                }
                mergeVariableTypes(joinOrigin, firstDeadTemp);
                return;
            }
            throw new AssertionError();
        }

        private void mergeVariableTypes(Stack joinOrigin, int toSlot) {
            ListIterator<Type> it1 = this.localVariableTypes.listIterator();
            Iterator<Type> it2 = joinOrigin.localVariableTypes.iterator();
            for (int i = 0; i < toSlot; i++) {
                Type thisType = it1.next();
                Type otherType = it2.next();
                if (otherType == Type.UNKNOWN) {
                    it1.set(Type.UNKNOWN);
                } else if (thisType == otherType) {
                    continue;
                } else if (thisType.isObject() && otherType.isObject()) {
                    it1.set(Type.OBJECT);
                } else if (!$assertionsDisabled && thisType != Type.UNKNOWN) {
                    throw new AssertionError();
                }
            }
        }

        void joinFromTry(Stack joinOrigin) {
            this.firstTemp = Math.min(this.firstTemp, joinOrigin.firstTemp);
            if ($assertionsDisabled || isVariablePartitioningEqual(joinOrigin, this.firstTemp)) {
                mergeVariableTypes(joinOrigin, this.firstTemp);
                return;
            }
            throw new AssertionError();
        }

        private int getFirstDeadLocal(List<Type> types) {
            int i = types.size();
            ListIterator<Type> it = types.listIterator(i);
            while (it.hasPrevious() && it.previous() == Type.UNKNOWN) {
                i--;
            }
            while (!this.symbolBoundary.get(i - 1)) {
                i++;
            }
            return i;
        }

        private boolean isStackCompatible(Stack other) {
            if (this.f232sp != other.f232sp) {
                return false;
            }
            for (int i = 0; i < this.f232sp; i++) {
                if (!this.data[i].isEquivalentTo(other.data[i])) {
                    return false;
                }
            }
            return true;
        }

        private boolean isVariablePartitioningEqual(Stack other, int toSlot) {
            BitSet diff = other.getSymbolBoundaryCopy();
            diff.xor(this.symbolBoundary);
            return diff.previousSetBit(toSlot - 1) == -1;
        }

        public void markDeadLocalVariables(int fromSlot, int slotCount) {
            int localCount = this.localVariableTypes.size();
            if (fromSlot >= localCount) {
                return;
            }
            int toSlot = Math.min(fromSlot + slotCount, localCount);
            invalidateLocalLoadsOnStack(fromSlot, toSlot);
            for (int i = fromSlot; i < toSlot; i++) {
                this.localVariableTypes.set(i, Type.UNKNOWN);
            }
        }

        public List<Type> getLocalVariableTypesCopy() {
            return (List) ((ArrayList) this.localVariableTypes).clone();
        }

        public BitSet getSymbolBoundaryCopy() {
            return (BitSet) this.symbolBoundary.clone();
        }

        public List<Type> getWidestLiveLocals(List<Type> lvarTypes) {
            List<Type> widestLiveLocals = new ArrayList<>(lvarTypes);
            boolean keepNextValue = true;
            int size = widestLiveLocals.size();
            int i = size - 1;
            while (true) {
                int i2 = i;
                i--;
                if (i2 > 0) {
                    if (this.symbolBoundary.get(i)) {
                        keepNextValue = true;
                    }
                    Type t = widestLiveLocals.get(i);
                    if (t != Type.UNKNOWN) {
                        if (keepNextValue) {
                            if (t != Type.SLOT_2) {
                                keepNextValue = false;
                            }
                        } else {
                            widestLiveLocals.set(i, Type.UNKNOWN);
                        }
                    }
                } else {
                    widestLiveLocals.subList(Math.max(getFirstDeadLocal(widestLiveLocals), this.firstTemp), widestLiveLocals.size()).clear();
                    return widestLiveLocals;
                }
            }
        }

        public String markSymbolBoundariesInLvarTypesDescriptor(String lvarDescriptor) {
            char[] chars = lvarDescriptor.toCharArray();
            int j = 0;
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                int nextj = j + CodeGeneratorLexicalContext.getTypeForSlotDescriptor(c).getSlots();
                if (!this.symbolBoundary.get(nextj - 1)) {
                    chars[i] = Character.toLowerCase(c);
                }
                j = nextj;
            }
            return new String(chars);
        }

        public Type pop() {
            if ($assertionsDisabled || this.f232sp > 0) {
                Type[] typeArr = this.data;
                int i = this.f232sp - 1;
                this.f232sp = i;
                return typeArr[i];
            }
            throw new AssertionError();
        }

        public Stack clone() {
            try {
                Stack clone = (Stack) super.clone();
                clone.data = (Type[]) this.data.clone();
                clone.localLoads = (int[]) this.localLoads.clone();
                clone.symbolBoundary = getSymbolBoundaryCopy();
                clone.localVariableTypes = getLocalVariableTypesCopy();
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError("", e);
            }
        }

        public Stack cloneWithEmptyStack() {
            Stack stack = clone();
            stack.f232sp = 0;
            return stack;
        }

        public int getTopLocalLoad() {
            return this.localLoads[this.f232sp - 1];
        }

        public void markLocalLoad(int slot) {
            this.localLoads[this.f232sp - 1] = slot;
        }

        public void onLocalStore(Type type, int slot, boolean onlySymbolLiveValue) {
            if (onlySymbolLiveValue) {
                int fromSlot = slot == 0 ? 0 : this.symbolBoundary.previousSetBit(slot - 1) + 1;
                int toSlot = this.symbolBoundary.nextSetBit(slot) + 1;
                for (int i = fromSlot; i < toSlot; i++) {
                    this.localVariableTypes.set(i, Type.UNKNOWN);
                }
                invalidateLocalLoadsOnStack(fromSlot, toSlot);
            } else {
                invalidateLocalLoadsOnStack(slot, slot + type.getSlots());
            }
            this.localVariableTypes.set(slot, type);
            if (type.isCategory2()) {
                this.localVariableTypes.set(slot + 1, Type.SLOT_2);
            }
        }

        private void invalidateLocalLoadsOnStack(int fromSlot, int toSlot) {
            for (int i = 0; i < this.f232sp; i++) {
                int localLoad = this.localLoads[i];
                if (localLoad >= fromSlot && localLoad < toSlot) {
                    this.localLoads[i] = -1;
                }
            }
        }

        public void defineBlockLocalVariable(int fromSlot, int toSlot) {
            defineLocalVariable(fromSlot, toSlot);
            if ($assertionsDisabled || this.firstTemp < toSlot) {
                this.firstTemp = toSlot;
                return;
            }
            throw new AssertionError();
        }

        public int defineTemporaryLocalVariable(int width) {
            int fromSlot = getUsedSlotsWithLiveTemporaries();
            defineLocalVariable(fromSlot, fromSlot + width);
            return fromSlot;
        }

        public void defineTemporaryLocalVariable(int fromSlot, int toSlot) {
            defineLocalVariable(fromSlot, toSlot);
        }

        private void defineLocalVariable(int fromSlot, int toSlot) {
            if ($assertionsDisabled || !hasLoadsOnStack(fromSlot, toSlot)) {
                if (!$assertionsDisabled && fromSlot >= toSlot) {
                    throw new AssertionError();
                }
                this.symbolBoundary.clear(fromSlot, toSlot - 1);
                this.symbolBoundary.set(toSlot - 1);
                int lastExisting = Math.min(toSlot, this.localVariableTypes.size());
                for (int i = fromSlot; i < lastExisting; i++) {
                    this.localVariableTypes.set(i, Type.UNKNOWN);
                }
                for (int i2 = lastExisting; i2 < toSlot; i2++) {
                    this.localVariableTypes.add(i2, Type.UNKNOWN);
                }
                return;
            }
            throw new AssertionError();
        }

        public void undefineLocalVariables(int fromSlot, boolean canTruncateSymbol) {
            int lvarCount = this.localVariableTypes.size();
            if ($assertionsDisabled || lvarCount == this.symbolBoundary.length()) {
                if (!$assertionsDisabled && hasLoadsOnStack(fromSlot, lvarCount)) {
                    throw new AssertionError();
                }
                if (canTruncateSymbol) {
                    if (fromSlot > 0) {
                        this.symbolBoundary.set(fromSlot - 1);
                    }
                } else if (!$assertionsDisabled && fromSlot != 0 && !this.symbolBoundary.get(fromSlot - 1)) {
                    throw new AssertionError();
                }
                if (fromSlot < lvarCount) {
                    this.symbolBoundary.clear(fromSlot, lvarCount);
                    this.localVariableTypes.subList(fromSlot, lvarCount).clear();
                }
                this.firstTemp = Math.min(fromSlot, this.firstTemp);
                if (!$assertionsDisabled && this.symbolBoundary.length() != this.localVariableTypes.size()) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && this.symbolBoundary.length() != fromSlot) {
                    throw new AssertionError();
                }
                return;
            }
            throw new AssertionError();
        }

        public void markAsOptimisticCatchHandler(int liveLocalCount) {
            undefineLocalVariables(liveLocalCount, true);
            this.firstTemp = liveLocalCount;
            this.localVariableTypes.subList(this.firstTemp, this.localVariableTypes.size()).clear();
            if ($assertionsDisabled || this.symbolBoundary.length() == this.firstTemp) {
                ListIterator<Type> it = this.localVariableTypes.listIterator();
                while (it.hasNext()) {
                    Type type = it.next();
                    if (type == Type.BOOLEAN) {
                        it.set(Type.INT);
                    } else if (type.isObject() && type != Type.OBJECT) {
                        it.set(Type.OBJECT);
                    }
                }
                return;
            }
            throw new AssertionError();
        }

        boolean hasLoadsOnStack(int fromSlot, int toSlot) {
            for (int i = 0; i < this.f232sp; i++) {
                int load = this.localLoads[i];
                if (load >= fromSlot && load < toSlot) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "stack=" + Arrays.toString(Arrays.copyOf(this.data, this.f232sp)) + ", symbolBoundaries=" + String.valueOf(this.symbolBoundary) + ", firstTemp=" + this.firstTemp + ", localTypes=" + String.valueOf(this.localVariableTypes);
        }
    }

    public Label(String name) {
        this.name = name;
        int i = nextId;
        nextId = i + 1;
        this.f231id = i;
    }

    public Label(Label label) {
        this.name = label.name;
        this.f231id = label.f231id;
    }

    public jdk.internal.org.objectweb.asm.Label getLabel() {
        if (this.label == null) {
            this.label = new jdk.internal.org.objectweb.asm.Label();
        }
        return this.label;
    }

    public Stack getStack() {
        return this.stack;
    }

    public void joinFrom(Stack joinOrigin) {
        this.reachable = true;
        if (this.stack == null) {
            this.stack = joinOrigin.clone();
        } else {
            this.stack.joinFrom(joinOrigin, this.breakTarget);
        }
    }

    public void joinFromTry(Stack joinOrigin, boolean isOptimismHandler) {
        this.reachable = true;
        if (this.stack == null) {
            if (!isOptimismHandler) {
                this.stack = joinOrigin.cloneWithEmptyStack();
                this.stack.undefineLocalVariables(this.stack.firstTemp, false);
            }
        } else if (!$assertionsDisabled && isOptimismHandler) {
            throw new AssertionError();
        } else {
            this.stack.joinFromTry(joinOrigin);
        }
    }

    public void markAsBreakTarget() {
        this.breakTarget = true;
    }

    public boolean isBreakTarget() {
        return this.breakTarget;
    }

    public void onCatch() {
        if (this.stack != null) {
            this.stack = this.stack.cloneWithEmptyStack();
        }
    }

    public void markAsOptimisticCatchHandler(Stack currentStack, int liveLocalCount) {
        this.stack = currentStack.cloneWithEmptyStack();
        this.stack.markAsOptimisticCatchHandler(liveLocalCount);
    }

    public void markAsOptimisticContinuationHandlerFor(Label afterConsumeStackLabel) {
        this.stack = afterConsumeStackLabel.stack.cloneWithEmptyStack();
    }

    public boolean isReachable() {
        return this.reachable;
    }

    public boolean isAfter(Label other) {
        return this.label.getOffset() > other.label.getOffset();
    }

    public String toString() {
        if (this.str == null) {
            this.str = this.name + '_' + this.f231id;
        }
        return this.str;
    }
}
