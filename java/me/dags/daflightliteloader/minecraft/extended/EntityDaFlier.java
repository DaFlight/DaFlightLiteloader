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

public class EntityDaFlier extends EntityPlayerSP
{
    private DaFlightMovement movementInput;
    private int ticksSinceMovePacket = 0;
    private boolean wasSneaking = false;
    private double oldPosX;
    private double oldMinY;
    private double oldPosZ;
    private double oldRotationYaw;
    private double oldRotationPitch;

    public EntityDaFlier(Minecraft mc, World world, NetHandlerPlayClient netHandlerPlayClient, StatFileWriter fileWriter)
    {
        super(mc, world, netHandlerPlayClient, fileWriter);
        this.movementInput = new DaFlightMovement();
    }

    @Override
    public void onLivingUpdate()
    {
        if (super.movementInput != this.movementInput)
            super.movementInput = this.movementInput;
        this.movementInput.block = flyModOn();
        super.onLivingUpdate();
    }

    @Override
    public void onUpdateWalkingPlayer()
    {
        if (DaFlight.get().daPlayer.softFallOn())
        {
            boolean sneaking = super.isSneaking();
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
            if (sendMovementUpdate && sendLookUpdate)
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, true));
            }
            else if (sendMovementUpdate)
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, true));
            }
            else if (sendLookUpdate)
            {
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, true));
            }
            else
            {
                sendQueue.addToSendQueue(new C03PacketPlayer(true));
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
        if (this.isOnLadder() && !flyModOn() && sprintModOn())
        {
            if (this.isCollidedHorizontally)
            {
                if (this.rotationPitch < -30F)
                {
                    double speed = DaFlight.get().daPlayer.getSpeed();
                    this.moveEntity(0D, speed, 0D);
                }
            }
            else if (!isSneaking() && this.rotationPitch > 40F)
            {
                double speed = DaFlight.get().daPlayer.getSpeed();
                this.moveEntity(0D, -speed, 0D);
            }
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        if (DaFlight.get().daPlayer.softFallOn())
            return;
        super.fall(distance, damageMultiplier);
    }

    @Override
    public float getFovModifier()
    {
        if (!DaFlight.getConfig().disabled)
            if (flyModOn())
            {
                if (!this.capabilities.isFlying)
                {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
                return 1.0F;
            }
        return super.getFovModifier();
    }

    @Override
    public boolean isOnLadder()
    {
        return !flyModOn() && super.isOnLadder();
    }

    @Override
    public void jump()
    {
        if (DaFlight.get().daPlayer.sprintModOn && !this.capabilities.isFlying)
            this.motionY = 0.42F + 1.25 * DaFlight.getConfig().jumpModifier * DaFlight.get().daPlayer.getSpeed();
        else
            super.jump();
    }

    @Override
    public float getToolDigEfficiency(Block b)
    {
        float f = super.getToolDigEfficiency(b);
        if (flyModOn() && (!this.onGround || (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))))
            f *= 5.0F;
        return f;
    }

    @Override
    public boolean isSneaking()
    {
        return (flyModOn() && movementInput.wasSneaking) || super.isSneaking();
    }

    public boolean flyModOn()
    {
        return DaFlight.get().daPlayer.flyModOn;
    }

    public boolean sprintModOn()
    {
        return DaFlight.get().daPlayer.sprintModOn;
    }
}
