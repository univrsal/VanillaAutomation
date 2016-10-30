package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.tile.TileClock;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by universallp on 22.10.2016 20:46.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class BlockClock extends BlockVA implements IEntryProvider, ITileEntityProvider {

    private static VisualRecipe recipe;
    private static final AxisAlignedBB CLOCK_UP    = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
    private static final AxisAlignedBB CLOCK_DOWN  = new AxisAlignedBB(0.0D, 1.0D, 0.0D, 1.0D, 0.875D, 1.0D);
    private static final AxisAlignedBB CLOCK_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D,   0.125D);
    private static final AxisAlignedBB CLOCK_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB CLOCK_EAST  = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
    private static final AxisAlignedBB CLOCK_WEST  = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public static final PropertyBool EMITTING = PropertyBool.create("emitting");

    public BlockClock() {
        super(Material.ROCK, LibNames.BLOCK_CLOCK);
        setCreativeTab(CreativeTabs.REDSTONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLever.POWERED, Boolean.FALSE).withProperty(EMITTING, Boolean.FALSE).withProperty(BlockButton.FACING, EnumFacing.UP));
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (worldIn.isBlockPowered(pos)) {
            return canPlaceBlock(worldIn, pos, facing.getOpposite()) ? this.getDefaultState().withProperty(BlockButton.FACING, facing).withProperty(BlockLever.POWERED, Boolean.TRUE) : getDefaultState();
        } else {
            return canPlaceBlock(worldIn, pos, facing.getOpposite()) ? this.getDefaultState().withProperty(BlockButton.FACING, facing).withProperty(BlockLever.POWERED, Boolean.FALSE) : getDefaultState();
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        if (!canPlaceBlock(worldIn, pos, state.getValue(BlockButton.FACING).getOpposite())) {
            if (this.checkForDrop(worldIn, pos, state)) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
        if (worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.withProperty(BlockLever.POWERED, Boolean.TRUE));
        } else {
            worldIn.setBlockState(pos, state.withProperty(BlockLever.POWERED, Boolean.FALSE));
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getValue(BlockLever.POWERED)) {
            worldIn.notifyNeighborsOfStateChange(pos, this);
            worldIn.notifyNeighborsOfStateChange(pos.offset(state.getValue(BlockButton.FACING).getOpposite()), this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (this.canPlaceBlockAt(worldIn, pos)) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }

    private static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction) {
        BlockPos blockpos = pos.offset(direction);
        return worldIn.getBlockState(blockpos).isSideSolid(worldIn, blockpos, direction.getOpposite());
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing enumfacing = state.getValue(BlockButton.FACING);
        switch (enumfacing) {
            case DOWN:
                return CLOCK_DOWN;
            case UP:
                return CLOCK_UP;
            case NORTH:
                return CLOCK_NORTH;
            case SOUTH:
                return CLOCK_SOUTH;
            case WEST:
                return CLOCK_WEST;
            case EAST:
                return CLOCK_EAST;
            default:
                return CLOCK_UP;
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockLever.POWERED, EMITTING, BlockButton.FACING);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    // Blockstate stuff

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockButton.FACING, EnumFacing.VALUES[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockButton.FACING).getIndex() ; // All this bit shifting, I lost track of how it works
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(BlockLever.POWERED)) {
            double d0 = (double)pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double)pos.getY() + 0.7D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double)pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    // Redstone stuff
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return blockState.getValue(EMITTING) ? 15 : 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return !blockState.getValue(EMITTING) ? 0 : 15;
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
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
