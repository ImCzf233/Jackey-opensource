package kotlin.p002io;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Exceptions.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0016\u0018��2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n��\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n��\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, m53d2 = {"Lkotlin/io/FileSystemException;", "Ljava/io/IOException;", "file", "Ljava/io/File;", "other", "reason", "", "(Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V", "getFile", "()Ljava/io/File;", "getOther", "getReason", "()Ljava/lang/String;", "kotlin-stdlib"})
/* renamed from: kotlin.io.FileSystemException */
/* loaded from: Jackey Client b2.jar:kotlin/io/FileSystemException.class */
public class FileSystemException extends IOException {
    @NotNull
    private final File file;
    @Nullable
    private final File other;
    @Nullable
    private final String reason;

    public /* synthetic */ FileSystemException(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? null : file2, (i & 4) != 0 ? null : str);
    }

    @NotNull
    public final File getFile() {
        return this.file;
    }

    @Nullable
    public final File getOther() {
        return this.other;
    }

    @Nullable
    public final String getReason() {
        return this.reason;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FileSystemException(@org.jetbrains.annotations.NotNull java.io.File r6, @org.jetbrains.annotations.Nullable java.io.File r7, @org.jetbrains.annotations.Nullable java.lang.String r8) {
        /*
            r5 = this;
            r0 = r6
            java.lang.String r1 = "file"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r0 = r5
            r1 = r6
            r2 = r7
            r3 = r8
            java.lang.String r1 = kotlin.p002io.ExceptionsKt.access$constructMessage(r1, r2, r3)
            r0.<init>(r1)
            r0 = r5
            r1 = r6
            r0.file = r1
            r0 = r5
            r1 = r7
            r0.other = r1
            r0 = r5
            r1 = r8
            r0.reason = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FileSystemException.<init>(java.io.File, java.io.File, java.lang.String):void");
    }
}
