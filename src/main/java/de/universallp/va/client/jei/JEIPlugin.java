package de.universallp.va.client.jei;

import de.universallp.va.client.gui.guide.Entries;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.ItemGuide;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by universallp on 10.04.2016 14:34 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {

    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void register(@Nonnull IModRegistry registry) {

        // Items
        registry.addIngredientInfo(new ItemStack(VAItems.itemGuide), VanillaTypes.ITEM, LibLocalization.JEI_GUIDE);
        //String[] descriptionTag = Entries.DESCRIPTION_TAG.
        //descriptionTag[1] = I18n.format(descriptionTag[1]).replaceAll("7n", "\\\\\n");
        //registry.addDescription(new ItemStack(VAItems.itemDescriptionTag, 1), descriptionTag);

        //// Blocks
        //registry.addDescription(new ItemStack(VABlocks.filterHopper, 1), EnumEntry.getGuidePage("filteredhopper", 1, 2));
        //registry.addDescription(new ItemStack(VABlocks.xpHopper, 1), EnumEntry.getGuidePage("xphopper", 1, 2));
        //registry.addDescription(new ItemStack(VABlocks.placer, 1), EnumEntry.getGuidePage("blockplacer", 1, 2));
        //registry.addDescription(new ItemStack(Blocks.DISPENSER, 1), EnumEntry.getGuidePage("dispenser", 1, 2));
        //registry.addDescription(new ItemStack(VABlocks.redstoneclock, 1), EnumEntry.getGuidePage("redstoneclock", 1, 2));

    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {

    }
}
