package de.universallp.va.core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by universallp on 22.10.2016 22:11.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ContainerClock extends Container {


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }


    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return false;
    }

    @Override
    public Slot getSlot(int slotId) {
        return null;
    }

    @Nullable
    @Override
    public Slot getSlotFromInventory(IInventory inv, int slotIn) {
        return null;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }


}
