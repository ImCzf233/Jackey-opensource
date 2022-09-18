package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils;

import com.viaversion.viaversion.libs.javassist.compiler.TokenId;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Particle.kt */
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n��\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0001\u0018�� \u00152\b\u0012\u0004\u0012\u00020��0\u0001:\u0001\u0015B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014¨\u0006\u0016"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType;", "", "typeName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getTypeName", "()Ljava/lang/String;", "performRendering", "", "x", "", "y", "rad", "col", "Ljava/awt/Color;", "SOLID_CIRCLE", "CIRCLE", "SOLID_RECT", "RECT", "SOLID_TRIANGLE", "TRIANGLE", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType.class */
public enum ShapeType {
    SOLID_CIRCLE { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType.SOLID_CIRCLE
        @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType
        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkNotNullParameter(col, "col");
            RenderUtils.drawFilledCircle(x, y, rad, col);
        }
    },
    CIRCLE { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType.CIRCLE
        @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType
        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkNotNullParameter(col, "col");
            RenderUtils.drawCircle(x, y, rad, 0.5f, 0, TokenId.EXOR_E, col);
        }
    },
    SOLID_RECT { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType.SOLID_RECT
        @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType
        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkNotNullParameter(col, "col");
            RenderUtils.drawRect(x - (rad / 2.0f), y - (rad / 2.0f), x + (rad / 2.0f), y + (rad / 2.0f), col.getRGB());
        }
    },
    RECT { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType.RECT
        @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType
        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkNotNullParameter(col, "col");
            RenderUtils.drawBorder(x - (rad / 2.0f), y - (rad / 2.0f), x + (rad / 2.0f), y + (rad / 2.0f), 0.5f, col.getRGB());
        }
    },
    SOLID_TRIANGLE { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType.SOLID_TRIANGLE
        @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType
        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkNotNullParameter(col, "col");
            RenderUtils.drawTriAngle(x, y, rad, 3.0f, col, true);
        }
    },
    TRIANGLE { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType.TRIANGLE
        @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.elements.targets.utils.ShapeType
        public void performRendering(float x, float y, float rad, @NotNull Color col) {
            Intrinsics.checkNotNullParameter(col, "col");
            RenderUtils.drawTriAngle(x, y, rad, 3.0f, col, false);
        }
    };
    
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String typeName;

    public abstract void performRendering(float f, float f2, float f3, @NotNull Color color);

    /* synthetic */ ShapeType(String typeName, DefaultConstructorMarker $constructor_marker) {
        this(typeName);
    }

    ShapeType(String typeName) {
        this.typeName = typeName;
    }

    @NotNull
    public final String getTypeName() {
        return this.typeName;
    }

    /* compiled from: Particle.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0018\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType$Companion;", "", "()V", "getTypeFromName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType;", "name", "", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.targets.utils.ShapeType$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/targets/utils/ShapeType$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @Nullable
        public final ShapeType getTypeFromName(@NotNull String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            ShapeType[] values = ShapeType.values();
            int i = 0;
            int length = values.length;
            while (i < length) {
                ShapeType it = values[i];
                i++;
                if (StringsKt.equals(it.getTypeName(), name, true)) {
                    return it;
                }
            }
            return null;
        }
    }
}
