package me.dags.daflightliteloader.minecraft.extended;

import me.dags.daflight.DaFlight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

/**
 * @author dags_ <dags@dags.me>
 */

public class DFRenderGlobal extends RenderGlobal
{
    public DFRenderGlobal(Minecraft mcIn)
    {
        super(mcIn);
    }

    @Override
    public void setupTerrain(Entity viewEntity, double ticks, ICamera camera, int frameCount, boolean spectator)
    {
        if (DaFlight.get().DFController.noClipOn)
        {
            spectator = true;
        }
        super.setupTerrain(viewEntity, ticks, camera, frameCount, spectator);
    }
}
