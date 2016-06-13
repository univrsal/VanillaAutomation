package de.universallp.va.core.tile;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by universallp on 19.03.2016 14:08.
 */
public class TileVA extends TileEntity implements IInventory {
    protected ItemStack[] items = new ItemStack[9];
    protected int sizeInv;

    public TileVA(int invSize) {
        this.sizeInv = invSize;
        items = new ItemStack[invSize];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList l = new NBTTagList();
        for (int i = 0; i < items.length; i++) {
            NBTTagCompound tag = new NBTTagCompound();
            if (items[i] != null)
                items[i].writeToNBT(tag);
            tag.setByte("slot", (byte) i);
            l.appendTag(tag);
        }

        compound.setTag("items", l);
        return super.writeToNBT(compound);

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            NBTTagList l = compound.getTagList("items", 10);
            for (int i = 0; i < getSizeInventory(); i++) {
                NBTTagCompound t = l.getCompoundTagAt(i);
                if (t != null)
                    items[t.getByte("slot")] = ItemStack.loadItemStackFromNBT(t);
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return sizeInv;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(items, index, count);

        if (itemstack != null)
            this.markDirty();

        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack s = items[index];
        items[index] = null;
        return s;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

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
        return "va.container.blockplacer";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(I18n.format(getName()));
    }
}
