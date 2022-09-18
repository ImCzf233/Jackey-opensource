package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/shader/shaders/OutlineShader.class */
public final class OutlineShader extends FramebufferShader {
    public static final OutlineShader OUTLINE_SHADER = new OutlineShader();

    public OutlineShader() {
        super("outline.frag");
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
        GL20.glUniform4f(getUniform("color"), this.red, this.green, this.blue, this.alpha);
        GL20.glUniform1f(getUniform("radius"), this.radius);
    }
}
