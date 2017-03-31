package de.universallp.va.core.container.handler;

import de.universallp.va.core.tile.TileFilteredHopper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.VanillaHopperItemHandler;

/**
 * Created by universallp on 31.03.2016 15:15 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class FilteredItemHandler extends VanillaHopperItemHandler {

    private TileFilteredHopper te;

    public FilteredItemHandler(TileFilteredHopper hopper) {
        super(hopper);
        te = hopper;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot < 5) {
            if (te.getHasItemFilter()) {
                if (te.isItemValid(stack)) {
                    return super.insertItem(slot, stack, simulate);
                }

            } else
                return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot < 5)
            return super.extractItem(slot, amount, simulate);
        return ItemStack.EMPTY;
    }
}
