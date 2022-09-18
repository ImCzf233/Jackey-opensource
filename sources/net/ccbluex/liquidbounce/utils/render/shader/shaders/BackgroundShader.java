package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL20;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/shader/shaders/BackgroundShader.class */
public final class BackgroundShader extends Shader {
    public static final BackgroundShader BACKGROUND_SHADER = new BackgroundShader();
    private float time;

    public BackgroundShader() {
        super("background.frag");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void setupUniforms() {
        setupUniform("iResolution");
        setupUniform("iTime");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void updateUniforms() {
        new ScaledResolution(f362mc);
        int resolutionID = getUniform("iResolution");
        if (resolutionID > -1) {
            GL20.glUniform2f(resolutionID, Display.getWidth(), Display.getHeight());
        }
        int timeID = getUniform("iTime");
        if (timeID > -1) {
            GL20.glUniform1f(timeID, this.time);
        }
        this.time += 0.005f * RenderUtils.deltaTime;
    }
}
