package net.ccbluex.liquidbounce.utils.render;

import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/SafeVertexBuffer.class */
public class SafeVertexBuffer extends VertexBuffer {
    public SafeVertexBuffer(VertexFormat vertexFormatIn) {
        super(vertexFormatIn);
    }

    protected void finalize() throws Throwable {
        func_177362_c();
    }
}
