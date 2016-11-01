package de.universallp.va.core.tile;

import de.universallp.va.core.block.BlockClock;
import de.universallp.va.core.block.VABlocks;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

/**
 * Created by universallp on 22.10.2016 20:49.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class TileClock extends TileEntity implements ITickable, IInventory {

    private int tickDelay = 20;
    private int tickLength = 20;
    private int tickCount = 0;

    public int getTickDelay() {
        return tickDelay;
    }

    public int getTickLength() { return tickLength; }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("tickdelay"))
            tickDelay = compound.getInteger("tickdelay");
        if (compound.hasKey("ticklength"))
            tickLength = compound.getInteger("ticklength");
        if (compound.hasKey("tickcount"))
            tickCount = compound.getInteger("tickcount");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("tickdelay", tickDelay);
        compound.setInteger("ticklength", tickLength);
        compound.setInteger("tickcount", tickCount);;
        return super.writeToNBT(compound);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return !newSate.getBlock().equals(VABlocks.redstoneclock);
    }

    @Override
    public void update() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            if (worldObj.getBlockState(getPos()).getValue(BlockLever.POWERED)) {
                tickCount++;
                if (!worldObj.getBlockState(getPos()).getValue(BlockClock.EMITTING)) {
                    if (tickCount == tickDelay) {
                        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(BlockClock.EMITTING, Boolean.TRUE));
                        tickCount = 0;
                    }
                } else {
                    if (tickCount == tickLength) {
                        tickCount = 0;
                        worldObj.setBlockState(getPos(), worldObj.getBlockState(getPos()).withProperty(BlockClock.EMITTING, Boolean.FALSE));
                    }
                }
            } else {
                tickCount = 0;
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return id == 0 ? tickDelay : tickLength;
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0)
            tickDelay = (value > 0 && value < 1001 ? value : tickDelay);
        else
            tickLength = (value > 0 && value < 1001 ? value : tickLength);
        tickCount = 0;

    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public void clear() {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
