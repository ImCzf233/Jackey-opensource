package net.ccbluex.liquidbounce.utils.render;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/render/WorldToScreen.class */
public class WorldToScreen {
    public static Matrix4f getMatrix(int matrix) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(matrix, floatBuffer);
        return new Matrix4f().load(floatBuffer);
    }

    public static Vector2f worldToScreen(Vector3f pointInWorld, int screenWidth, int screenHeight) {
        return worldToScreen(pointInWorld, getMatrix(2982), getMatrix(2983), screenWidth, screenHeight);
    }

    public static Vector2f worldToScreen(Vector3f pointInWorld, Matrix4f view, Matrix4f projection, int screenWidth, int screenHeight) {
        Vector4f clipSpacePos = multiply(multiply(new Vector4f(pointInWorld.x, pointInWorld.y, pointInWorld.z, 1.0f), view), projection);
        Vector3f ndcSpacePos = new Vector3f(clipSpacePos.x / clipSpacePos.w, clipSpacePos.y / clipSpacePos.w, clipSpacePos.z / clipSpacePos.w);
        float screenX = ((ndcSpacePos.x + 1.0f) / 2.0f) * screenWidth;
        float screenY = ((1.0f - ndcSpacePos.y) / 2.0f) * screenHeight;
        if (ndcSpacePos.z < -1.0d || ndcSpacePos.z > 1.0d) {
            return null;
        }
        return new Vector2f(screenX, screenY);
    }

    public static Vector4f multiply(Vector4f vec, Matrix4f mat) {
        return new Vector4f((vec.x * mat.m00) + (vec.y * mat.m10) + (vec.z * mat.m20) + (vec.w * mat.m30), (vec.x * mat.m01) + (vec.y * mat.m11) + (vec.z * mat.m21) + (vec.w * mat.m31), (vec.x * mat.m02) + (vec.y * mat.m12) + (vec.z * mat.m22) + (vec.w * mat.m32), (vec.x * mat.m03) + (vec.y * mat.m13) + (vec.z * mat.m23) + (vec.w * mat.m33));
    }
}
