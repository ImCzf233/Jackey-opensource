package kotlin.p002io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FileTreeWalk.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 5, m49xi = 49, m54d1 = {"��\u0014\n��\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010��\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0007"}, m53d2 = {"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"}, m48xs = "kotlin/io/FilesKt")
/* renamed from: kotlin.io.FilesKt__FileTreeWalkKt */
/* loaded from: Jackey Client b2.jar:kotlin/io/FilesKt__FileTreeWalkKt.class */
public class FilesKt__FileTreeWalkKt extends FilesKt__FileReadWriteKt {
    public static /* synthetic */ FileTreeWalk walk$default(File file, FileWalkDirection fileWalkDirection, int i, Object obj) {
        if ((i & 1) != 0) {
            fileWalkDirection = FileWalkDirection.TOP_DOWN;
        }
        return FilesKt.walk(file, fileWalkDirection);
    }

    @NotNull
    public static final FileTreeWalk walk(@NotNull File $this$walk, @NotNull FileWalkDirection direction) {
        Intrinsics.checkNotNullParameter($this$walk, "<this>");
        Intrinsics.checkNotNullParameter(direction, "direction");
        return new FileTreeWalk($this$walk, direction);
    }

    @NotNull
    public static final FileTreeWalk walkTopDown(@NotNull File $this$walkTopDown) {
        Intrinsics.checkNotNullParameter($this$walkTopDown, "<this>");
        return FilesKt.walk($this$walkTopDown, FileWalkDirection.TOP_DOWN);
    }

    @NotNull
    public static final FileTreeWalk walkBottomUp(@NotNull File $this$walkBottomUp) {
        Intrinsics.checkNotNullParameter($this$walkBottomUp, "<this>");
        return FilesKt.walk($this$walkBottomUp, FileWalkDirection.BOTTOM_UP);
    }
}
