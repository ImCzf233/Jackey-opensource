package jdk.nashorn.internal.codegen;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/CompileUnit.class */
public final class CompileUnit implements Comparable<CompileUnit>, Serializable {
    private static final long serialVersionUID = 1;
    private final transient String className;
    private transient ClassEmitter classEmitter;
    private transient long weight;
    private transient Class<?> clazz;
    private transient Map<FunctionNode, RecompilableScriptFunctionData> functions = new IdentityHashMap();
    private transient boolean isUsed;
    private static int emittedUnitCount;

    public CompileUnit(String className, ClassEmitter classEmitter, long initialWeight) {
        this.className = className;
        this.weight = initialWeight;
        this.classEmitter = classEmitter;
    }

    public static Set<CompileUnit> createCompileUnitSet() {
        return new TreeSet();
    }

    public static void increaseEmitCount() {
        emittedUnitCount++;
    }

    public static int getEmittedUnitCount() {
        return emittedUnitCount;
    }

    public boolean isUsed() {
        return this.isUsed;
    }

    public boolean hasCode() {
        return (this.classEmitter.getMethodCount() - this.classEmitter.getInitCount()) - this.classEmitter.getClinitCount() > 0;
    }

    public void setUsed() {
        this.isUsed = true;
    }

    public Class<?> getCode() {
        return this.clazz;
    }

    public void setCode(Class<?> clazz) {
        this.clazz = (Class) Objects.requireNonNull(clazz);
        this.classEmitter = null;
    }

    public void addFunctionInitializer(RecompilableScriptFunctionData data, FunctionNode functionNode) {
        this.functions.put(functionNode, data);
    }

    public boolean isInitializing(RecompilableScriptFunctionData data, FunctionNode functionNode) {
        return this.functions.get(functionNode) == data;
    }

    public void initializeFunctionsCode() {
        for (Map.Entry<FunctionNode, RecompilableScriptFunctionData> entry : this.functions.entrySet()) {
            entry.getValue().initializeCode(entry.getKey());
        }
    }

    public Collection<FunctionNode> getFunctionNodes() {
        return Collections.unmodifiableCollection(this.functions.keySet());
    }

    public void addWeight(long w) {
        this.weight += w;
    }

    public boolean canHold(long w) {
        return this.weight + w < Splitter.SPLIT_THRESHOLD;
    }

    public ClassEmitter getClassEmitter() {
        return this.classEmitter;
    }

    public String getUnitClassName() {
        return this.className;
    }

    private static String shortName(String name) {
        if (name == null) {
            return null;
        }
        return name.lastIndexOf(47) == -1 ? name : name.substring(name.lastIndexOf(47) + 1);
    }

    public String toString() {
        String methods = this.classEmitter != null ? this.classEmitter.getMethodNames().toString() : "<anon>";
        return "[CompileUnit className=" + shortName(this.className) + " weight=" + this.weight + '/' + Splitter.SPLIT_THRESHOLD + " hasCode=" + methods + ']';
    }

    public int compareTo(CompileUnit o) {
        return this.className.compareTo(o.className);
    }
}
