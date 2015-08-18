package me.dags.daflightliteloader.minecraft.extended;

import me.dags.daflight.DaFlight;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Session;
import net.minecraft.world.World;

/**
 * @author dags_ <dags@dags.me>
 */

public class EntityDaFlyer extends EntityClientPlayerMP
{
    private DFMovementController movementInput;
    private int ticksSinceMovePacket = 0;
    private boolean wasSneaking = false;

    private double oldPosX;
    private double oldMinY;
    private double oldPosZ;
    private double oldRotationYaw;
    private double oldRotationPitch;

    public EntityDaFlyer(Minecraft mc, World world, Session session, NetHandlerPlayClient netHandlerPlayClient, StatFileWriter fileWriter)
    {
        super(mc, world, session, netHandlerPlayClient, fileWriter);
        this.movementInput = new DFMovementController();
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
    public void sendMotionUpdates()
    {
        if (DaFlight.get().DFController.softFallOn())
        {
            boolean sneaking = this.isSneaking();
            if (sneaking != wasSneaking)
            {
                if (sneaking)
                {
                    this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 1));
                }
                else
                {
                    this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 2));
                }
                wasSneaking = sneaking;
            }
            double xChange = this.posX - oldPosX;
            double yChange = this.boundingBox.minY - oldMinY;
            double zChange = this.posZ - oldPosZ;
            double rotationChange = this.rotationYaw - oldRotationYaw;
            double pitchChange = this.rotationPitch - oldRotationPitch;
            boolean sendMovementUpdate = xChange * xChange + yChange * yChange + zChange * zChange > 9.0E-4D || ticksSinceMovePacket >= 20;
            boolean sendLookUpdate = rotationChange != 0.0D || pitchChange != 0.0D;
            boolean ground = true;
            if (DaFlight.get().DFController.flyModOn)
            {
                ground = !this.capabilities.allowFlying;
            }
            if (sendMovementUpdate && sendLookUpdate)
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, ground));
            }
            else if (sendMovementUpdate)
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, ground));
            }
            else if (sendLookUpdate)
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, ground));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer(ground));
            }
            ++ticksSinceMovePacket;
            if (sendMovementUpdate)
            {
                oldPosX = this.posX;
                oldMinY = this.boundingBox.minY;
                oldPosZ = this.posZ;
                ticksSinceMovePacket = 0;
            }
            if (sendLookUpdate)
            {
                oldRotationPitch = this.rotationPitch;
                oldRotationYaw = this.rotationYaw;
            }
        }
        else
        {
            super.sendMotionUpdates();
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
    public void fall(float distance)
    {
        if (DaFlight.get().DFController.softFallOn())
            return;
        super.fall(distance);
    }

    @Override
    public float getFOVMultiplier()
    {
        if (!DaFlight.getConfig().disabled && DaFlight.get().DFController.flyModOn)
        {
            if (!this.capabilities.isFlying)
            {
                this.capabilities.isFlying = true;
                this.sendPlayerAbilities();
            }
            if (DaFlight.getConfig().disableFov)
                return 1.0F;
        }
        return super.getFOVMultiplier();
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
    public float getBreakSpeed(Block b, boolean boo)
    {
        float f = super.getBreakSpeed(b, boo);
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
    public boolean pushOutOfBlocks(double x, double y, double z)
    {
        this.noClip = this.capabilities.isCreativeMode && DaFlight.get().DFController.noClipOn && DaFlight.get().DFController.flyModOn;
        return !this.noClip && super.pushOutOfBlocks(x, y, z);
    }

    @Override
    public void moveEntity(double x, double y, double z)
    {
        this.noClip = this.capabilities.isCreativeMode && DaFlight.get().DFController.noClipOn && DaFlight.get().DFController.flyModOn;
        super.moveEntity(x, y, z);
    }

    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return !this.noClip && super.isEntityInsideOpaqueBlock();
    }
}
