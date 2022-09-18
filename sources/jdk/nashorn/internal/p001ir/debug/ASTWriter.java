package jdk.nashorn.internal.p001ir.debug;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.Terminal;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Reference;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;

/* renamed from: jdk.nashorn.internal.ir.debug.ASTWriter */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/debug/ASTWriter.class */
public final class ASTWriter {
    private final Node root;
    private static final int TABWIDTH = 4;

    public ASTWriter(Node root) {
        this.root = root;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        printAST(sb, null, null, "root", this.root, 0);
        return sb.toString();
    }

    public Node[] toArray() {
        List<Node> preorder = new ArrayList<>();
        printAST(new StringBuilder(), preorder, null, "root", this.root, 0);
        return (Node[]) preorder.toArray(new Node[preorder.size()]);
    }

    private void printAST(StringBuilder sb, List<Node> preorder, Field field, String name, Node node, int indent) {
        Symbol symbol;
        indent(sb, indent);
        if (node == null) {
            sb.append("[Object ");
            sb.append(name);
            sb.append(" null]\n");
            return;
        }
        if (preorder != null) {
            preorder.add(node);
        }
        boolean isReference = field != null && field.isAnnotationPresent(Reference.class);
        Class<?> clazz = node.getClass();
        String type = clazz.getName();
        String type2 = type.substring(type.lastIndexOf(46) + 1, type.length());
        int truncate = type2.indexOf("Node");
        if (truncate == -1) {
            truncate = type2.indexOf("Statement");
        }
        if (truncate != -1) {
            type2 = type2.substring(0, truncate);
        }
        String type3 = type2.toLowerCase();
        if (isReference) {
            type3 = "ref: " + type3;
        }
        if (node instanceof IdentNode) {
            symbol = ((IdentNode) node).getSymbol();
        } else {
            symbol = null;
        }
        if (symbol != null) {
            type3 = type3 + ">" + symbol;
        }
        if ((node instanceof Block) && ((Block) node).needsScope()) {
            type3 = type3 + " <scope>";
        }
        List<Field> children = new LinkedList<>();
        if (!isReference) {
            enqueueChildren(node, clazz, children);
        }
        String status = "";
        if ((node instanceof Terminal) && ((Terminal) node).isTerminal()) {
            status = status + " Terminal";
        }
        if ((node instanceof Statement) && ((Statement) node).hasGoto()) {
            status = status + " Goto ";
        }
        if (symbol != null) {
            status = status + symbol;
        }
        String status2 = status.trim();
        if (!"".equals(status2)) {
            status2 = " [" + status2 + "]";
        }
        if (symbol != null) {
            String tname = ((Expression) node).getType().toString();
            if (tname.indexOf(46) != -1) {
                tname = tname.substring(tname.lastIndexOf(46) + 1, tname.length());
            }
            status2 = status2 + " (" + tname + ")";
        }
        String status3 = status2 + " @" + Debug.m67id(node);
        if (children.isEmpty()) {
            sb.append("[").append(type3).append(' ').append(name).append(" = '").append(node).append("'").append(status3).append("] ").append('\n');
            return;
        }
        sb.append("[").append(type3).append(' ').append(name).append(' ').append(Token.toString(node.getToken())).append(status3).append("]").append('\n');
        for (Field child : children) {
            if (!child.isAnnotationPresent(Ignore.class)) {
                try {
                    Object value = child.get(node);
                    if (value instanceof Node) {
                        printAST(sb, preorder, child, child.getName(), (Node) value, indent + 1);
                    } else if (value instanceof Collection) {
                        int pos = 0;
                        indent(sb, indent + 1);
                        sb.append('[').append(child.getName()).append("[0..").append(((Collection) value).size()).append("]]").append('\n');
                        for (Node member : (Collection) value) {
                            int i = pos;
                            pos++;
                            printAST(sb, preorder, child, child.getName() + "[" + i + "]", member, indent + 2);
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    Context.printStackTrace(e);
                    return;
                }
            }
        }
    }

    private static void enqueueChildren(Node node, Class<?> nodeClass, List<Field> children) {
        Field[] declaredFields;
        ArrayDeque arrayDeque = new ArrayDeque();
        Class<?> clazz = nodeClass;
        do {
            arrayDeque.push(clazz);
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        if (node instanceof TernaryNode) {
            arrayDeque.push(arrayDeque.removeLast());
        }
        Iterator<Class<?>> iter = node instanceof BinaryNode ? arrayDeque.descendingIterator() : arrayDeque.iterator();
        while (iter.hasNext()) {
            Class<?> c = iter.next();
            for (Field f : c.getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    Object child = f.get(node);
                    if (child != null) {
                        if (child instanceof Node) {
                            children.add(f);
                        } else if ((child instanceof Collection) && !((Collection) child).isEmpty()) {
                            children.add(f);
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    return;
                }
            }
        }
    }

    private static void indent(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(' ');
            }
        }
    }
}
