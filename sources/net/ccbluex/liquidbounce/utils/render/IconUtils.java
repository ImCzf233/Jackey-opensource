package net.ccbluex.liquidbounce.utils.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.LiquidBounce;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/IconUtils.class */
public final class IconUtils {
    public static ByteBuffer[] getFavicon() {
        try {
            return new ByteBuffer[]{readImageToBuffer(IconUtils.class.getResourceAsStream("/assets/minecraft/" + LiquidBounce.CLIENT_NAME.toLowerCase() + "/icon_16x16.png")), readImageToBuffer(IconUtils.class.getResourceAsStream("/assets/minecraft/" + LiquidBounce.CLIENT_NAME.toLowerCase() + "/icon_32x32.png"))};
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        if (imageStream == null) {
            return null;
        }
        BufferedImage bufferedImage = ImageIO.read(imageStream);
        int[] rgb = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), (int[]) null, 0, bufferedImage.getWidth());
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * rgb.length);
        for (int i : rgb) {
            byteBuffer.putInt((i << 8) | ((i >> 24) & 255));
        }
        byteBuffer.flip();
        return byteBuffer;
    }
}
