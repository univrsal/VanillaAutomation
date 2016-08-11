package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.tile.TileAutoTrader;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by universallp on 08.08.2016 17:52.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class BlockAutoTrader extends BlockVA {

    private static VisualRecipe recipe;

    public BlockAutoTrader() {
        super(Material.ROCK, LibNames.BLOCK_AUTOTRADER);
        setCreativeTab(CreativeTabs.REDSTONE);
        setHardness(2);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, BlockPistonBase.getFacingFromEntity(pos, placer));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            playerIn.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_AUTOTRADER, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockDispenser.FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(BlockDispenser.FACING).getIndex();

        return i;
    }

    @Override
    public int tickRate(World worldIn) {
        return 20;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, EnumFacing.getFront(meta));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(BlockDispenser.FACING, rot.rotate(state.getValue(BlockDispenser.FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDispenser.FACING)));
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileAutoTrader();
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack cobbleStone = new ItemStack(Blocks.COBBLESTONE, 1);
        ItemStack emerald = new ItemStack(Items.EMERALD, 1);
        ItemStack chest = new ItemStack(Blocks.CHEST, 1);

        recipe = new VisualRecipe(new ItemStack[] { cobbleStone, cobbleStone, cobbleStone,
                cobbleStone, chest, cobbleStone,
                cobbleStone, emerald, cobbleStone }, new ItemStack(VABlocks.autoTrader, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public void addRecipe() {
        // WIP
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof IInventory)
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
        super.breakBlock(worldIn, pos, state);
    }


    @Override
    public EnumEntry getEntry() {
        return EnumEntry.AUTO_TRADER;
    }
}
