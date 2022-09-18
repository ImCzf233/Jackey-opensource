package com.viaversion.viaversion.libs.javassist.compiler;

import com.viaversion.viaversion.libs.javassist.compiler.ast.Declarator;
import java.util.HashMap;

/* loaded from: Jackey Client b2.jar:com/viaversion/viaversion/libs/javassist/compiler/SymbolTable.class */
public final class SymbolTable extends HashMap<String, Declarator> {
    private static final long serialVersionUID = 1;
    private SymbolTable parent;

    public SymbolTable() {
        this(null);
    }

    public SymbolTable(SymbolTable p) {
        this.parent = p;
    }

    public SymbolTable getParent() {
        return this.parent;
    }

    public Declarator lookup(String name) {
        Declarator found = get(name);
        if (found == null && this.parent != null) {
            return this.parent.lookup(name);
        }
        return found;
    }

    public void append(String name, Declarator value) {
        put(name, value);
    }
}
