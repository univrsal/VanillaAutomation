package de.universallp.va.core.block;


import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.IEntryProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

/**
 * Created by universallp on 08.08.2016 17:45.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class BlockVA extends Block implements IEntryProvider {

    private String blockName;

    public BlockVA(Material materialIn, String blockName) {
        super(materialIn);
        this.blockName = blockName;
        setUnlocalizedName(blockName);
        setRegistryName(new ResourceLocation(blockName));
        setHardness(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation mdlResource = new ModelResourceLocation(blockName, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, mdlResource);
    }

    @Override
    public VisualRecipe getRecipe() {
        return null;
    }

    @Override
    public EnumEntry getEntry() {
        return null;
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
