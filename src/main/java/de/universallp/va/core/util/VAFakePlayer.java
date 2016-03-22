package de.universallp.va.core.util;

import com.mojang.authlib.GameProfile;
import jline.internal.Log;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.UUID;

/**
 * Created by universallp on 19.03.2016 16:45.
 */
public class VAFakePlayer extends FakePlayer {
    private static VAFakePlayer instance;

    private VAFakePlayer(WorldServer world) {
        super(world, new GameProfile(UUID.randomUUID(), "VAFakePlayer"));
    }

    public static VAFakePlayer instance(World w) {
        if (instance == null)
            instance = new VAFakePlayer((WorldServer) w);
        else
            instance.setWorld(w);

        return instance;
    }

    public void setItemInHand(ItemStack m_item) {
        this.setHeldItem(EnumHand.MAIN_HAND, m_item);
    }

    public boolean rightClick(ItemStack itemStack, BlockPos pos, EnumFacing side, float deltaX, float deltaY, float deltaZ, boolean blockExists) {
        if (itemStack == null) return false;

        PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(this, PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, worldObj, pos, side, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));

        if (event.isCanceled()) { return false; }

        final Item usedItem = itemStack.getItem();
        EnumActionResult res = usedItem.onItemUseFirst(itemStack, this, worldObj, pos, side, deltaX, deltaY, deltaZ, EnumHand.MAIN_HAND);
        if (res == EnumActionResult.PASS || res == EnumActionResult.SUCCESS) { return true; }

        if (event.useBlock != Event.Result.DENY && (isSneaking() || usedItem.doesSneakBypassUse(itemStack, worldObj, pos, this))) {
            IBlockState blockS = worldObj.getBlockState(pos);
            Block block = blockS.getBlock();
            if (block != null) try {
                if (block.onBlockActivated(worldObj, pos, blockS, this, EnumHand.MAIN_HAND, itemStack, side, deltaX, deltaY, deltaZ)) return true;
            } catch (Throwable t) {
                Log.warn(t, "Invalid use of fake player on block %s @ (%d,%d,%d), aborting. Don't do it again", block, pos);
            }
        }
        return false;
    }
}
