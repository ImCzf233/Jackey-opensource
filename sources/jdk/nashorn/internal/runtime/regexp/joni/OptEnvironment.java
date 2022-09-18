package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/OptEnvironment.class */
final class OptEnvironment {
    final MinMaxLen mmd = new MinMaxLen();
    int options;
    int caseFoldFlag;
    ScanEnvironment scanEnv;

    public void copy(OptEnvironment other) {
        this.mmd.copy(other.mmd);
        this.options = other.options;
        this.caseFoldFlag = other.caseFoldFlag;
        this.scanEnv = other.scanEnv;
    }
}
