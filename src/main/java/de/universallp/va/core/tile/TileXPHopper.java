package de.universallp.va.core.tile;

import de.universallp.va.core.container.XPHopperItemHandler;
import de.universallp.va.core.util.References;
import de.universallp.va.core.util.Utils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

/**
 * Created by universallp on 27.03.2016 23:58.
 */
public class TileXPHopper extends TileEntityHopper {

    public static final int xpPerBottle = 13;
    private static final int hopperInv = 5;
    private int progress;

    public TileXPHopper() {
        setCustomName(I18n.format(References.Local.GUI_XPHOPPER));
        ReflectionHelper.setPrivateValue(TileEntityHopper.class, this, new ItemStack[6], "inventory"); // Welp, seems to work
    }


    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack != null && stack.getItem() != null && stack.getItem().equals(Items.glass_bottle)) {
            ItemStack bottles = getStackInSlot(5);
            if (getStackInSlot(index) == null && index == 5)
                return true;

            if (bottles != null && bottles.stackSize < bottles.getMaxStackSize() && index == 5)
                return true;

            if (bottles != null && bottles.stackSize >= bottles.getMaxStackSize() && index != 5)
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
                ItemStack bottles = getStackInSlot(5);
                if (bottles != null && bottles.stackSize > 0)
                    if (resultXP >= xpPerBottle && slot > -1) { // If there's space for a new bottle and adding the xp of the current orb will result in a new bottle
                        ItemStack xpBottle = new ItemStack(Items.experience_bottle, 1);
                        getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP).insertItem(slot, xpBottle, false);
                        setInventorySlotContents(5, Utils.decreaseStack(bottles, 1));
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

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new XPHopperItemHandler(this);
    }
}
