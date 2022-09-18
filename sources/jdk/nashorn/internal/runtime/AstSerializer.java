package jdk.nashorn.internal.runtime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: Jackey Client b2.jar:jdk/nashorn/internal/runtime/AstSerializer.class */
public final class AstSerializer {
    private static final int COMPRESSION_LEVEL = Options.getIntProperty("nashorn.serialize.compression", 4);

    AstSerializer() {
    }

    public static byte[] serialize(FunctionNode fn) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(COMPRESSION_LEVEL);
        try {
            try {
                ObjectOutputStream oout = new ObjectOutputStream(new DeflaterOutputStream(out, deflater));
                Throwable th = null;
                try {
                    oout.writeObject(fn);
                    if (oout != null) {
                        if (0 != 0) {
                            try {
                                oout.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        } else {
                            oout.close();
                        }
                    }
                    return out.toByteArray();
                } finally {
                }
            } finally {
                deflater.end();
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception serializing function", e);
        }
    }
}
