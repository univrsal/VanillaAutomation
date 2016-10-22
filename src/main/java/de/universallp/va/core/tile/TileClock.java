package de.universallp.va.core.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nullable;

/**
 * Created by universallp on 22.10.2016 20:49.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class TileClock extends TileEntity implements ITickable, IInventory {

    private int tickDelay = 20;
    private boolean powered = false;

    public int getTickDelay() {
        return tickDelay;
    }

    public void setTickDelay(int tickDelay) {
        this.tickDelay = tickDelay;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("tickdelay"))
            tickDelay = compound.getInteger("tickdelay");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("tickdelay", tickDelay);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (worldObj.isBlockPowered(getPos())) {
            if (worldObj.getWorldTime() % tickDelay == 0) {
                powered = true;

            } else {
                powered = false;
            }
        }

    }

    public boolean isPowered() {
        return powered;
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
        return tickDelay;
    }

    @Override
    public void setField(int id, int value) {
        tickDelay = (value > 0 && value < 1001 ? value : tickDelay);
    }

    @Override
    public int getFieldCount() {
        return 0;
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
