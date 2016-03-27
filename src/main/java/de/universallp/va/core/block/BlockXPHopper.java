package de.universallp.va.core.block;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.References;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

/**
 * Created by universallp on 27.03.2016 22:56.
 */
public class BlockXPHopper extends BlockHopper implements IEntryProvider {

    private static VisualRecipe recipe;

    public BlockXPHopper() {
        setUnlocalizedName(References.BLOCK_XPHOPPER);
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation mdlResource = new ModelResourceLocation(References.BLOCK_XPHOPPER, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, mdlResource);
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return null;

        ItemStack endereye = new ItemStack(Items.ender_eye, 1);
        ItemStack hopper = new ItemStack(Blocks.hopper, 1);
        recipe = new VisualRecipe(new ItemStack[]{endereye, null, null, hopper}, new ItemStack(this, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.XPHOPPER;
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
