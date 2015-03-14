package me.dags.daflightliteloader.minecraft.extended;

import me.dags.daflight.DaFlight;
import net.minecraft.util.MovementInputFromOptions;

/**
 * @author dags_ <dags@dags.me>
 */

public class DFMovementController extends MovementInputFromOptions
{
    public boolean block = false;
    public boolean wasSneaking = false;

    public DFMovementController()
    {
        super(DaFlight.getMC().getGameSettings());
    }

    @Override
    public void updatePlayerMoveState()
    {
        super.updatePlayerMoveState();
        if (block)
        {
            wasSneaking = sneak;

            super.sneak = false;
            super.jump = false;

            block = false;
        }
    }
}
