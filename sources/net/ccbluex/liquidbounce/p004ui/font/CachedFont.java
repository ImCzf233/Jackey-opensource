package net.ccbluex.liquidbounce.p004ui.font;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/* compiled from: CachedFont.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��,\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\b\n��\n\u0002\u0010\t\n��\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\b\u0086\b\u0018��2\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J'\u0010\u0016\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0004J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001e"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "", "displayList", "", "lastUsage", "", "deleted", "", "(IJZ)V", "getDeleted", "()Z", "setDeleted", "(Z)V", "getDisplayList", "()I", "getLastUsage", "()J", "setLastUsage", "(J)V", "component1", "component2", "component3", "copy", "equals", "other", "finalize", "", "hashCode", "toString", "", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.font.CachedFont */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/font/CachedFont.class */
public final class CachedFont {
    private final int displayList;
    private long lastUsage;
    private boolean deleted;

    public final int component1() {
        return this.displayList;
    }

    public final long component2() {
        return this.lastUsage;
    }

    public final boolean component3() {
        return this.deleted;
    }

    @NotNull
    public final CachedFont copy(int displayList, long lastUsage, boolean deleted) {
        return new CachedFont(displayList, lastUsage, deleted);
    }

    public static /* synthetic */ CachedFont copy$default(CachedFont cachedFont, int i, long j, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = cachedFont.displayList;
        }
        if ((i2 & 2) != 0) {
            j = cachedFont.lastUsage;
        }
        if ((i2 & 4) != 0) {
            z = cachedFont.deleted;
        }
        return cachedFont.copy(i, j, z);
    }

    @NotNull
    public String toString() {
        return "CachedFont(displayList=" + this.displayList + ", lastUsage=" + this.lastUsage + ", deleted=" + this.deleted + ')';
    }

    public int hashCode() {
        int result = Integer.hashCode(this.displayList);
        int hashCode = ((result * 31) + Long.hashCode(this.lastUsage)) * 31;
        boolean z = this.deleted;
        if (z) {
            z = true;
        }
        int i = z ? 1 : 0;
        int i2 = z ? 1 : 0;
        int result2 = hashCode + i;
        return result2;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CachedFont)) {
            return false;
        }
        CachedFont cachedFont = (CachedFont) other;
        return this.displayList == cachedFont.displayList && this.lastUsage == cachedFont.lastUsage && this.deleted == cachedFont.deleted;
    }

    public CachedFont(int displayList, long lastUsage, boolean deleted) {
        this.displayList = displayList;
        this.lastUsage = lastUsage;
        this.deleted = deleted;
    }

    public /* synthetic */ CachedFont(int i, long j, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, j, (i2 & 4) != 0 ? false : z);
    }

    public final int getDisplayList() {
        return this.displayList;
    }

    public final long getLastUsage() {
        return this.lastUsage;
    }

    public final void setLastUsage(long j) {
        this.lastUsage = j;
    }

    public final boolean getDeleted() {
        return this.deleted;
    }

    public final void setDeleted(boolean z) {
        this.deleted = z;
    }

    protected final void finalize() {
        if (!this.deleted) {
            GL11.glDeleteLists(this.displayList, 1);
        }
    }
}
