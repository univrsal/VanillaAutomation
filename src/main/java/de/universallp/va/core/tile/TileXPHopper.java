package de.universallp.va.core.tile;

import de.universallp.va.core.util.References;
import de.universallp.va.core.util.Utils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

/**
 * Created by universallp on 27.03.2016 23:58.
 */
public class TileXPHopper extends TileEntityHopper {

    private static final int xpPerBottle = 13;
    private ItemStack bottleStack;
    private int progress;

    public TileXPHopper() {
        setCustomName(I18n.format(References.Local.GUI_XPHOPPER));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound bottletag = new NBTTagCompound();
        bottletag = bottleStack.writeToNBT(bottletag);
        compound.setTag("bottles", bottletag);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("bottles"))
            bottleStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("bottles"));
    }

    @Override
    public int getSizeInventory() {
        return super.getSizeInventory() + 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < super.getSizeInventory())
            return super.getStackInSlot(index);
        else
            return bottleStack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < super.getSizeInventory())
            super.setInventorySlotContents(index, stack);
        else if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.glass_bottle))
            bottleStack = stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (index < super.getSizeInventory())
            return super.decrStackSize(index, count);
        else {
            return ItemStackHelper.func_188382_a(new ItemStack[]{bottleStack}, index, count);
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (index < super.getSizeInventory())
            return super.removeStackFromSlot(index);
        else {
            return ItemStackHelper.func_188383_a(new ItemStack[]{bottleStack}, index);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index < super.getSizeInventory())
            return super.isItemValidForSlot(index, stack);
        else if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.glass_bottle))
            return true;
        return false;
    }

    @Override
    public boolean updateHopper() {
        BlockPos overHopper = getPos().up();

        List<EntityXPOrb> orbs = getWorld().getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(overHopper));

        if (orbs != null && orbs.size() > 0) {
            for (EntityXPOrb orb : orbs) {
                int slot = getBottleSlot();
                int resultXP = orb.xpValue + progress;
                if (resultXP >= xpPerBottle && slot > -1) { // If there's space for a new bottle and adding the xp of the current orb will result in a new bottle
                    ItemStack xpBottle = new ItemStack(Items.experience_bottle, 1);
                    getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).insertItem(slot, xpBottle, false);
                    Utils.decreaseStack(bottleStack, 1);
                    progress = resultXP - xpPerBottle;
                } else {
                    if (slot == -1 && resultXP > xpPerBottle) // If theres no space for a new bottle and adding the xp of the current orb would result in a new bottle
                        break;

                    // If there's no space for a new bottle but adding the xp of the current orb won't result in  a new bottle
                    progress = resultXP;
                    getWorld().removeEntity(orb);
                }
            }
        }
        return super.updateHopper();
    }

    public int getProgress() {
        return progress;
    }

    private int getBottleSlot() {
        for (int i = 0; i < super.getSizeInventory(); i++) {
            ItemStack s = getStackInSlot(i);
            if (s == null || (s.getItem() != null && s.getItem().equals(Items.experience_bottle) && s.stackSize < s.getMaxStackSize()))
                return i;
        }
        return -1;
    }
}
