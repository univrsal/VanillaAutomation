package de.universallp.va.core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by universallp on 31.03.2016 16:12.
 * Spoooky
 */
public class SlotGhost extends Slot {

    public SlotGhost(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
        // NO-OP
    }

    @Override
    public int getSlotStackLimit() {
        return 0;
    }

    @Override
    public void putStack(ItemStack stack) {
        if (stack != null)
            stack.stackSize = 1;
        super.putStack(stack);
    }
}
