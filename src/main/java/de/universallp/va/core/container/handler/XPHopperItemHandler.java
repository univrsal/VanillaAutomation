package de.universallp.va.core.container.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.items.VanillaHopperItemHandler;

/**
 * Created by universallp on 28.03.2016 20:20.
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
