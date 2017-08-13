package de.universallp.va.core.block;


import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.IEntryProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by universallp on 08.08.2016 17:45.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/univrsal/VanillaAutomation
 */
public class BlockVA extends Block implements IEntryProvider {

    public BlockVA(Material materialIn, String blockName) {
        super(materialIn);
        setUnlocalizedName(blockName);
        setRegistryName(new ResourceLocation(blockName));
        setHardness(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        String name = ForgeRegistries.BLOCKS.getKey(this).toString();
        ModelResourceLocation mdlResource = new ModelResourceLocation(name, "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, mdlResource);
    }

    @Override
    public VisualRecipe getRecipe() {
        return null;
    }

    @Override
    public int getEntryID() {
        return -1;
    }

}
