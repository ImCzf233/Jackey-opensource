package jdk.nashorn.internal.runtime.regexp.joni;

import java.lang.ref.WeakReference;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StackType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/StackMachine.class */
public abstract class StackMachine extends Matcher implements StackType {
    protected static final int INVALID_INDEX = -1;
    protected StackEntry[] stack;
    protected int stk;
    protected final int[] repeatStk;
    protected final int memStartStk;
    protected final int memEndStk;
    static final ThreadLocal<WeakReference<StackEntry[]>> stacks = new ThreadLocal<WeakReference<StackEntry[]>>() { // from class: jdk.nashorn.internal.runtime.regexp.joni.StackMachine.1
        @Override // java.lang.ThreadLocal
        public WeakReference<StackEntry[]> initialValue() {
            return new WeakReference<>(StackMachine.allocateStack());
        }
    };

    public StackMachine(Regex regex, char[] chars, int p, int end) {
        super(regex, chars, p, end);
        this.stack = regex.stackNeeded ? fetchStack() : null;
        int n = regex.numRepeat + (regex.numMem << 1);
        this.repeatStk = n > 0 ? new int[n] : null;
        this.memStartStk = regex.numRepeat - 1;
        this.memEndStk = this.memStartStk + regex.numMem;
    }

    public static StackEntry[] allocateStack() {
        StackEntry[] stack = new StackEntry[64];
        stack[0] = new StackEntry();
        return stack;
    }

    private void doubleStack() {
        StackEntry[] newStack = new StackEntry[this.stack.length << 1];
        System.arraycopy(this.stack, 0, newStack, 0, this.stack.length);
        this.stack = newStack;
    }

    private static StackEntry[] fetchStack() {
        WeakReference<StackEntry[]> ref = stacks.get();
        StackEntry[] stack = ref.get();
        if (stack == null) {
            StackEntry[] allocateStack = allocateStack();
            stack = allocateStack;
            WeakReference<StackEntry[]> ref2 = new WeakReference<>(allocateStack);
            stacks.set(ref2);
        }
        return stack;
    }

    public final void init() {
        if (this.stack != null) {
            pushEnsured(1, this.regex.codeLength - 1);
        }
        if (this.repeatStk != null) {
            for (int i = 1; i <= this.regex.numMem; i++) {
                int[] iArr = this.repeatStk;
                int i2 = i + this.memStartStk;
                this.repeatStk[i + this.memEndStk] = -1;
                iArr[i2] = -1;
            }
        }
    }

    protected final StackEntry ensure1() {
        if (this.stk >= this.stack.length) {
            doubleStack();
        }
        StackEntry e = this.stack[this.stk];
        if (e == null) {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk;
            StackEntry stackEntry = new StackEntry();
            e = stackEntry;
            stackEntryArr[i] = stackEntry;
        }
        return e;
    }

    protected final void pushType(int type) {
        ensure1().type = type;
        this.stk++;
    }

    private void push(int type, int pat, int s, int prev) {
        StackEntry e = ensure1();
        e.type = type;
        e.setStatePCode(pat);
        e.setStatePStr(s);
        e.setStatePStrPrev(prev);
        this.stk++;
    }

    protected final void pushEnsured(int type, int pat) {
        StackEntry e = this.stack[this.stk];
        e.type = type;
        e.setStatePCode(pat);
        this.stk++;
    }

    public final void pushAlt(int pat, int s, int prev) {
        push(1, pat, s, prev);
    }

    public final void pushPos(int s, int prev) {
        push(StackType.POS, -1, s, prev);
    }

    public final void pushPosNot(int pat, int s, int prev) {
        push(3, pat, s, prev);
    }

    public final void pushStopBT() {
        pushType(StackType.STOP_BT);
    }

    public final void pushLookBehindNot(int pat, int s, int sprev) {
        push(2, pat, s, sprev);
    }

    public final void pushRepeat(int id, int pat) {
        StackEntry e = ensure1();
        e.type = StackType.REPEAT;
        e.setRepeatNum(id);
        e.setRepeatPCode(pat);
        e.setRepeatCount(0);
        this.stk++;
    }

    public final void pushRepeatInc(int sindex) {
        StackEntry e = ensure1();
        e.type = StackType.REPEAT_INC;
        e.setSi(sindex);
        this.stk++;
    }

    public final void pushMemStart(int mnum, int s) {
        StackEntry e = ensure1();
        e.type = 256;
        e.setMemNum(mnum);
        e.setMemPstr(s);
        e.setMemStart(this.repeatStk[this.memStartStk + mnum]);
        e.setMemEnd(this.repeatStk[this.memEndStk + mnum]);
        this.repeatStk[this.memStartStk + mnum] = this.stk;
        this.repeatStk[this.memEndStk + mnum] = -1;
        this.stk++;
    }

    public final void pushMemEnd(int mnum, int s) {
        StackEntry e = ensure1();
        e.type = StackType.MEM_END;
        e.setMemNum(mnum);
        e.setMemPstr(s);
        e.setMemStart(this.repeatStk[this.memStartStk + mnum]);
        e.setMemEnd(this.repeatStk[this.memEndStk + mnum]);
        this.repeatStk[this.memEndStk + mnum] = this.stk;
        this.stk++;
    }

