package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/shader/shaders/GlowShader.class */
public final class GlowShader extends FramebufferShader {
    public static final GlowShader GLOW_SHADER = new GlowShader();

    public GlowShader() {
        super("glow.frag");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void setupUniforms() {
        setupUniform("texture");
        setupUniform("texelSize");
        setupUniform("color");
        setupUniform("divider");
        setupUniform("radius");
        setupUniform("maxSample");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void updateUniforms() {
        GL20.glUniform1i(getUniform("texture"), 0);
        GL20.glUniform2f(getUniform("texelSize"), (1.0f / f362mc.field_71443_c) * this.radius * this.quality, (1.0f / f362mc.field_71440_d) * this.radius * this.quality);
        GL20.glUniform3f(getUniform("color"), this.red, this.green, this.blue);
        GL20.glUniform1f(getUniform("divider"), 140.0f);
        GL20.glUniform1f(getUniform("radius"), this.radius);
        GL20.glUniform1f(getUniform("maxSample"), 10.0f);
    }
}
