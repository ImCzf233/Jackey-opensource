package kotlin.p002io;

import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\b\u0002\u0018��2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, m53d2 = {"Lkotlin/io/ExposingBufferByteArrayOutputStream;", "Ljava/io/ByteArrayOutputStream;", "size", "", "(I)V", "buffer", "", "getBuffer", "()[B", "kotlin-stdlib"})
/* renamed from: kotlin.io.ExposingBufferByteArrayOutputStream */
/* loaded from: Jackey Client b2.jar:kotlin/io/ExposingBufferByteArrayOutputStream.class */
final class FileReadWrite extends ByteArrayOutputStream {
    public FileReadWrite(int size) {
        super(size);
    }

    @NotNull
    public final byte[] getBuffer() {
        byte[] buf = this.buf;
        Intrinsics.checkNotNullExpressionValue(buf, "buf");
        return buf;
    }
}
