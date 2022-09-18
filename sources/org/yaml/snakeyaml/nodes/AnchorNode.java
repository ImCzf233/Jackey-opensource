package org.yaml.snakeyaml.nodes;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/nodes/AnchorNode.class */
public class AnchorNode extends Node {
    private Node realNode;

    public AnchorNode(Node realNode) {
        super(realNode.getTag(), realNode.getStartMark(), realNode.getEndMark());
        this.realNode = realNode;
    }

    @Override // org.yaml.snakeyaml.nodes.Node
    public NodeId getNodeId() {
        return NodeId.anchor;
    }

    public Node getRealNode() {
        return this.realNode;
    }
}
