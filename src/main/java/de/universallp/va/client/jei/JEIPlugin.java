package de.universallp.va.client.jei;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import net.minecraft.client.resources.I18n;
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
        registry.addDescription(new ItemStack(VAItems.itemPokeStick, 1), EnumEntry.getGuidePage("pokestick", 1, 2));
        registry.addDescription(new ItemStack(VAItems.itemGuide, 1), LibLocalization.JEI_GUIDE);
        String[] descriptionTag = EnumEntry.getGuidePage("descriptiontag", 1, 2);
        descriptionTag[1] = I18n.format(descriptionTag[1]).replaceAll("7n", "\\\\\n");
        registry.addDescription(new ItemStack(VAItems.itemDescriptionTag, 1), descriptionTag);

        // Blocks
        registry.addDescription(new ItemStack(VABlocks.filterHopper, 1), EnumEntry.getGuidePage("filteredhopper", 1, 2));
        registry.addDescription(new ItemStack(VABlocks.xpHopper, 1), EnumEntry.getGuidePage("xphopper", 1, 2));
        registry.addDescription(new ItemStack(VABlocks.placer, 1), EnumEntry.getGuidePage("blockplacer", 1, 2));
        registry.addDescription(new ItemStack(Blocks.DISPENSER, 1), EnumEntry.getGuidePage("dispenser", 1, 2));


    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

    }
}
