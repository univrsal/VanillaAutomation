package de.universallp.va.core.container;

import de.universallp.va.core.tile.TileAutoTrader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by universallp on 09.08.2016 19:35.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class SlotTradeResult extends Slot {

    private final TileAutoTrader trader;

    public SlotTradeResult(TileAutoTrader inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.trader = inventoryIn;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        if (trader.isTradingPossible() && trader.getIsTradePossible(trader.getField(0)) && super.canTakeStack(playerIn)) {
            return trader.performTrade();
        }

        return false;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return false;
    }
}
