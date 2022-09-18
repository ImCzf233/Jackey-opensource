package net.ccbluex.liquidbounce.p004ui.client.hud.element.elements;

import com.google.gson.JsonElement;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p004ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/* compiled from: Image.kt */
@ElementInfo(name = "Image")
@Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018�� \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020��2\u0006\u0010\u0005\u001a\u00020\u000fJ\u0010\u0010\u000e\u001a\u00020��2\u0006\u0010\u0005\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n��R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0012"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "height", "", "image", "Lnet/ccbluex/liquidbounce/value/TextValue;", "resourceLocation", "Lnet/minecraft/util/ResourceLocation;", "width", "createElement", "", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setImage", "Ljava/io/File;", "", "Companion", "LiquidBounce"})
/* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Image */
/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Image.class */
public final class Image extends Element {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final TextValue image = new TextValue() { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Image$image$1
        /* JADX INFO: Access modifiers changed from: package-private */
        {
            super("Image", "");
        }

        @Override // net.ccbluex.liquidbounce.value.TextValue, net.ccbluex.liquidbounce.value.Value
        public void fromJson(@NotNull JsonElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            super.fromJson(element);
            if (!(get().length() == 0)) {
                Image.this.setImage(get());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onChanged(@NotNull String oldValue, @NotNull String newValue) {
            Intrinsics.checkNotNullParameter(oldValue, "oldValue");
            Intrinsics.checkNotNullParameter(newValue, "newValue");
            if (!(get().length() == 0)) {
                Image.this.setImage(get());
            }
        }
    };
    @NotNull
    private final ResourceLocation resourceLocation = new ResourceLocation(RandomUtils.randomNumber(128));
    private int width = 64;
    private int height = 64;

    public Image() {
        super(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* compiled from: Image.kt */
    @Metadata(m51mv = {1, 6, 0}, m52k = 1, m49xi = 48, m54d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, m53d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "LiquidBounce"})
    /* renamed from: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Image$Companion */
    /* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Image$Companion.class */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private Companion() {
        }

        @NotNull
        /* renamed from: default */
        public final Image m2851default() {
            Image image = new Image();
            image.setX(0.0d);
            image.setY(0.0d);
            return image;
        }
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        GlStateManager.func_179118_c();
        RenderUtils.drawImage(this.resourceLocation, 0, 0, this.width / 2, this.height / 2);
        GlStateManager.func_179141_d();
        return new Border(0.0f, 0.0f, this.width / 2.0f, this.height / 2.0f);
    }

    @Override // net.ccbluex.liquidbounce.p004ui.client.hud.element.Element
    public boolean createElement() {
        File file = MiscUtils.openFileChooser();
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            MiscUtils.showErrorPopup("Error", "The file does not exist.");
            return false;
        } else if (file.isDirectory()) {
            MiscUtils.showErrorPopup("Error", "The file is a directory.");
            return false;
        } else {
            setImage(file);
            return true;
        }
    }

    public final Image setImage(String image) {
        try {
            this.image.changeValue(image);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(image));
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            byteArrayInputStream.close();
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
            MinecraftInstance.f362mc.func_110434_K().func_110579_a(this.resourceLocation, new DynamicTexture(bufferedImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @NotNull
    public final Image setImage(@NotNull File image) {
        Intrinsics.checkNotNullParameter(image, "image");
        try {
            String encodeToString = Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));
            Intrinsics.checkNotNullExpressionValue(encodeToString, "getEncoder().encodeToStr…AllBytes(image.toPath()))");
            setImage(encodeToString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
