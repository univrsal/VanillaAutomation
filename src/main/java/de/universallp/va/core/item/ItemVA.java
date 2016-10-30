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
import net.minecraft.util.ResourceLocation;
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
 * github.com/UniversalLP/VanillaAutomation
 */
public class ItemVA extends Item implements IEntryProvider {

    public static List<ItemVA> items = new ArrayList<ItemVA>();
    private String itemName;

    protected ItemVA(String name) {
        this.itemName = name;
        setUnlocalizedName(name);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for (ItemVA item : items)
            item.registerModel();
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        if (getHasSubtypes()) {
            ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
            getSubItems(this, getCreativeTab(), subItems);

            for (int i = 0; i < subItems.size(); i++) {
                ModelResourceLocation mdlResource = new ModelResourceLocation(itemName + (i == 0 ? "" : i), "inventory"); // Different resource location for sub items
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, mdlResource);
                ModelBakery.registerItemVariants(this, mdlResource);
            }
        } else {
            ModelResourceLocation mdlResource = new ModelResourceLocation(itemName, "inventory");
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, mdlResource);
        }
    }

    @Override
    public VisualRecipe getRecipe() {
        return null;
    }

    @Override
    public EnumEntry getEntry() {
        return null;
    }

    public void register() {
        GameRegistry.register(this, new ResourceLocation(itemName));
        addRecipe();
    }

    @Override
    public void addRecipe() {
        if (getRecipe() != null)
            switch (getRecipe().getType()) {
                case SHAPED:
                    GameRegistry.addRecipe(new ShapedRecipes(3, 3, this.getRecipe().getIngredients(), this.getRecipe().getResult()));
                    break;
                case SHAPELESS:
                    GameRegistry.addRecipe(new ShapelessRecipes(this.getRecipe().getResult(), Arrays.asList(this.getRecipe().getIngredients())));
                    break;
            }
    }
}
