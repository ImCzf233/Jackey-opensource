package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AnchorType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.ObjPtr;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/regexp/joni/Analyser.class */
public final class Analyser extends Parser {
    private static final int GET_CHAR_LEN_VARLEN = -1;
    private static final int GET_CHAR_LEN_TOP_ALT_VARLEN = -2;
    private static final int THRESHOLD_CASE_FOLD_ALT_FOR_EXPANSION = 8;
    private static final int IN_ALT = 1;
    private static final int IN_NOT = 2;
    private static final int IN_REPEAT = 4;
    private static final int IN_VAR_REPEAT = 8;
    private static final int EXPAND_STRING_MAX_LENGTH = 100;
    private static final int MAX_NODE_OPT_INFO_REF_COUNT = 5;

    public Analyser(ScanEnvironment env, char[] chars, int p, int end) {
        super(env, chars, p, end);
    }

    public final void compile() {
        reset();
        this.regex.numMem = 0;
        this.regex.numRepeat = 0;
        this.regex.numNullCheck = 0;
        this.regex.repeatRangeLo = null;
        this.regex.repeatRangeHi = null;
        parse();
        this.root = setupTree(this.root, 0);
        this.regex.captureHistory = this.env.captureHistory;
        this.regex.btMemStart = this.env.btMemStart;
        this.regex.btMemEnd = this.env.btMemEnd;
        if (Option.isFindCondition(this.regex.options)) {
            this.regex.btMemEnd = BitStatus.bsAll();
        } else {
            this.regex.btMemEnd = this.env.btMemEnd;
            this.regex.btMemEnd |= this.regex.captureHistory;
        }
        this.regex.clearOptimizeInfo();
        setOptimizedInfoFromTree(this.root);
        this.env.memNodes = null;
        if (this.regex.numRepeat != 0 || this.regex.btMemEnd != 0) {
            this.regex.stackPopLevel = 2;
        } else if (this.regex.btMemStart != 0) {
            this.regex.stackPopLevel = 1;
        } else {
            this.regex.stackPopLevel = 0;
        }
    }

    private void swap(Node a, Node b) {
        a.swap(b);
        if (this.root == b) {
            this.root = a;
        } else if (this.root == a) {
            this.root = b;
        }
    }

    private int quantifiersMemoryInfo(Node node) {
        ConsAltNode consAltNode;
        int info = 0;
        switch (node.getType()) {
            case 5:
                QuantifierNode qn = (QuantifierNode) node;
                if (qn.upper != 0) {
                    info = quantifiersMemoryInfo(qn.target);
                    break;
                }
                break;
            case 6:
                EncloseNode en = (EncloseNode) node;
                switch (en.type) {
                    case 1:
                        return 2;
                    case 2:
                    case 4:
                        info = quantifiersMemoryInfo(en.target);
                        break;
                }
            case 8:
            case 9:
                ConsAltNode can = (ConsAltNode) node;
                do {
                    int v = quantifiersMemoryInfo(can.car);
                    if (v > info) {
                        info = v;
                    }
                    consAltNode = can.cdr;
                    can = consAltNode;
                } while (consAltNode != null);
                break;
        }
        return info;
    }

