package jdk.nashorn.internal.runtime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.InflaterInputStream;
import jdk.nashorn.internal.p001ir.FunctionNode;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/AstDeserializer.class */
public final class AstDeserializer {
    AstDeserializer() {
    }

    public static FunctionNode deserialize(byte[] serializedAst) {
        try {
            return (FunctionNode) new ObjectInputStream(new InflaterInputStream(new ByteArrayInputStream(serializedAst))).readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError("Unexpected exception deserializing function", e);
        }
    }
}
