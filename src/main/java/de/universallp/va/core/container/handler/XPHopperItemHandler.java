package de.universallp.va.core.container.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.items.VanillaHopperItemHandler;

/**
 * Created by universallp on 28.03.2016 20:20 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class XPHopperItemHandler extends VanillaHopperItemHandler {

    public XPHopperItemHandler(TileEntityHopper hopper) {
        super(hopper);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot == 5) // prevent pipes from taking out the bottles
            return null;
        return super.extractItem(slot, amount, simulate);
    }
}
