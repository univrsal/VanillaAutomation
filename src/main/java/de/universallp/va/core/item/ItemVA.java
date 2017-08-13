package de.universallp.va.core.item;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.IEntryProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by universallp on 21.03.2016 14:45 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ItemVA extends Item implements IEntryProvider {


    protected ItemVA(String name) {
        setUnlocalizedName(name);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        return super.setUnlocalizedName("va_" + unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        if (getHasSubtypes()) {
            NonNullList<ItemStack> subItems = NonNullList.create();
            getSubItems(getCreativeTab(), subItems);
            String name = ForgeRegistries.ITEMS.getKey(this).toString();
            for (int i = 0; i < subItems.size(); i++) {
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(name, "inventory"));
            }
        } else {
            String name = ForgeRegistries.ITEMS.getKey(this).toString();
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(name, "inventory"));
        }
    }

    @Override
    public VisualRecipe getRecipe() {
        return null;
    }

    @Override
    public int getEntryID() {
        return -1;
    }

    public void register() {
        ForgeRegistries.ITEMS.register(this);//, new ResourceLocation(itemName));
        addRecipe();
    }

    @Override
    public void addRecipe() {
        if (getRecipe() != null)
            switch (getRecipe().getType()) {
              /*  case SHAPED:
                    GameRegistry.addShapedRecipe(new ShapedRecipes("",3, 3, this.getRecipe().getIngredients(), this.getRecipe().getResult()));
                    break;
                case SHAPELESS:
                    GameRegistry.addShapelessRecipe(new ShapelessRecipes(this.getRecipe().getResult(), Arrays.asList(this.getRecipe().getIngredients())));
                    break;*/
            }
    }
}
