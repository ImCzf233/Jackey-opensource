package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.p001ir.CompileUnitHolder;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/codegen/ReplaceCompileUnits.class */
public abstract class ReplaceCompileUnits extends SimpleNodeVisitor {
    static final /* synthetic */ boolean $assertionsDisabled;

    abstract CompileUnit getReplacement(CompileUnit compileUnit);

    static {
        $assertionsDisabled = !ReplaceCompileUnits.class.desiredAssertionStatus();
    }

    CompileUnit getExistingReplacement(CompileUnitHolder node) {
        CompileUnit oldUnit = node.getCompileUnit();
        if ($assertionsDisabled || oldUnit != null) {
            CompileUnit newUnit = getReplacement(oldUnit);
            if (!$assertionsDisabled && newUnit == null) {
                throw new AssertionError();
            }
            return newUnit;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode node) {
        return node.setCompileUnit(this.f247lc, getExistingReplacement(node));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveLiteralNode(LiteralNode<?> node) {
        if (node instanceof LiteralNode.ArrayLiteralNode) {
            LiteralNode.ArrayLiteralNode aln = (LiteralNode.ArrayLiteralNode) node;
            if (aln.getSplitRanges() == null) {
                return node;
            }
            List<Splittable.SplitRange> newArrayUnits = new ArrayList<>();
            for (Splittable.SplitRange au : aln.getSplitRanges()) {
                newArrayUnits.add(new Splittable.SplitRange(getExistingReplacement(au), au.getLow(), au.getHigh()));
            }
            return aln.setSplitRanges(this.f247lc, newArrayUnits);
        }
        return node;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveObjectNode(ObjectNode objectNode) {
        List<Splittable.SplitRange> ranges = objectNode.getSplitRanges();
        if (ranges != null) {
            List<Splittable.SplitRange> newRanges = new ArrayList<>();
            for (Splittable.SplitRange range : ranges) {
                newRanges.add(new Splittable.SplitRange(getExistingReplacement(range), range.getLow(), range.getHigh()));
            }
            return objectNode.setSplitRanges(this.f247lc, newRanges);
        }
        return super.leaveObjectNode(objectNode);
    }
}