    private int getMinMatchLength(Node node) {
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        int min = 0;
        switch (node.getType()) {
            case 0:
                min = ((StringNode) node).length();
                break;
            case 1:
            case 3:
                min = 1;
                break;
            case 2:
                min = 1;
                break;
            case 4:
                BackRefNode br = (BackRefNode) node;
                if (!br.isRecursion()) {
                    if (br.backRef > this.env.numMem) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
                    }
                    min = getMinMatchLength(this.env.memNodes[br.backRef]);
                    break;
                }
                break;
            case 5:
                QuantifierNode qn = (QuantifierNode) node;
                if (qn.lower > 0) {
                    min = MinMaxLen.distanceMultiply(getMinMatchLength(qn.target), qn.lower);
                    break;
                }
                break;
            case 6:
                EncloseNode en = (EncloseNode) node;
                switch (en.type) {
                    case 1:
                        if (en.isMinFixed()) {
                            min = en.minLength;
                            break;
                        } else {
                            min = getMinMatchLength(en.target);
                            en.minLength = min;
                            en.setMinFixed();
                            break;
                        }
                    case 2:
                    case 4:
                        min = getMinMatchLength(en.target);
                        break;
                }
            case 8:
                ConsAltNode can = (ConsAltNode) node;
                do {
                    min += getMinMatchLength(can.car);
                    consAltNode2 = can.cdr;
                    can = consAltNode2;
                } while (consAltNode2 != null);
                break;
            case 9:
                ConsAltNode y = (ConsAltNode) node;
                do {
                    Node x = y.car;
                    int tmin = getMinMatchLength(x);
                    if (y == node) {
                        min = tmin;
                    } else if (min > tmin) {
                        min = tmin;
                    }
                    consAltNode = y.cdr;
                    y = consAltNode;
                } while (consAltNode != null);
                break;
        }
        return min;
    }

    private int getMaxMatchLength(Node node) {
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        int max = 0;
        switch (node.getType()) {
            case 0:
                max = ((StringNode) node).length();
                break;
            case 1:
            case 3:
                max = 1;
                break;
            case 2:
                max = 1;
                break;
            case 4:
                BackRefNode br = (BackRefNode) node;
                if (br.isRecursion()) {
                    max = Integer.MAX_VALUE;
                    break;
                } else if (br.backRef > this.env.numMem) {
                    throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
                } else {
                    int tmax = getMaxMatchLength(this.env.memNodes[br.backRef]);
                    if (0 < tmax) {
                        max = tmax;
                        break;
                    }
                }
                break;
            case 5:
                QuantifierNode qn = (QuantifierNode) node;
                if (qn.upper != 0) {
                    max = getMaxMatchLength(qn.target);
                    if (max != 0) {
                        if (!QuantifierNode.isRepeatInfinite(qn.upper)) {
                            max = MinMaxLen.distanceMultiply(max, qn.upper);
                            break;
                        } else {
                            max = Integer.MAX_VALUE;
                            break;
                        }
                    }
                }
                break;
            case 6:
                EncloseNode en = (EncloseNode) node;
                switch (en.type) {
                    case 1:
                        if (en.isMaxFixed()) {
                            max = en.maxLength;
                            break;
                        } else {
                            max = getMaxMatchLength(en.target);
                            en.maxLength = max;
                            en.setMaxFixed();
                            break;
                        }
                    case 2:
                    case 4:
                        max = getMaxMatchLength(en.target);
                        break;
                }
            case 8:
                ConsAltNode ln = (ConsAltNode) node;
                do {
                    max = MinMaxLen.distanceAdd(max, getMaxMatchLength(ln.car));
                    consAltNode2 = ln.cdr;
                    ln = consAltNode2;
                } while (consAltNode2 != null);
                break;
            case 9:
                ConsAltNode an = (ConsAltNode) node;
                do {
                    int tmax2 = getMaxMatchLength(an.car);
                    if (max < tmax2) {
                        max = tmax2;
                    }
                    consAltNode = an.cdr;
                    an = consAltNode;
                } while (consAltNode != null);
                break;
        }
        return max;
    }

    public final int getCharLengthTree(Node node) {
        return getCharLengthTree(node, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getCharLengthTree(jdk.nashorn.internal.runtime.regexp.joni.ast.Node r5, int r6) {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.regexp.joni.Analyser.getCharLengthTree(jdk.nashorn.internal.runtime.regexp.joni.ast.Node, int):int");
    }

    private static boolean isNotIncluded(Node xn, Node yn) {
        Node x = xn;
        Node node = yn;
        while (true) {
            Node y = node;
            int yType = y.getType();
            switch (x.getType()) {
                case 0:
                    StringNode xs = (StringNode) x;
                    if (xs.length() != 0) {
                        switch (yType) {
                            case 0:
                                StringNode ys = (StringNode) y;
                                int len = xs.length();
                                if (len > ys.length()) {
                                    len = ys.length();
                                }
                                if (xs.isAmbig() || ys.isAmbig()) {
                                    return false;
                                }
                                int i = 0;
                                int pt = ys.f305p;
                                int q = xs.f305p;
                                while (i < len) {
                                    if (ys.chars[pt] == xs.chars[q]) {
                                        i++;
                                        pt++;
                                        q++;
                                    } else {
                                        return true;
                                    }
                                }
                                return false;
                            case 1:
                                CClassNode cc = (CClassNode) y;
                                return !cc.isCodeInCC(xs.chars[xs.f305p]);
                            default:
                                return false;
                        }
                    }
                    return false;
                case 1:
                    CClassNode xc = (CClassNode) x;
                    switch (yType) {
                        case 0:
                            Node tmp = x;
                            x = y;
                            node = tmp;
                            break;
                        case 1:
                            CClassNode yc = (CClassNode) y;
                            for (int i2 = 0; i2 < 256; i2++) {
                                boolean v = xc.f297bs.m58at(i2);
                                if ((v && !xc.isNot()) || (!v && xc.isNot())) {
                                    boolean v2 = yc.f297bs.m58at(i2);
                                    if (v2 && !yc.isNot()) {
                                        return false;
                                    }
                                    if (!v2 && yc.isNot()) {
                                        return false;
                                    }
                                }
                            }
                            if (xc.mbuf == null && !xc.isNot()) {
                                return true;
                            }
                            if (yc.mbuf == null && !yc.isNot()) {
                                return true;
                            }
                            return false;
                        default:
                            return false;
                    }
                case 2:
                    switch (yType) {
                        case 0:
                            Node tmp2 = x;
                            x = y;
                            node = tmp2;
                            continue;
                        case 1:
                            Node tmp3 = x;
                            x = y;
                            node = tmp3;
                            continue;
                        default:
                            return false;
                    }
                default:
                    return false;
            }
        }
    }

    private Node getHeadValueNode(Node node, boolean exact) {
        Node n = null;
        switch (node.getType()) {
            case 0:
                StringNode sn = (StringNode) node;
                if (sn.end > sn.f305p && (!exact || sn.isRaw() || !Option.isIgnoreCase(this.regex.options))) {
                    n = node;
                    break;
                }
                break;
            case 1:
            case 2:
                if (!exact) {
                    n = node;
                    break;
                }
                break;
            case 5:
                QuantifierNode qn = (QuantifierNode) node;
                if (qn.lower > 0) {
                    if (qn.headExact != null) {
                        n = qn.headExact;
                        break;
                    } else {
                        n = getHeadValueNode(qn.target, exact);
                        break;
                    }
                }
                break;
            case 6:
                EncloseNode en = (EncloseNode) node;
                switch (en.type) {
                    case 1:
                    case 4:
                        n = getHeadValueNode(en.target, exact);
                        break;
                    case 2:
                        int options = this.regex.options;
                        this.regex.options = en.option;
                        n = getHeadValueNode(en.target, exact);
                        this.regex.options = options;
                        break;
                }
            case 7:
                AnchorNode an = (AnchorNode) node;
                if (an.type == 1024) {
                    n = getHeadValueNode(an.target, exact);
                    break;
                }
                break;
            case 8:
                n = getHeadValueNode(((ConsAltNode) node).car, exact);
                break;
        }
        return n;
    }

    private boolean checkTypeTree(Node node, int typeMask, int encloseMask, int anchorMask) {
        ConsAltNode consAltNode;
        if ((node.getType2Bit() & typeMask) == 0) {
            return true;
        }
        boolean invalid = false;
        switch (node.getType()) {
            case 5:
                invalid = checkTypeTree(((QuantifierNode) node).target, typeMask, encloseMask, anchorMask);
                break;
            case 6:
                EncloseNode en = (EncloseNode) node;
                if ((en.type & encloseMask) == 0) {
                    return true;
                }
                invalid = checkTypeTree(en.target, typeMask, encloseMask, anchorMask);
                break;
            case 7:
                AnchorNode an = (AnchorNode) node;
                if ((an.type & anchorMask) == 0) {
                    return true;
                }
                if (an.target != null) {
                    invalid = checkTypeTree(an.target, typeMask, encloseMask, anchorMask);
                    break;
                }
                break;
            case 8:
            case 9:
                ConsAltNode can = (ConsAltNode) node;
                do {
                    invalid = checkTypeTree(can.car, typeMask, encloseMask, anchorMask);
                    if (invalid) {
                        break;
                    } else {
                        consAltNode = can.cdr;
                        can = consAltNode;
                    }
                } while (consAltNode != null);
                break;
        }
        return invalid;
    }

    private Node divideLookBehindAlternatives(Node nodep) {
        Node node;
        AnchorNode an = (AnchorNode) nodep;
        int anchorType = an.type;
        Node head = an.target;
        Node np = ((ConsAltNode) head).car;
        swap(nodep, head);
        ((ConsAltNode) head).setCar(nodep);
        ((AnchorNode) nodep).setTarget(np);
        Node np2 = head;
        while (true) {
            Node node2 = ((ConsAltNode) np2).cdr;
            np2 = node2;
            if (node2 == null) {
                break;
            }
            AnchorNode insert = new AnchorNode(anchorType);
            insert.setTarget(((ConsAltNode) np2).car);
            ((ConsAltNode) np2).setCar(insert);
        }
        if (anchorType == 8192) {
            Node np3 = head;
            do {
                ((ConsAltNode) np3).toListNode();
                node = ((ConsAltNode) np3).cdr;
                np3 = node;
            } while (node != null);
            return head;
        }
        return head;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Node setupLookBehind(Node node) {
        AnchorNode an = (AnchorNode) node;
        int len = getCharLengthTree(an.target);
        switch (this.returnCode) {
            case -2:
                if (this.syntax.differentLengthAltLookBehind()) {
                    return divideLookBehindAlternatives(node);
                }
                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
            case -1:
                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
            case 0:
                an.charLength = len;
                break;
        }
        return node;
    }

    private void nextSetup(Node nodep, Node nextNode) {
        Node x;
        Node y;
        Node node = nodep;
        while (true) {
            Node node2 = node;
            int type = node2.getType();
            if (type == 5) {
                QuantifierNode qn = (QuantifierNode) node2;
                if (qn.greedy && QuantifierNode.isRepeatInfinite(qn.upper)) {
                    StringNode n = (StringNode) getHeadValueNode(nextNode, true);
                    if (n != null && n.chars[n.f305p] != 0) {
                        qn.nextHeadExact = n;
                    }
                    if (qn.lower <= 1 && qn.target.isSimple() && (x = getHeadValueNode(qn.target, false)) != null && (y = getHeadValueNode(nextNode, false)) != null && isNotIncluded(x, y)) {
                        EncloseNode en = new EncloseNode(4);
                        en.setStopBtSimpleRepeat();
                        swap(node2, en);
                        en.setTarget(node2);
                        return;
                    }
                    return;
                }
                return;
            } else if (type == 6) {
                EncloseNode en2 = (EncloseNode) node2;
                if (en2.isMemory()) {
                    node = en2.target;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void updateStringNodeCaseFoldMultiByte(StringNode sn) {
        char[] ch = sn.chars;
        int end = sn.end;
        this.value = sn.f305p;
        int sp = 0;
        while (this.value < end) {
            int ovalue = this.value;
            int i = this.value;
            this.value = i + 1;
            char buf = EncodingHelper.toLowerCase(ch[i]);
            if (ch[ovalue] != buf) {
                char[] sbuf = new char[sn.length() << 1];
                System.arraycopy(ch, sn.f305p, sbuf, 0, ovalue - sn.f305p);
                this.value = ovalue;
                while (this.value < end) {
                    int i2 = this.value;
                    this.value = i2 + 1;
                    char buf2 = EncodingHelper.toLowerCase(ch[i2]);
                    if (sp >= sbuf.length) {
                        char[] tmp = new char[sbuf.length << 1];
                        System.arraycopy(sbuf, 0, tmp, 0, sbuf.length);
                        sbuf = tmp;
                    }
                    int i3 = sp;
                    sp++;
                    sbuf[i3] = buf2;
                }
                sn.set(sbuf, 0, sp);
                return;
            }
            sp++;
        }
    }

    private void updateStringNodeCaseFold(Node node) {
        StringNode sn = (StringNode) node;
        updateStringNodeCaseFoldMultiByte(sn);
    }

    private Node expandCaseFoldMakeRemString(char[] ch, int pp, int end) {
        StringNode node = new StringNode(ch, pp, end);
        updateStringNodeCaseFold(node);
        node.setAmbig();
        node.setDontGetOptInfo();
        return node;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode, T] */
    private static boolean expandCaseFoldStringAlt(int itemNum, char[] items, char[] chars, int p, int slen, int end, ObjPtr<Node> node) {
        ?? newAltNode = ConsAltNode.newAltNode(null, null);
        ConsAltNode altNode = newAltNode;
        node.f311p = newAltNode;
        altNode.setCar(new StringNode(chars, p, p + slen));
        for (int i = 0; i < itemNum; i++) {
            StringNode snode = new StringNode();
            snode.catCode(items[i]);
            ConsAltNode an = ConsAltNode.newAltNode(null, null);
            an.setCar(snode);
            altNode.setCdr(an);
            altNode = an;
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v29, types: [T, jdk.nashorn.internal.runtime.regexp.joni.ast.Node] */
    /* JADX WARN: Type inference failed for: r1v26, types: [T, jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode] */
    private Node expandCaseFoldString(Node node) {
        StringNode sn = (StringNode) node;
        if (sn.isAmbig() || sn.length() <= 0) {
            return node;
        }
        char[] chars1 = sn.chars;
        int pt = sn.f305p;
        int end = sn.end;
        int altNum = 1;
        ConsAltNode topRoot = null;
        ConsAltNode r = null;
        ObjPtr<Node> prevNode = new ObjPtr<>();
        StringNode stringNode = null;
        while (pt < end) {
            char[] items = EncodingHelper.caseFoldCodesByString(this.regex.caseFoldFlag, chars1[pt]);
            if (items.length == 0) {
                if (stringNode == null) {
                    if (r == null && prevNode.f311p != 0) {
                        ConsAltNode listAdd = ConsAltNode.listAdd(null, (Node) prevNode.f311p);
                        r = listAdd;
                        topRoot = listAdd;
                    }
                    ?? stringNode2 = new StringNode();
                    stringNode = stringNode2;
                    prevNode.f311p = stringNode2;
                    if (r != null) {
                        ConsAltNode.listAdd(r, stringNode);
                    }
                }
                stringNode.cat(chars1, pt, pt + 1);
            } else {
                altNum *= items.length + 1;
                if (altNum > 8) {
                    break;
                }
                if (r == null && prevNode.f311p != 0) {
                    ConsAltNode listAdd2 = ConsAltNode.listAdd(null, (Node) prevNode.f311p);
                    r = listAdd2;
                    topRoot = listAdd2;
                }
                expandCaseFoldStringAlt(items.length, items, chars1, pt, 1, end, prevNode);
                if (r != null) {
                    ConsAltNode.listAdd(r, (Node) prevNode.f311p);
                }
                stringNode = null;
            }
            pt++;
        }
        if (pt < end) {
            ?? expandCaseFoldMakeRemString = expandCaseFoldMakeRemString(chars1, pt, end);
            if (prevNode.f311p != 0 && r == null) {
                ConsAltNode listAdd3 = ConsAltNode.listAdd(null, (Node) prevNode.f311p);
                r = listAdd3;
                topRoot = listAdd3;
            }
            if (r == null) {
                prevNode.f311p = expandCaseFoldMakeRemString;
            } else {
                ConsAltNode.listAdd(r, expandCaseFoldMakeRemString);
            }
        }
        Node xnode = topRoot != null ? topRoot : (Node) prevNode.f311p;
        swap(node, xnode);
        return xnode;
    }

    protected final Node setupTree(Node nodep, int statep) {
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        Node node = nodep;
        while (true) {
            switch (node.getType()) {
                case 0:
                    if (Option.isIgnoreCase(this.regex.options) && !((StringNode) node).isRaw()) {
                        node = expandCaseFoldString(node);
                        break;
                    }
                    break;
                case 4:
                    BackRefNode br = (BackRefNode) node;
                    if (br.backRef > this.env.numMem) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
                    }
                    this.env.backrefedMem = BitStatus.bsOnAt(this.env.backrefedMem, br.backRef);
                    this.env.btMemStart = BitStatus.bsOnAt(this.env.btMemStart, br.backRef);
                    ((EncloseNode) this.env.memNodes[br.backRef]).setMemBackrefed();
                    break;
                case 5:
                    QuantifierNode qn = (QuantifierNode) node;
                    Node target = qn.target;
                    if ((statep & 4) != 0) {
                        qn.setInRepeat();
                    }
                    if (QuantifierNode.isRepeatInfinite(qn.upper) || qn.lower >= 1) {
                        int d = getMinMatchLength(target);
                        if (d == 0) {
                            qn.targetEmptyInfo = 1;
                            int info = quantifiersMemoryInfo(target);
                            if (info > 0) {
                                qn.targetEmptyInfo = info;
                            }
                        }
                    }
                    int state = statep | 4;
                    if (qn.lower != qn.upper) {
                        state |= 8;
                    }
                    Node target2 = setupTree(target, state);
                    if (target2.getType() == 0 && !QuantifierNode.isRepeatInfinite(qn.lower) && qn.lower == qn.upper && qn.lower > 1 && qn.lower <= 100) {
                        StringNode sn = (StringNode) target2;
                        int len = sn.length();
                        if (len * qn.lower <= 100) {
                            StringNode str = qn.convertToString(sn.flag);
                            int n = qn.lower;
                            for (int i = 0; i < n; i++) {
                                str.cat(sn.chars, sn.f305p, sn.end);
                            }
                            break;
                        }
                    }
                    if (qn.greedy && qn.targetEmptyInfo != 0) {
                        if (target2.getType() == 5) {
                            QuantifierNode tqn = (QuantifierNode) target2;
                            if (tqn.headExact != null) {
                                qn.headExact = tqn.headExact;
                                tqn.headExact = null;
                                break;
                            }
                        } else {
                            qn.headExact = getHeadValueNode(qn.target, true);
                            break;
                        }
                    }
                    break;
                case 6:
                    EncloseNode en = (EncloseNode) node;
                    switch (en.type) {
                        case 1:
                            if ((statep & 11) != 0) {
                                this.env.btMemStart = BitStatus.bsOnAt(this.env.btMemStart, en.regNum);
                            }
                            setupTree(en.target, statep);
                            break;
                        case 2:
                            int options = this.regex.options;
                            this.regex.options = en.option;
                            setupTree(en.target, statep);
                            this.regex.options = options;
                            break;
                        case 4:
                            setupTree(en.target, statep);
                            if (en.target.getType() == 5) {
                                QuantifierNode tqn2 = (QuantifierNode) en.target;
                                if (QuantifierNode.isRepeatInfinite(tqn2.upper) && tqn2.lower <= 1 && tqn2.greedy && tqn2.target.isSimple()) {
                                    en.setStopBtSimpleRepeat();
                                    break;
                                }
                            }
                            break;
                    }
                case 7:
                    AnchorNode an = (AnchorNode) node;
                    switch (an.type) {
                        case 1024:
                            setupTree(an.target, statep);
                            break;
                        case 2048:
                            setupTree(an.target, statep | 2);
                            break;
                        case 4096:
                            if (checkTypeTree(an.target, NodeType.ALLOWED_IN_LB, 1, AnchorType.ALLOWED_IN_LB)) {
                                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
                            }
                            node = setupLookBehind(node);
                            if (node.getType() != 7) {
                                break;
                            } else {
                                setupTree(((AnchorNode) node).target, statep);
                                break;
                            }
                        case 8192:
                            if (checkTypeTree(an.target, NodeType.ALLOWED_IN_LB, 1, AnchorType.ALLOWED_IN_LB)) {
                                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
                            }
                            node = setupLookBehind(node);
                            if (node.getType() != 7) {
                                break;
                            } else {
                                setupTree(((AnchorNode) node).target, statep | 2);
                                break;
                            }
                    }
                    break;
                case 8:
                    ConsAltNode lin = (ConsAltNode) node;
                    Node prev = null;
                    do {
                        setupTree(lin.car, statep);
                        if (prev != null) {
                            nextSetup(prev, lin.car);
                        }
                        prev = lin.car;
                        consAltNode2 = lin.cdr;
                        lin = consAltNode2;
                    } while (consAltNode2 != null);
                    break;
                case 9:
                    ConsAltNode aln = (ConsAltNode) node;
                    do {
                        setupTree(aln.car, statep | 1);
                        consAltNode = aln.cdr;
                        aln = consAltNode;
                    } while (consAltNode != null);
                    break;
            }
        }
        return node;
    }

    private void optimizeNodeLeft(Node node, NodeOptInfo opt, OptEnvironment oenv) {
        int max;
        int max2;
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        opt.clear();
        opt.setBoundNode(oenv.mmd);
        switch (node.getType()) {
            case 0:
                StringNode sn = (StringNode) node;
                int slen = sn.length();
                if (!sn.isAmbig()) {
                    opt.exb.concatStr(sn.chars, sn.f305p, sn.end, sn.isRaw());
                    if (slen > 0) {
                        opt.map.addChar(sn.chars[sn.f305p]);
                    }
                    opt.length.set(slen, slen);
                } else {
                    if (sn.isDontGetOptInfo()) {
                        max2 = sn.length();
                    } else {
                        opt.exb.concatStr(sn.chars, sn.f305p, sn.end, sn.isRaw());
                        opt.exb.ignoreCase = true;
                        if (slen > 0) {
                            opt.map.addCharAmb(sn.chars, sn.f305p, sn.end, oenv.caseFoldFlag);
                        }
                        max2 = slen;
                    }
                    opt.length.set(slen, max2);
                }
                if (opt.exb.length == slen) {
                    opt.exb.reachEnd = true;
                    return;
                }
                return;
            case 1:
                CClassNode cc = (CClassNode) node;
                if (cc.mbuf != null || cc.isNot()) {
                    opt.length.set(1, 1);
                    return;
                }
                for (int i = 0; i < 256; i++) {
                    boolean z = cc.f297bs.m58at(i);
                    if ((z && !cc.isNot()) || (!z && cc.isNot())) {
                        opt.map.addChar(i);
                    }
                }
                opt.length.set(1, 1);
                return;
            case 2:
            default:
                throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
            case 3:
                opt.length.set(1, 1);
                return;
            case 4:
                BackRefNode br = (BackRefNode) node;
                if (br.isRecursion()) {
                    opt.length.set(0, Integer.MAX_VALUE);
                    return;
                }
                Node[] nodes = oenv.scanEnv.memNodes;
                int min = getMinMatchLength(nodes[br.backRef]);
                int max3 = getMaxMatchLength(nodes[br.backRef]);
                opt.length.set(min, max3);
                return;
            case 5:
                NodeOptInfo nopt = new NodeOptInfo();
                QuantifierNode qn = (QuantifierNode) node;
                optimizeNodeLeft(qn.target, nopt, oenv);
                if (qn.lower == 0 && QuantifierNode.isRepeatInfinite(qn.upper)) {
                    if (oenv.mmd.max == 0 && qn.target.getType() == 3 && qn.greedy) {
                        if (Option.isMultiline(oenv.options)) {
                            opt.anchor.add(32768);
                        } else {
                            opt.anchor.add(16384);
                        }
                    }
                } else if (qn.lower > 0) {
                    opt.copy(nopt);
                    if (nopt.exb.length > 0 && nopt.exb.reachEnd) {
                        int i2 = 2;
                        while (i2 <= qn.lower && !opt.exb.isFull()) {
                            opt.exb.concat(nopt.exb);
                            i2++;
                        }
                        if (i2 < qn.lower) {
                            opt.exb.reachEnd = false;
                        }
                    }
                    if (qn.lower != qn.upper) {
                        opt.exb.reachEnd = false;
                        opt.exm.reachEnd = false;
                    }
                    if (qn.lower > 1) {
                        opt.exm.reachEnd = false;
                    }
                }
                int min2 = MinMaxLen.distanceMultiply(nopt.length.min, qn.lower);
                if (QuantifierNode.isRepeatInfinite(qn.upper)) {
                    max = nopt.length.max > 0 ? Integer.MAX_VALUE : 0;
                } else {
                    max = MinMaxLen.distanceMultiply(nopt.length.max, qn.upper);
                }
                opt.length.set(min2, max);
                return;
            case 6:
                EncloseNode en = (EncloseNode) node;
                switch (en.type) {
                    case 1:
                        int i3 = en.optCount + 1;
                        en.optCount = i3;
                        if (i3 > 5) {
                            int min3 = 0;
                            int max4 = Integer.MAX_VALUE;
                            if (en.isMinFixed()) {
                                min3 = en.minLength;
                            }
                            if (en.isMaxFixed()) {
                                max4 = en.maxLength;
                            }
                            opt.length.set(min3, max4);
                            return;
                        }
                        optimizeNodeLeft(en.target, opt, oenv);
                        if (opt.anchor.isSet(AnchorType.ANYCHAR_STAR_MASK) && BitStatus.bsAt(oenv.scanEnv.backrefedMem, en.regNum)) {
                            opt.anchor.remove(AnchorType.ANYCHAR_STAR_MASK);
                            return;
                        }
                        return;
                    case 2:
                        int save = oenv.options;
                        oenv.options = en.option;
                        optimizeNodeLeft(en.target, opt, oenv);
                        oenv.options = save;
                        return;
                    case 3:
                    default:
                        return;
                    case 4:
                        optimizeNodeLeft(en.target, opt, oenv);
                        return;
                }
            case 7:
                AnchorNode an = (AnchorNode) node;
                switch (an.type) {
                    case 1:
                    case 2:
                    case 4:
                    case 8:
                    case 16:
                    case 32:
                        opt.anchor.add(an.type);
                        return;
                    case 1024:
                        NodeOptInfo nopt2 = new NodeOptInfo();
                        optimizeNodeLeft(an.target, nopt2, oenv);
                        if (nopt2.exb.length > 0) {
                            opt.expr.copy(nopt2.exb);
                        } else if (nopt2.exm.length > 0) {
                            opt.expr.copy(nopt2.exm);
                        }
                        opt.expr.reachEnd = false;
                        if (nopt2.map.value > 0) {
                            opt.map.copy(nopt2.map);
                            return;
                        }
                        return;
                    case 2048:
                    case 4096:
                    case 8192:
                    default:
                        return;
                }
            case 8:
                OptEnvironment nenv = new OptEnvironment();
                NodeOptInfo nopt3 = new NodeOptInfo();
                nenv.copy(oenv);
                ConsAltNode lin = (ConsAltNode) node;
                do {
                    optimizeNodeLeft(lin.car, nopt3, nenv);
                    nenv.mmd.add(nopt3.length);
                    opt.concatLeftNode(nopt3);
                    consAltNode2 = lin.cdr;
                    lin = consAltNode2;
                } while (consAltNode2 != null);
                return;
            case 9:
                NodeOptInfo nopt4 = new NodeOptInfo();
                ConsAltNode aln = (ConsAltNode) node;
                do {
                    optimizeNodeLeft(aln.car, nopt4, oenv);
                    if (aln == node) {
                        opt.copy(nopt4);
                    } else {
                        opt.altMerge(nopt4, oenv);
                    }
                    consAltNode = aln.cdr;
                    aln = consAltNode;
                } while (consAltNode != null);
                return;
        }
    }

    protected final void setOptimizedInfoFromTree(Node node) {
        NodeOptInfo opt = new NodeOptInfo();
        OptEnvironment oenv = new OptEnvironment();
        oenv.options = this.regex.options;
        oenv.caseFoldFlag = this.regex.caseFoldFlag;
        oenv.scanEnv = this.env;
        oenv.mmd.clear();
        optimizeNodeLeft(node, opt, oenv);
        this.regex.anchor = opt.anchor.leftAnchor & 49157;
        this.regex.anchor |= opt.anchor.rightAnchor & 24;
        if ((this.regex.anchor & 24) != 0) {
            this.regex.anchorDmin = opt.length.min;
            this.regex.anchorDmax = opt.length.max;
        }
        if (opt.exb.length > 0 || opt.exm.length > 0) {
            opt.exb.select(opt.exm);
            if (opt.map.value > 0 && opt.exb.compare(opt.map) > 0) {
                this.regex.setOptimizeMapInfo(opt.map);
                this.regex.setSubAnchor(opt.map.anchor);
                return;
            }
            this.regex.setExactInfo(opt.exb);
            this.regex.setSubAnchor(opt.exb.anchor);
        } else if (opt.map.value > 0) {
            this.regex.setOptimizeMapInfo(opt.map);
            this.regex.setSubAnchor(opt.map.anchor);
        } else {
            this.regex.subAnchor |= opt.anchor.leftAnchor & 2;
            if (opt.length.max == 0) {
                this.regex.subAnchor |= opt.anchor.rightAnchor & 32;
            }
        }
    }
}
