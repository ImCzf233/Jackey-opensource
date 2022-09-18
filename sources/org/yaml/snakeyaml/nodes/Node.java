package org.yaml.snakeyaml.nodes;

import java.util.List;
import org.yaml.snakeyaml.comments.CommentLine;
import org.yaml.snakeyaml.error.Mark;

/* loaded from: Jackey Client b2.jar:org/yaml/snakeyaml/nodes/Node.class */
public abstract class Node {
    private Tag tag;
    private Mark startMark;
    protected Mark endMark;
    private String anchor;
    private Class<? extends Object> type = Object.class;
    private boolean twoStepsConstruction = false;
    protected boolean resolved = true;
    protected Boolean useClassConstructor = null;
    private List<CommentLine> inLineComments = null;
    private List<CommentLine> blockComments = null;
    private List<CommentLine> endComments = null;

    public abstract NodeId getNodeId();

    public Node(Tag tag, Mark startMark, Mark endMark) {
        setTag(tag);
        this.startMark = startMark;
        this.endMark = endMark;
    }

    public Tag getTag() {
        return this.tag;
    }

    public Mark getEndMark() {
        return this.endMark;
    }

    public Mark getStartMark() {
        return this.startMark;
    }

    public void setTag(Tag tag) {
        if (tag == null) {
            throw new NullPointerException("tag in a Node is required.");
        }
        this.tag = tag;
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Class<? extends Object> getType() {
        return this.type;
    }

    public void setType(Class<? extends Object> type) {
        if (!type.isAssignableFrom(this.type)) {
            this.type = type;
        }
    }

    public void setTwoStepsConstruction(boolean twoStepsConstruction) {
        this.twoStepsConstruction = twoStepsConstruction;
    }

    public boolean isTwoStepsConstruction() {
        return this.twoStepsConstruction;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public boolean useClassConstructor() {
        if (this.useClassConstructor == null) {
            if ((!this.tag.isSecondary() && this.resolved && !Object.class.equals(this.type) && !this.tag.equals(Tag.NULL)) || this.tag.isCompatible(getType())) {
                return true;
            }
            return false;
        }
        return this.useClassConstructor.booleanValue();
    }

    public void setUseClassConstructor(Boolean useClassConstructor) {
        this.useClassConstructor = useClassConstructor;
    }

    @Deprecated
    public boolean isResolved() {
        return this.resolved;
    }

    public String getAnchor() {
        return this.anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public List<CommentLine> getInLineComments() {
        return this.inLineComments;
    }

    public void setInLineComments(List<CommentLine> inLineComments) {
        this.inLineComments = inLineComments;
    }

    public List<CommentLine> getBlockComments() {
        return this.blockComments;
    }

    public void setBlockComments(List<CommentLine> blockComments) {
        this.blockComments = blockComments;
    }

    public List<CommentLine> getEndComments() {
        return this.endComments;
    }

    public void setEndComments(List<CommentLine> endComments) {
        this.endComments = endComments;
    }
}
