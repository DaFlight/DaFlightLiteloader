package me.dags.daflightliteloader.minecraft.extended;

import me.dags.daflight.DaFlight;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;

/**
 * @author dags_ <dags@dags.me>
 */

public class EntityDaFlyer extends EntityPlayerSP
{
    private DFMovementInput movementInput;
    private int ticksSinceMovePacket = 0;
    private boolean wasSneaking = false;

    private double oldPosX;
    private double oldMinY;
    private double oldPosZ;
    private double oldRotationYaw;
    private double oldRotationPitch;

    public EntityDaFlyer(Minecraft mc, World world, NetHandlerPlayClient netHandlerPlayClient, StatFileWriter fileWriter)
    {
        super(mc, world, netHandlerPlayClient, fileWriter);
        this.movementInput = new DFMovementInput();
    }

    @Override
    public void onLivingUpdate()
    {
        if (super.movementInput != this.movementInput)
            super.movementInput = this.movementInput;
        this.movementInput.block = DaFlight.get().DFController.flyModOn;
        super.onLivingUpdate();
    }

    @Override
    public void onUpdateWalkingPlayer()
    {
        if (DaFlight.get().DFController.softFallOn())
        {
            boolean sneaking = this.isSneaking();
            if (sneaking != wasSneaking)
            {
                if (sneaking)
                {
                    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
                }
                else
                {
                    sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
                wasSneaking = sneaking;
            }
            double xChange = this.posX - this.oldPosX;
            double yChange = getEntityBoundingBox().minY - oldMinY;
            double zChange = posZ - oldPosZ;
            double rotationChange = rotationYaw - oldRotationYaw;
            double pitchChange = rotationPitch - oldRotationPitch;
            boolean sendMovementUpdate = xChange * xChange + yChange * yChange + zChange * zChange > 9.0E-4D || ticksSinceMovePacket >= 20;
            boolean sendLookUpdate = rotationChange != 0.0D || pitchChange != 0.0D;
            boolean ground = true;
            if (DaFlight.get().DFController.flyModOn)
            {
                ground = !this.capabilities.allowFlying;
            }
            if (sendMovementUpdate && sendLookUpdate)
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, ground));
            }
            else if (sendMovementUpdate)
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, ground));
            }
            else if (sendLookUpdate)
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, ground));
            }
            else
            {
                sendQueue.addToSendQueue(new C03PacketPlayer(ground));
            }
            ++ticksSinceMovePacket;
            if (sendMovementUpdate)
            {
                oldPosX = posX;
                oldMinY = getEntityBoundingBox().minY;
                oldPosZ = posZ;
                ticksSinceMovePacket = 0;
            }
            if (sendLookUpdate)
            {
                oldRotationPitch = rotationPitch;
                oldRotationYaw = rotationYaw;
            }
        }
        else
        {
            super.onUpdateWalkingPlayer();
        }
    }

    @Override
    public void moveEntityWithHeading(float f1, float f2)
    {
        super.moveEntityWithHeading(f1, f2);
        if (this.isOnLadder() && !DaFlight.get().DFController.flyModOn && DaFlight.get().DFController.sprintModOn)
        {
            if (this.isCollidedHorizontally)
            {
                if (this.rotationPitch < -30F)
                {
                    double speed = DaFlight.get().DFController.getSpeed();
                    this.moveEntity(0D, speed, 0D);
                }
            }
            else if (!isSneaking() && this.rotationPitch > 40F)
            {
                double speed = DaFlight.get().DFController.getSpeed();
                this.moveEntity(0D, -speed, 0D);
            }
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        if (DaFlight.get().DFController.softFallOn())
            return;
        super.fall(distance, damageMultiplier);
    }

    @Override
    public float getFovModifier()
    {
        if (!DaFlight.getConfig().disabled && DaFlight.get().DFController.flyModOn)
        {
            if (!this.capabilities.isFlying)
                this.capabilities.isFlying = true;
            if (DaFlight.getConfig().disableFov)
                return 1.0F;
        }
        return super.getFovModifier();
    }

    @Override
    public boolean isOnLadder()
    {
        return !DaFlight.get().DFController.flyModOn && super.isOnLadder();
    }

    @Override
    public void jump()
    {
        if (DaFlight.get().DFController.sprintModOn && !this.capabilities.isFlying)
            this.motionY = 0.42F + 1.25 * DaFlight.getConfig().jumpModifier * DaFlight.get().DFController.getSpeed();
        else
            super.jump();
    }

    @Override
    public float getToolDigEfficiency(Block b)
    {
        float f = super.getToolDigEfficiency(b);
        if (DaFlight.get().DFController.flyModOn && (!this.onGround || (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))))
            f *= 5.0F;
        return f;
    }

    @Override
    public boolean isSneaking()
    {
        return (DaFlight.get().DFController.flyModOn && movementInput.wasSneaking) || super.isSneaking();
    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z)
    {
        if (DaFlight.get().DFController.noClipOn && DaFlight.get().DFController.flyModOn)
            this.noClip = true;
        return super.pushOutOfBlocks(x, y, z);
    }

    @Override
    public void moveEntity(double x, double y, double z)
    {
        if (DaFlight.get().DFController.noClipOn && DaFlight.get().DFController.flyModOn)
            this.noClip = true;
        super.moveEntity(x, y, z);
    }

    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return !DaFlight.get().DFController.noClipOn && super.isEntityInsideOpaqueBlock();
    }
}
