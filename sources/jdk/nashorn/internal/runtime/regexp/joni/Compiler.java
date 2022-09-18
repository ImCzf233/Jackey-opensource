package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Compiler.class */
public abstract class Compiler implements ErrorMessages {
    protected final Analyser analyser;
    protected final Regex regex;

    protected abstract void prepare();

    protected abstract void finish();

    protected abstract void compileAltNode(ConsAltNode consAltNode);

    protected abstract void addCompileString(char[] cArr, int i, int i2, boolean z);

    protected abstract void compileCClassNode(CClassNode cClassNode);

    protected abstract void compileAnyCharNode();

    protected abstract void compileBackrefNode(BackRefNode backRefNode);

    protected abstract void compileNonCECQuantifierNode(QuantifierNode quantifierNode);

    protected abstract void compileOptionNode(EncloseNode encloseNode);

    protected abstract void compileEncloseNode(EncloseNode encloseNode);

    protected abstract void compileAnchorNode(AnchorNode anchorNode);

    public Compiler(Analyser analyser) {
        this.analyser = analyser;
        this.regex = analyser.regex;
    }

    public final void compile() {
        prepare();
        compileTree(this.analyser.root);
        finish();
    }

    private void compileStringRawNode(StringNode sn) {
        if (sn.length() <= 0) {
            return;
        }
        addCompileString(sn.chars, sn.f305p, sn.length(), false);
    }

    private void compileStringNode(StringNode node) {
        if (node.length() <= 0) {
            return;
        }
        boolean ambig = node.isAmbig();
        int p = node.f305p;
        int end = node.end;
        char[] chars = node.chars;
        int slen = 1;
        for (int p2 = p + 1; p2 < end; p2++) {
            slen++;
        }
        addCompileString(chars, p, slen, ambig);
    }

    public final void compileTree(Node node) {
        ConsAltNode consAltNode;
        switch (node.getType()) {
            case 0:
                StringNode sn = (StringNode) node;
                if (sn.isRaw()) {
                    compileStringRawNode(sn);
                    return;
                } else {
                    compileStringNode(sn);
                    return;
                }
            case 1:
                compileCClassNode((CClassNode) node);
                return;
            case 2:
            default:
                newInternalException(ErrorMessages.ERR_PARSER_BUG);
                return;
            case 3:
                compileAnyCharNode();
                return;
            case 4:
                compileBackrefNode((BackRefNode) node);
                return;
            case 5:
                compileNonCECQuantifierNode((QuantifierNode) node);
                return;
            case 6:
                EncloseNode enode = (EncloseNode) node;
                if (enode.isOption()) {
                    compileOptionNode(enode);
                    return;
                } else {
                    compileEncloseNode(enode);
                    return;
                }
            case 7:
                compileAnchorNode((AnchorNode) node);
                return;
            case 8:
                ConsAltNode lin = (ConsAltNode) node;
                do {
                    compileTree(lin.car);
                    consAltNode = lin.cdr;
                    lin = consAltNode;
                } while (consAltNode != null);
                return;
            case 9:
                compileAltNode((ConsAltNode) node);
                return;
        }
    }

    public final void compileTreeNTimes(Node node, int n) {
        for (int i = 0; i < n; i++) {
            compileTree(node);
        }
    }

    public void newSyntaxException(String message) {
        throw new SyntaxException(message);
    }

    public void newInternalException(String message) {
        throw new InternalException(message);
    }
}
