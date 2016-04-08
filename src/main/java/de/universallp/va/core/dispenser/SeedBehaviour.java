package de.universallp.va.core.dispenser;

import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessagePlaySound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.SoundType;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by universallp on 31.03.2016 14:10.
 */
public class SeedBehaviour implements IBehaviorDispenseItem {

    private Block b;

    public SeedBehaviour(Block b) {
        this.b = b;
    }

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        EnumFacing facing = BlockDispenser.getFacing(source.getBlockMetadata());
        BlockPos pos = source.getBlockPos().offset(facing);
        World world = source.getWorld();

        if (world.isAirBlock(pos) && b.canPlaceBlockAt(world, pos)) {
            world.setBlockState(pos, b.getDefaultState());
            stack.stackSize--;
            NetworkRegistry.TargetPoint tp = new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64);
            SoundType sound = b.getStepSound();
            PacketHandler.INSTANCE.sendToAllAround(new MessagePlaySound(sound.getBreakSound().getSoundName().toString(), pos, sound.getPitch(), sound.getVolume()), tp);
            return stack;
        }

        return stack;
    }
}
