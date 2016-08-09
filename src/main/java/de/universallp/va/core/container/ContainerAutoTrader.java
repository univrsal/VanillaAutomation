package de.universallp.va.core.container;

import de.universallp.va.core.tile.TileAutoTrader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by universallp on 08.08.2016 21:30.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class ContainerAutoTrader extends Container {

    private final IInventory autoTrader;

    public ContainerAutoTrader(InventoryPlayer playerInventory, IInventory trader) {
        this.autoTrader = trader;

        // Autotrader slots

        addSlotToContainer(new SlotLocked(trader, 0, 36, 36)); // Trade input preview
        addSlotToContainer(new SlotLocked(trader, 1, 62, 36)); // Trade input preview
        addSlotToContainer(new SlotLocked(trader, 2, 120, 36)); // Trade output preview

        addSlotToContainer(new Slot(trader, 3, 36, 66)); // Trade input
        addSlotToContainer(new Slot(trader, 4, 62, 66)); // Trade input
        addSlotToContainer(new SlotTradeResult((TileAutoTrader) trader, 5, 120, 67)); // Trade output


        // Player inv
        int i = 51;
        for (int l = 0; l < 3; ++l)
            for (int k = 0; k < 9; ++k)
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + i + 50));


        for (int i1 = 0; i1 < 9; ++i1)
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 108 + i));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
