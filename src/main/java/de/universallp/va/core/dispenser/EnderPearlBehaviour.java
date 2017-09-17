package de.universallp.va.core.dispenser;

import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class EnderPearlBehaviour implements IBehaviorDispenseItem {

    public static Random rng = new Random();
    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        TileEntityDispenser te = source.getBlockTileEntity();
        IBlockState s = te.getWorld().getBlockState(source.getBlockPos());
        EnumFacing f = (EnumFacing) s.getProperties().get(BlockDirectional.FACING);
        BlockPos pos = source.getBlockPos();

        VAFakePlayer p = VAFakePlayer.instance(source.getWorld());
        pos = pos.add(f.getDirectionVec());

        float xOff, zOff, pitch;

        xOff = f == EnumFacing.NORTH ? 0.5f  : (f == EnumFacing.SOUTH ? 0.5f  : (f == EnumFacing.EAST ? 0.5f  : 0.5f));
        zOff = f == EnumFacing.NORTH ? -0.5f : (f == EnumFacing.SOUTH ? -0.5f : (f == EnumFacing.EAST ? -0.5f : -0.5f));
        pitch = f == EnumFacing.UP ? -90.0f : (f == EnumFacing.DOWN ? 90.0f : 0.0f);

        p.posX = ((float)pos.getX()) + xOff;
        p.posY = pos.getY() - p.eyeHeight + 0.35;
        p.posZ = ((float)pos.getZ()) - zOff;


        double rotation = f == EnumFacing.NORTH ? 180.0 : (f == EnumFacing.SOUTH ? 0.0 : (f == EnumFacing.EAST ? -90.0 : 90.0));
        p.rotationYaw = (float) rotation;
        p.rotationPitch = pitch;

        return throwPearl(stack, p);
    }


    private ItemStack throwPearl(ItemStack s, VAFakePlayer p) {
        s.shrink(1);

        p.getEntityWorld().playSound((EntityPlayer)null, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rng.nextFloat() * 0.4F + 0.8F));

        if (!p.getEntityWorld().isRemote) {
            EntityEnderPearl entityenderpearl = new EntityEnderPearl(p.getEntityWorld(), p);
            entityenderpearl.setHeadingFromThrower(p, p.rotationPitch, p.rotationYaw, 0.0F, 1.5F, 1.0F);
            p.getEntityWorld().spawnEntity(entityenderpearl);
        }
        return s;
    }
}
