package net.ccbluex.liquidbounce.p004ui.client.hud.element;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Element.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\r\u0018�� \u000f2\u00020\u0001:\u0003\u000f\u0010\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n��\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "", "horizontal", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "vertical", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "getHorizontal", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "setHorizontal", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;)V", "getVertical", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "setVertical", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "Companion", "Horizontal", "Vertical", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Side */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side.class */
public final class Side {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Horizontal horizontal;
    @NotNull
    private Vertical vertical;

    public Side(@NotNull Horizontal horizontal, @NotNull Vertical vertical) {
        Intrinsics.checkNotNullParameter(horizontal, "horizontal");
        Intrinsics.checkNotNullParameter(vertical, "vertical");
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @NotNull
    public final Horizontal getHorizontal() {
        return this.horizontal;
    }

    public final void setHorizontal(@NotNull Horizontal horizontal) {
        Intrinsics.checkNotNullParameter(horizontal, "<set-?>");
        this.horizontal = horizontal;
    }

    @NotNull
    public final Vertical getVertical() {
        return this.vertical;
    }

    public final void setVertical(@NotNull Vertical vertical) {
        Intrinsics.checkNotNullParameter(vertical, "<set-?>");
        this.vertical = vertical;
    }

    /* compiled from: Element.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Side$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        /* renamed from: default */
        public final Side m2845default() {
            return new Side(Horizontal.LEFT, Vertical.UP);
        }
    }

    /* compiled from: Element.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018�� \n2\b\u0012\u0004\u0012\u00020��0\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "LEFT", "MIDDLE", "RIGHT", "Companion", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Side$Horizontal */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal.class */
    public enum Horizontal {
        LEFT("Left"),
        MIDDLE("Middle"),
        RIGHT("Right");
        
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final String sideName;

        @JvmStatic
        @Nullable
        public static final Horizontal getByName(@NotNull String name) {
            return Companion.getByName(name);
        }

        Horizontal(String sideName) {
            this.sideName = sideName;
        }

        @NotNull
        public final String getSideName() {
            return this.sideName;
        }

        /* compiled from: Element.kt */
        @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "name", "", "LiquidBounce"})
        /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Side$Horizontal$Companion */
        /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal$Companion.class */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            @Nullable
            public final Horizontal getByName(@NotNull String name) {
                Intrinsics.checkNotNullParameter(name, "name");
                Horizontal[] values = Horizontal.values();
                int i = 0;
                int length = values.length;
                while (i < length) {
                    Horizontal it = values[i];
                    i++;
                    if (Intrinsics.areEqual(it.getSideName(), name)) {
                        return it;
                    }
                }
                return null;
            }
        }
    }

    /* compiled from: Element.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018�� \n2\b\u0012\u0004\u0012\u00020��0\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "UP", "MIDDLE", "DOWN", "Companion", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Side$Vertical */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical.class */
    public enum Vertical {
        UP("Up"),
        MIDDLE("Middle"),
        DOWN("Down");
        
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final String sideName;

        @JvmStatic
        @Nullable
        public static final Vertical getByName(@NotNull String name) {
            return Companion.getByName(name);
        }

        Vertical(String sideName) {
            this.sideName = sideName;
        }

        @NotNull
        public final String getSideName() {
            return this.sideName;
        }

        /* compiled from: Element.kt */
        @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "name", "", "LiquidBounce"})
        /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.Side$Vertical$Companion */
        /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical$Companion.class */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            @Nullable
            public final Vertical getByName(@NotNull String name) {
                Intrinsics.checkNotNullParameter(name, "name");
                Vertical[] values = Vertical.values();
                int i = 0;
                int length = values.length;
                while (i < length) {
                    Vertical it = values[i];
                    i++;
                    if (Intrinsics.areEqual(it.getSideName(), name)) {
                        return it;
                    }
                }
                return null;
            }
        }
    }
}
