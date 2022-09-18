package jdk.nashorn.internal.p001ir;

/* renamed from: jdk.nashorn.internal.ir.JoinPredecessor */
/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/ir/JoinPredecessor.class */
public interface JoinPredecessor {
    JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion);

    LocalVariableConversion getLocalVariableConversion();
}
