package de.universallp.va.client.jei;

import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by universallp on 10.04.2016 14:34.
 */
@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void register(@Nonnull IModRegistry registry) {

        // Items
        registry.addDescription(new ItemStack(VAItems.itemPokeStick, 1), LibLocalization.getGuidePage("pokestick", 1));
        registry.addDescription(new ItemStack(VAItems.itemGuide, 1), LibLocalization.JEI_GUIDE);

        // Blocks
        registry.addDescription(new ItemStack(VABlocks.filterHopper, 1), LibLocalization.getGuidePage("filteredhopper", 1));
        registry.addDescription(new ItemStack(VABlocks.xpHopper, 1), LibLocalization.getGuidePage("xphopper", 1));
        registry.addDescription(new ItemStack(VABlocks.placer, 1), LibLocalization.getGuidePage("blockplacer", 1));
        registry.addDescription(new ItemStack(Blocks.dispenser, 1), LibLocalization.getGuidePage("dispenser", 1));

    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

    }
}
