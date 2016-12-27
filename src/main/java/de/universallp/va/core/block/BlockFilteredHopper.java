package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.tile.TileFilteredHopper;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by universallp on 30.03.2016 13:26 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class BlockFilteredHopper extends BlockHopper implements IEntryProvider {

    private static VisualRecipe recipe;

    public BlockFilteredHopper() {
        setUnlocalizedName(LibNames.BLOCK_FILTEREDHOPPER);
        setRegistryName(LibNames.BLOCK_FILTEREDHOPPER);
        setSoundType(SoundType.METAL);
        setHardness(2);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation mdlResource = new ModelResourceLocation(LibNames.BLOCK_FILTEREDHOPPER, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, mdlResource);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFilteredHopper();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileFilteredHopper();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileFilteredHopper)
                playerIn.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_FILTEREDHOPPER, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;
        ItemStack hopper = new ItemStack(Blocks.HOPPER, 1);
        ItemStack ironbars = new ItemStack(Blocks.IRON_BARS, 1);
        recipe = new VisualRecipe(new ItemStack[] { ironbars, null, null, hopper }, new ItemStack(VABlocks.filterHopper, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.FILTERED_HOPPER;
    }

    @Override
    public void addRecipe() {
        GameRegistry.addShapedRecipe(new ItemStack(VABlocks.filterHopper, 1), "I", "H", 'I', Blocks.IRON_BARS, 'H', Blocks.HOPPER);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileFilteredHopper) {
            TileFilteredHopper teF = (TileFilteredHopper) te;
            for (int i = TileXPHopper.hopperInv; i < teF.getSizeInventory(); i++)
                teF.setInventorySlotContents(i, null); // Clear the filters so they won't be dropped as items
        }

        super.breakBlock(worldIn, pos, state);
    }
}
