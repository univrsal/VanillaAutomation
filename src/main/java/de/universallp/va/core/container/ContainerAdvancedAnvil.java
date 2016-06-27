package de.universallp.va.core.container;

import de.universallp.va.core.tile.TileAdvancedAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by universallp on 22.06.2016 19:28.
 */
public class ContainerAdvancedAnvil extends Container {

    private final IInventory anvilInv;

    public ContainerAdvancedAnvil(InventoryPlayer playerInventory, IInventory anvilInventory) {
        this.anvilInv = anvilInventory;

        // Anvil inv

        this.addSlotToContainer(new Slot(anvilInv, 0, 27, 53)); // iron
        this.addSlotToContainer(new SlotAnvilInput(anvilInv, 1, 27, 96)); // item 1
        this.addSlotToContainer(new Slot(anvilInv, 2, 76, 96)); // item 2
        this.addSlotToContainer(new SlotAnvilOutput((TileAdvancedAnvil) anvilInv, 3, 134, 96)); // output

        // Player inv
        int i = 133;
        for (int l = 0; l < 3; ++l)
            for (int k = 0; k < 9; ++k)
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + i));

        for (int i1 = 0; i1 < 9; ++i1)
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 58 + i));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return anvilInv.isUseableByPlayer(playerIn);
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {

        super.putStackInSlot(slotID, stack);
    }
}
