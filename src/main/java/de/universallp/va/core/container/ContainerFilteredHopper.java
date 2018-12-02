package de.universallp.va.core.container;

import de.universallp.va.core.tile.TileXPHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by universallp on 31.03.2016 16:09 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ContainerFilteredHopper extends Container {

    private final IInventory hopperInventory;

    public ContainerFilteredHopper(InventoryPlayer playerInventory, IInventory hopperInventoryIn) {
        this.hopperInventory = hopperInventoryIn;

        // Hopper inv
        for (int j = 0; j < TileXPHopper.hopperInv; ++j)
            this.addSlotToContainer(new Slot(hopperInventoryIn, j, 45 + j * 18, 20));

        // The Filters
        for (int j = TileXPHopper.hopperInv; j < hopperInventoryIn.getSizeInventory(); ++j)
            this.addSlotToContainer(new SlotGhost(hopperInventoryIn, j, 45 + (j - TileXPHopper.hopperInv) * 18, 40));

        // Player inv
        int i = 71;
        for (int l = 0; l < 3; ++l)
            for (int k = 0; k < 9; ++k)
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + i));


        for (int i1 = 0; i1 < 9; ++i1)
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 58 + i));
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return !(slotIn instanceof SlotGhost);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < this.hopperInventory.getSizeInventory()) {
                if (index < TileXPHopper.hopperInv) {
                    if (!this.mergeItemStack(itemstack1, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), true))
                        return ItemStack.EMPTY;

                } else
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 0, TileXPHopper.hopperInv, false))
                return ItemStack.EMPTY;

            if (itemstack1.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return hopperInventory.isUsableByPlayer(playerIn);
    }

}
