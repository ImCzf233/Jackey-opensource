package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AnchorType;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/AnchorNode.class */
public final class AnchorNode extends Node implements AnchorType {
    public int type;
    public Node target;
    public int charLength = -1;

    public AnchorNode(int type) {
        this.type = type;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public int getType() {
        return 7;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    protected void setChild(Node newChild) {
        this.target = newChild;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    protected Node getChild() {
        return this.target;
    }

    public void setTarget(Node tgt) {
        this.target = tgt;
        tgt.parent = this;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String getName() {
        return "Anchor";
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int level) {
        StringBuilder value = new StringBuilder();
        value.append("\n  type: " + typeToString());
        value.append("\n  target: " + pad(this.target, level + 1));
        return value.toString();
    }

    public String typeToString() {
        StringBuilder sb = new StringBuilder();
        if (isType(1)) {
            sb.append("BEGIN_BUF ");
        }
        if (isType(2)) {
            sb.append("BEGIN_LINE ");
        }
        if (isType(4)) {
            sb.append("BEGIN_POSITION ");
        }
        if (isType(8)) {
            sb.append("END_BUF ");
        }
        if (isType(16)) {
            sb.append("SEMI_END_BUF ");
        }
        if (isType(32)) {
            sb.append("END_LINE ");
        }
        if (isType(64)) {
            sb.append("WORD_BOUND ");
        }
        if (isType(128)) {
            sb.append("NOT_WORD_BOUND ");
        }
        if (isType(256)) {
            sb.append("WORD_BEGIN ");
        }
        if (isType(512)) {
            sb.append("WORD_END ");
        }
        if (isType(1024)) {
            sb.append("PREC_READ ");
        }
        if (isType(2048)) {
            sb.append("PREC_READ_NOT ");
        }
        if (isType(4096)) {
            sb.append("LOOK_BEHIND ");
        }
        if (isType(8192)) {
            sb.append("LOOK_BEHIND_NOT ");
        }
        if (isType(16384)) {
            sb.append("ANYCHAR_STAR ");
        }
        if (isType(32768)) {
            sb.append("ANYCHAR_STAR_ML ");
        }
        return sb.toString();
    }

    private boolean isType(int t) {
        return (this.type & t) != 0;
    }
}
