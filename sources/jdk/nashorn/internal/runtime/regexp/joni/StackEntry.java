package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/StackEntry.class */
public final class StackEntry {
    int type;

    /* renamed from: E1 */
    private int f292E1;

    /* renamed from: E2 */
    private int f293E2;

    /* renamed from: E3 */
    private int f294E3;

    /* renamed from: E4 */
    private int f295E4;

    public void setStatePCode(int pcode) {
        this.f292E1 = pcode;
    }

    public int getStatePCode() {
        return this.f292E1;
    }

    public void setStatePStr(int pstr) {
        this.f293E2 = pstr;
    }

    public int getStatePStr() {
        return this.f293E2;
    }

    public void setStatePStrPrev(int pstrPrev) {
        this.f294E3 = pstrPrev;
    }

    public int getStatePStrPrev() {
        return this.f294E3;
    }

    void setStateCheck(int check) {
        this.f295E4 = check;
    }

    int getStateCheck() {
        return this.f295E4;
    }

    public void setRepeatCount(int count) {
        this.f292E1 = count;
    }

    public int getRepeatCount() {
        return this.f292E1;
    }

    public void decreaseRepeatCount() {
        this.f292E1--;
    }

    public void increaseRepeatCount() {
        this.f292E1++;
    }

    public void setRepeatPCode(int pcode) {
        this.f293E2 = pcode;
    }

    public int getRepeatPCode() {
        return this.f293E2;
    }

    public void setRepeatNum(int num) {
        this.f294E3 = num;
    }

    public int getRepeatNum() {
        return this.f294E3;
    }

    public void setSi(int si) {
        this.f292E1 = si;
    }

    public int getSi() {
        return this.f292E1;
    }

    public void setMemNum(int num) {
        this.f292E1 = num;
    }

    public int getMemNum() {
        return this.f292E1;
    }

    public void setMemPstr(int pstr) {
        this.f293E2 = pstr;
    }

    public int getMemPStr() {
        return this.f293E2;
    }

    public void setMemStart(int start) {
        this.f294E3 = start;
    }

    public int getMemStart() {
        return this.f294E3;
    }

    public void setMemEnd(int end) {
        this.f295E4 = end;
    }

    public int getMemEnd() {
        return this.f295E4;
    }

    public void setNullCheckNum(int num) {
        this.f292E1 = num;
    }

    public int getNullCheckNum() {
        return this.f292E1;
    }

    public void setNullCheckPStr(int pstr) {
        this.f293E2 = pstr;
    }

    public int getNullCheckPStr() {
        return this.f293E2;
    }

    void setCallFrameRetAddr(int addr) {
        this.f292E1 = addr;
    }

    public int getCallFrameRetAddr() {
        return this.f292E1;
    }

    void setCallFrameNum(int num) {
        this.f293E2 = num;
    }

    int getCallFrameNum() {
        return this.f293E2;
    }

    void setCallFramePStr(int pstr) {
        this.f294E3 = pstr;
    }

    int getCallFramePStr() {
        return this.f294E3;
    }
}
