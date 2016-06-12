package de.universallp.va.core.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by universallp on 28.03.2016 00:06.
 */
public class ContainerXPHopper extends Container {

    private final IInventory hopperInventory;

    public ContainerXPHopper(InventoryPlayer playerInventory, IInventory hopperInventoryIn) {
        this.hopperInventory = hopperInventoryIn;

        // Hopper inv
        for (int j = 0; j < hopperInventoryIn.getSizeInventory() - 1; ++j)
            this.addSlotToContainer(new Slot(hopperInventoryIn, j, 26 + j * 18, 20));

        // Bottle Slot
        this.addSlotToContainer(new SlotFiltered(hopperInventoryIn, hopperInventoryIn.getSizeInventory() - 1, 134, 20, new ItemStack(Items.GLASS_BOTTLE, 1)));

        // Player inv
        int i = 51;
        for (int l = 0; l < 3; ++l)
            for (int k = 0; k < 9; ++k)
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + i));


        for (int i1 = 0; i1 < 9; ++i1)
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 58 + i));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (!(slot instanceof SlotFiltered) && itemstack.getItem().equals(Items.GLASS_BOTTLE)) {
                if (!this.mergeItemStack(itemstack1, this.hopperInventory.getSizeInventory() - 1, this.hopperInventory.getSizeInventory(), true))
                    return null;
                if (itemstack1.stackSize == 0)
                    slot.putStack(null);
                return itemstack;
            }

            if (index < this.hopperInventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.hopperInventory.getSizeInventory(), false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return hopperInventory.isUseableByPlayer(playerIn);
    }
}
