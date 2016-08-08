package de.universallp.va.core.block;

import de.universallp.va.core.tile.TileAutoTrader;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by universallp on 08.08.2016 17:52.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class BlockAutoTrader extends BlockVA {

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
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileAutoTrader();
    }

    public EnumFacing getFacingFromState(IBlockState s) {
        if (s.getProperties().containsKey(BlockDispenser.FACING))
            return (EnumFacing) s.getProperties().get(BlockDispenser.FACING);
        return null;
    }

}