    public final void pushMemEndMark(int mnum) {
        StackEntry e = ensure1();
        e.type = StackType.MEM_END_MARK;
        e.setMemNum(mnum);
        this.stk++;
    }

    public final int getMemStart(int mnum) {
        int level = 0;
        int stkp = this.stk;
        while (stkp > 0) {
            stkp--;
            StackEntry e = this.stack[stkp];
            if ((e.type & 32768) != 0 && e.getMemNum() == mnum) {
                level++;
            } else if (e.type == 256 && e.getMemNum() == mnum) {
                if (level == 0) {
                    break;
                }
                level--;
            }
        }
        return stkp;
    }

    public final void pushNullCheckStart(int cnum, int s) {
        StackEntry e = ensure1();
        e.type = StackType.NULL_CHECK_START;
        e.setNullCheckNum(cnum);
        e.setNullCheckPStr(s);
        this.stk++;
    }

    protected final void pushNullCheckEnd(int cnum) {
        StackEntry e = ensure1();
        e.type = StackType.NULL_CHECK_END;
        e.setNullCheckNum(cnum);
        this.stk++;
    }

    public final void popOne() {
        this.stk--;
    }

    public final StackEntry pop() {
        switch (this.regex.stackPopLevel) {
            case 0:
                return popFree();
            case 1:
                return popMemStart();
            default:
                return popDefault();
        }
    }

    private StackEntry popFree() {
        StackEntry e;
        do {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk - 1;
            this.stk = i;
            e = stackEntryArr[i];
        } while ((e.type & 255) == 0);
        return e;
    }

    private StackEntry popMemStart() {
        while (true) {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk - 1;
            this.stk = i;
            StackEntry e = stackEntryArr[i];
            if ((e.type & 255) != 0) {
                return e;
            }
            if (e.type == 256) {
                this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemEnd();
            }
        }
    }

    private StackEntry popDefault() {
        while (true) {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk - 1;
            this.stk = i;
            StackEntry e = stackEntryArr[i];
            if ((e.type & 255) != 0) {
                return e;
            }
            if (e.type == 256) {
                this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemEnd();
            } else if (e.type == 768) {
                this.stack[e.getSi()].decreaseRepeatCount();
            } else if (e.type == 33280) {
                this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemEnd();
            }
        }
    }

    public final void popTilPosNot() {
        while (true) {
            this.stk--;
            StackEntry e = this.stack[this.stk];
            if (e.type != 3) {
                if (e.type == 256) {
                    this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                    this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemStart();
                } else if (e.type == 768) {
                    this.stack[e.getSi()].decreaseRepeatCount();
                } else if (e.type == 33280) {
                    this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                    this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemStart();
                }
            } else {
                return;
            }
        }
    }

    public final void popTilLookBehindNot() {
        while (true) {
            this.stk--;
            StackEntry e = this.stack[this.stk];
            if (e.type != 2) {
                if (e.type == 256) {
                    this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                    this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemEnd();
                } else if (e.type == 768) {
                    this.stack[e.getSi()].decreaseRepeatCount();
                } else if (e.type == 33280) {
                    this.repeatStk[this.memStartStk + e.getMemNum()] = e.getMemStart();
                    this.repeatStk[this.memEndStk + e.getMemNum()] = e.getMemEnd();
                }
            } else {
                return;
            }
        }
    }

    public final int posEnd() {
        int k = this.stk;
        while (true) {
            k--;
            StackEntry e = this.stack[k];
            if ((e.type & StackType.MASK_TO_VOID_TARGET) != 0) {
                e.type = StackType.VOID;
            } else if (e.type == 1280) {
                e.type = StackType.VOID;
                return k;
            }
        }
    }

    public final void stopBtEnd() {
        int k = this.stk;
        while (true) {
            k--;
            StackEntry e = this.stack[k];
            if ((e.type & StackType.MASK_TO_VOID_TARGET) != 0) {
                e.type = StackType.VOID;
            } else if (e.type == 1536) {
                e.type = StackType.VOID;
                return;
            }
        }
    }

    public final int nullCheck(int id, int s) {
        StackEntry e;
        int k = this.stk;
        while (true) {
            k--;
            e = this.stack[k];
            if (e.type == 12288 && e.getNullCheckNum() == id) {
                break;
            }
        }
        return e.getNullCheckPStr() == s ? 1 : 0;
    }

    public final int nullCheckMemSt(int id, int s) {
        return -nullCheck(id, s);
    }

    public final int getRepeat(int id) {
        int level = 0;
        int k = this.stk;
        while (true) {
            k--;
            StackEntry e = this.stack[k];
            if (e.type == 1792) {
                if (level == 0 && e.getRepeatNum() == id) {
                    return k;
                }
            } else if (e.type == 2048) {
                level--;
            } else if (e.type == 2304) {
                level++;
            }
        }
    }

    protected final int sreturn() {
        int level = 0;
        int k = this.stk;
        while (true) {
            k--;
            StackEntry e = this.stack[k];
            if (e.type == 2048) {
                if (level == 0) {
                    return e.getCallFrameRetAddr();
                }
                level--;
            } else if (e.type == 2304) {
                level++;
            }
        }
    }
}
