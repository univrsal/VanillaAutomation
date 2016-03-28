package de.universallp.va.core.tile;

import de.universallp.va.core.util.References;
import de.universallp.va.core.util.Utils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

/**
 * Created by universallp on 27.03.2016 23:58.
 */
public class TileXPHopper extends TileEntityHopper {

    public static final int xpPerBottle = 13;
    private static final int hopperInv = 5;
    private ItemStack[] items = new ItemStack[hopperInv + 1];
    private int progress;

    public TileXPHopper() {
        setCustomName(I18n.format(References.Local.GUI_XPHOPPER));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList l = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); i++) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            if (items[i] != null) {
                nbtTagCompound = items[i].writeToNBT(nbtTagCompound);
                nbtTagCompound.setByte("slot", (byte) i);
                l.appendTag(nbtTagCompound);
            }
        }

        if (!l.hasNoTags())
            compound.setTag("items", l);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            NBTTagList l = compound.getTagList("items", 10);
            for (int i = 0; i < l.tagCount(); i++) {
                NBTTagCompound tag = l.getCompoundTagAt(i);
                items[tag.getByte("slot")] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return hopperInv + 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        this.fillWithLoot((EntityPlayer) null);
        return items[index];
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.fillWithLoot((EntityPlayer) null);
        items[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        this.fillWithLoot((EntityPlayer) null);
        return ItemStackHelper.func_188382_a(items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        this.fillWithLoot((EntityPlayer) null);
        return ItemStackHelper.func_188383_a(items, index);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.glass_bottle)) {
            if (items[5] == null && index == 5)
                return true;

            if (items[5] != null && items[5].stackSize < items[5].getMaxStackSize() && index == 5)
                return true;

            if (items[5] != null && items[5].stackSize >= items[5].getMaxStackSize() && index != 5)
                return false;
            return false;
        }
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return hasCustomName() ? new TextComponentString(super.getName()) : null;
    }

    @Override
    public void setCustomName(String customName) {
        super.setCustomName(customName);
    }

    @Override
    public boolean hasCustomName() {
        return super.hasCustomName();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0 && value <= xpPerBottle)
            progress = value;
        markDirty();
    }

    @Override
    public boolean updateHopper() {
        BlockPos overHopper = getPos().up();
        List<EntityXPOrb> orbs = getWorld().getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(overHopper).expandXyz(1));

        if (orbs != null && orbs.size() > 0)
            for (EntityXPOrb orb : orbs) {
                int slot = getBottleSlot();
                int resultXP = orb.xpValue + progress;

                if (items[5] != null && items[5].stackSize > 0)
                    if (resultXP >= xpPerBottle && slot > -1) { // If there's space for a new bottle and adding the xp of the current orb will result in a new bottle
                        ItemStack xpBottle = new ItemStack(Items.experience_bottle, 1);
                        getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).insertItem(slot, xpBottle, false);
                        items[5] = Utils.decreaseStack(items[5], 1);
                        markDirty();
                        progress = resultXP - xpPerBottle;
                    } else {
                        if (slot == -1 && resultXP > xpPerBottle) // If theres no space for a new bottle and adding the xp of the current orb would result in a new bottle
                            break;

                        // If there's no space for a new bottle but adding the xp of the current orb won't result in  a new bottle
                        progress = resultXP;
                        getWorld().removeEntity(orb);
                    }
            }
        return super.updateHopper();
    }

    public int getProgress() {
        return progress;
    }

    private int getBottleSlot() {
        for (int i = 0; i < hopperInv; i++) {
            ItemStack s = getStackInSlot(i);
            if (s == null || (s.getItem() != null && s.getItem().equals(Items.experience_bottle) && s.stackSize < s.getMaxStackSize()))
                return i;
        }
        return -1;
    }
}
