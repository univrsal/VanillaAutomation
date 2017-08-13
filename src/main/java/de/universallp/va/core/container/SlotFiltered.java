package de.universallp.va.core.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by universallp on 28.03.2016 14:56 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class SlotFiltered extends Slot {

    private ItemStack filter;

    public SlotFiltered(IInventory inventoryIn, int index, int xPosition, int yPosition, ItemStack filter) {
        super(inventoryIn, index, xPosition, yPosition);
        this.filter = filter;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack == null || stack.getItem() != null && stack.getItem().equals(filter.getItem()))
            return true;
        return false;
    }
}
