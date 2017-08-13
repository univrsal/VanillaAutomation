package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.Entries;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by universallp on 27.03.2016 22:56 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class BlockXPHopper extends BlockHopper implements IEntryProvider {

    private static VisualRecipe recipe;

    public BlockXPHopper() {
        setUnlocalizedName(LibNames.BLOCK_XPHOPPER);
        setSoundType(SoundType.METAL);
        setRegistryName(new ResourceLocation(LibNames.BLOCK_XPHOPPER));
        setHardness(2);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation mdlResource = new ModelResourceLocation(LibNames.BLOCK_XPHOPPER, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, mdlResource);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileXPHopper)
                playerIn.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_XPHOPPER, worldIn, pos.getX(), pos.getY(), pos.getZ());

            return true;
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(VABlocks.xpHopper);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileXPHopper();
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack endereye = new ItemStack(Items.ENDER_EYE, 1);
        ItemStack hopper = new ItemStack(Blocks.HOPPER, 1);
        recipe = new VisualRecipe(new ItemStack[] { endereye, ItemStack.EMPTY, ItemStack.EMPTY, hopper }, new ItemStack(VABlocks.xpHopper, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public int getEntryID() {
        return Entries.XPHOPPER.getEntryID();
    }

    @Override
    public void addRecipe() {
        //GameRegistry.addShapedRecipe(new ItemStack(VABlocks.xpHopper, 1), "E", "H", 'E', Items.ENDER_EYE, 'H', Blocks.HOPPER);
    }

//    @Override
//    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
//        TileEntity te = worldIn.getTileEntity(pos);
//        if (te != null && te instanceof IInventory) {
//            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
//            worldIn.updateComparatorOutputLevel(pos, this);
//        }
//
//        super.breakBlock(worldIn, pos, state);
//    }
}
