package me.dags.daflight.utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import static com.mumfrey.liteloader.gl.GL.*;

public class GLHelper
{
    public static void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2)
    {
        glDisableLighting();
        glEnableBlend();
        glAlphaFunc(GL_GREATER, 0F);
        glEnableTexture2D();
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float texMapScale = 0.001953125F;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(x, y + height, 0, u * texMapScale, v2 * texMapScale);
        worldRenderer.addVertexWithUV(x + width, y + height, 0, u2 * texMapScale, v2 * texMapScale);
        worldRenderer.addVertexWithUV(x + width, y, 0, u2 * texMapScale, v * texMapScale);
        worldRenderer.addVertexWithUV(x, y, 0, u * texMapScale, v * texMapScale);
        tessellator.draw();
        glDisableBlend();
    }
}
