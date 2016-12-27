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
    public boolean canTakeStack(EntityPlayer playerIn) {
        putStack(null);
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        ItemStack s = stack.copy();
        s.setCount(1);
        putStack(s);
        return false;
    }
}
