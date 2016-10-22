package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.tile.TileClock;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by universallp on 22.10.2016 20:46.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class BlockClock extends BlockVA implements IEntryProvider, ITileEntityProvider {

    private static VisualRecipe recipe;

    public BlockClock() {
        super(Material.ROCK, LibNames.BLOCK_CLOCK);
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileClock)
                playerIn.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_CLOCK, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public int tickRate(World worldIn) {
        return 1;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileClock) {
            if (((TileClock) te).isPowered()) {
                super.updateTick(worldIn, pos, state, rand);
                worldIn.notifyNeighborsOfStateChange(pos, this);
            }
        }
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public boolean getWeakChanges(IBlockAccess blockAccess, BlockPos pos) {
        TileEntity te = blockAccess.getTileEntity(pos);
        if (te != null && te instanceof TileClock) {
            return ((TileClock) te).isPowered();
        }
        return false;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        TileEntity te = blockAccess.getTileEntity(pos);
        if (te != null && te instanceof TileClock) {
            return ((TileClock) te).isPowered() ? 15 : 0;
        }
        return super.getWeakPower(blockState, blockAccess, pos, side);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileClock();
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.CLOCK;
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe == null) {
            ItemStack cobble = new ItemStack(Blocks.COBBLESTONE, 1);
            ItemStack rs = new ItemStack(Items.REDSTONE, 1);
            ItemStack rstorch = new ItemStack(Blocks.REDSTONE_TORCH, 1);
            recipe = new VisualRecipe(new ItemStack[] { cobble, rstorch, cobble, rstorch, rs, rstorch, cobble, rstorch, cobble },
                    new ItemStack(VABlocks.redstoneclock, 1), VisualRecipe.EnumRecipeType.SHAPED);
        }
        return recipe;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileClock();
    }
}
