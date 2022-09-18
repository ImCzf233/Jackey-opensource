package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Rotation.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��,\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\b\u0086\b\u0018��2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n��\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, m53d2 = {"Lnet/ccbluex/liquidbounce/utils/PlaceRotation;", "", "placeInfo", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "(Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;Lnet/ccbluex/liquidbounce/utils/Rotation;)V", "getPlaceInfo", "()Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "getRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "LiquidBounce"})
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/PlaceRotation.class */
public final class PlaceRotation {
    @NotNull
    private final PlaceInfo placeInfo;
    @NotNull
    private final Rotation rotation;

    @NotNull
    public final PlaceInfo component1() {
        return this.placeInfo;
    }

    @NotNull
    public final Rotation component2() {
        return this.rotation;
    }

    @NotNull
    public final PlaceRotation copy(@NotNull PlaceInfo placeInfo, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter(placeInfo, "placeInfo");
        Intrinsics.checkNotNullParameter(rotation, "rotation");
        return new PlaceRotation(placeInfo, rotation);
    }

    public static /* synthetic */ PlaceRotation copy$default(PlaceRotation placeRotation, PlaceInfo placeInfo, Rotation rotation, int i, Object obj) {
        if ((i & 1) != 0) {
            placeInfo = placeRotation.placeInfo;
        }
        if ((i & 2) != 0) {
            rotation = placeRotation.rotation;
        }
        return placeRotation.copy(placeInfo, rotation);
    }

    @NotNull
    public String toString() {
        return "PlaceRotation(placeInfo=" + this.placeInfo + ", rotation=" + this.rotation + ')';
    }

    public int hashCode() {
        int result = this.placeInfo.hashCode();
        return (result * 31) + this.rotation.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PlaceRotation)) {
            return false;
        }
        PlaceRotation placeRotation = (PlaceRotation) other;
        return Intrinsics.areEqual(this.placeInfo, placeRotation.placeInfo) && Intrinsics.areEqual(this.rotation, placeRotation.rotation);
    }

    public PlaceRotation(@NotNull PlaceInfo placeInfo, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter(placeInfo, "placeInfo");
        Intrinsics.checkNotNullParameter(rotation, "rotation");
        this.placeInfo = placeInfo;
        this.rotation = rotation;
    }

    @NotNull
    public final PlaceInfo getPlaceInfo() {
        return this.placeInfo;
    }

    @NotNull
    public final Rotation getRotation() {
        return this.rotation;
    }
}
