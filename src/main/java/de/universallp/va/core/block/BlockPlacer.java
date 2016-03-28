package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.util.References;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

/**
 * Created by universallp on 19.03.2016 12:11.
 */
public class BlockPlacer extends BlockVA {

    private static VisualRecipe recipe;

    public BlockPlacer() {
        super(Material.rock, References.BLOCK_PLACER);
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            playerIn.openGui(VanillaAutomation.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public int tickRate(World worldIn)
    {
        return 4;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            TilePlacer tP = (TilePlacer) worldIn.getTileEntity(pos);
            int slot = tP.getNextPlaceable();

            if (slot == -1) {
                worldIn.playAuxSFX(1001, pos, 0);
                super.updateTick(worldIn, pos, state, rand);
                return;
            }
            ItemStack placable = tP.getStackInSlot(slot);
            EnumFacing f = getFacingFromState(state);
            BlockPos dest = pos.add(f.getFrontOffsetX() * tP.reachDistance, f.getFrontOffsetY() * tP.reachDistance, f.getFrontOffsetZ() * tP.reachDistance);

            if (placeBlock(worldIn, dest, tP.placeFace, VAFakePlayer.instance(worldIn), placable)) {
                tP.decrStackSize(slot, 1);
            }
        }

        super.updateTick(worldIn, pos, state, rand);
    }

    public boolean placeBlock(World worldObj, BlockPos pos, EnumFacing f, VAFakePlayer fakePlayer, ItemStack placeable) {
        fakePlayer.setItemInHand(placeable);
        final IBlockState blockS = worldObj.getBlockState(pos);
        final Block block = Block.getBlockFromItem(placeable.getItem());

        if (block != null) {
            IBlockState placeState = Block.getBlockFromItem(placeable.getItem()).getStateFromMeta(placeable.getItemDamage());
            BlockSnapshot bS;

            if (placeable.hasTagCompound())
                bS = new BlockSnapshot(worldObj, pos, placeState, placeable.getTagCompound());
            else
                bS = new BlockSnapshot(worldObj, pos, placeState);


            BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(bS, blockS, fakePlayer);

            if (MinecraftForge.EVENT_BUS.post(event)) return false;

            boolean canPlace = block.canPlaceBlockAt(worldObj, pos);
            if (canPlace) {

                IBlockState s = block.onBlockPlaced(worldObj, pos, f, 0, 0, 0, placeable.getItemDamage(), fakePlayer);

                if (s.getProperties().containsKey(BlockDispenser.FACING)) // For full rotation
                    s = s.withProperty(BlockDispenser.FACING, f);
                if (s.getProperties().containsKey(BlockFurnace.FACING)) { // For horizontal only
                    if (f == EnumFacing.DOWN || f == EnumFacing.UP)
                        f = EnumFacing.NORTH;
                    s = s.withProperty(BlockFurnace.FACING, f);
                }
                worldObj.setBlockState(pos, s);

                return true;
            }
        } else {
            if (placeable.getItem().equals(VAItems.itemPokeStick)) {
                fakePlayer.rightClick(placeable, pos, f, 0, 0, 0);
                placeable.damageItem(1, fakePlayer);
            } else if (worldObj.isAirBlock(pos)) {
                placeable.getItem().onItemRightClick(placeable, worldObj, VAFakePlayer.instance(worldObj), EnumHand.MAIN_HAND);
                placeable.getItem().onItemUse(placeable, VAFakePlayer.instance(worldObj), worldObj, pos, EnumHand.MAIN_HAND, f, 0, 0, 0);
            }
        }

        return false;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        TilePlacer te = (TilePlacer) worldIn.getTileEntity(pos);
        boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
        boolean flag1 = te.isTriggered;

        if (flag && !flag1) {
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            te.isTriggered = true;
        } else if (!flag && flag1) {
            te.isTriggered = false;
        }
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, BlockPistonBase.getFacingFromEntity(pos, placer));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TilePlacer();
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
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockDispenser.FACING, BlockDispenser.getFacing(meta));
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
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | state.getValue(BlockDispenser.FACING).getIndex();

        return i;
    }

    public EnumFacing getFacingFromState(IBlockState s) {
        if (s.getProperties().containsKey(BlockDispenser.FACING))
            return (EnumFacing) s.getProperties().get(BlockDispenser.FACING);
        return null;
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack cobbleStone = new ItemStack(Blocks.cobblestone, 1);
        ItemStack piston = new ItemStack(Blocks.piston, 1);
        ItemStack chest  = new ItemStack(Blocks.chest, 1);

        recipe = new VisualRecipe(new ItemStack[] { cobbleStone, cobbleStone, cobbleStone,
                                                    cobbleStone, chest,       cobbleStone,
                cobbleStone, piston, cobbleStone}, new ItemStack(VABlocks.placer, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.BLOCK_PLACER;
    }
}
