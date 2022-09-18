package net.ccbluex.liquidbounce.utils.render;

import net.vitox.ParticleGenerator;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/ParticleUtils.class */
public final class ParticleUtils {
    private static final ParticleGenerator particleGenerator = new ParticleGenerator(100);

    public static void drawParticles(int mouseX, int mouseY) {
        particleGenerator.draw(mouseX, mouseY);
    }
}
