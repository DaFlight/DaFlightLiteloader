package me.dags.daflightliteloader.minecraft.transformers;

import com.mumfrey.liteloader.transformers.ClassOverlayTransformer;

/**
 * @author dags_ <dags@dags.me>
 */

public class PlayerControllerTransformer extends ClassOverlayTransformer
{
    public PlayerControllerTransformer()
    {
        super("me/dags/daflightliteloader/minecraft/extended/ControllerMPOverlay");
    }
}
