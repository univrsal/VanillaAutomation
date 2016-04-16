package de.universallp.va.core.handler;

import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.entity.EntityMinecartCarriage;
import de.universallp.va.core.entity.EntityMinecartXPHopper;
import net.minecraft.entity.item.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by universallp on 16.04.2016 12:01.
 */
public class MinecartInteractionHandler {

    private static final Item chest = Item.getItemFromBlock(Blocks.chest);
    private static final Item furnace = Item.getItemFromBlock(Blocks.furnace);
    private static final Item hopper = Item.getItemFromBlock(Blocks.hopper);
    private static final Item tnt = Item.getItemFromBlock(Blocks.tnt);
    private static final Item filteredHopper = Item.getItemFromBlock(VABlocks.filterHopper);
    private static Item xpHopper;

    public MinecartInteractionHandler() {
        xpHopper = new ItemStack(VABlocks.xpHopper, 1).getItem();
    }

    @SubscribeEvent
    public void onInteraction(MinecartInteractEvent e) {
        if (e.getItem() != null && e.getMinecart() != null && e.getMinecart().getType() == EntityMinecart.Type.RIDEABLE) {
            if (!e.getEntity().worldObj.isRemote)
                if (replaceEntity(e.getMinecart(), e.getItem()) && !e.getPlayer().capabilities.isCreativeMode) {
                    ItemStack s = e.getPlayer().getHeldItem(e.getHand());
                    s.stackSize--;
                    if (s.stackSize < 1)
                        s = null;
                    e.getPlayer().setHeldItem(e.getHand(), s);
                }
            e.setResult(Event.Result.DENY);
            e.setCanceled(true);
        }
    }

    public boolean replaceEntity(EntityMinecart m, final ItemStack stack) {
        if (m.isBeingRidden())
            return false;

        EntityMinecart cart = m;
        Item i = stack.getItem();
        System.out.println(xpHopper);
        if (i.equals(chest))
            cart = new EntityMinecartChest(m.worldObj, m.posX, m.posY, m.posZ);
        else if (i.equals(furnace))
            cart = new EntityMinecartFurnace(m.worldObj, m.posX, m.posY, m.posZ);
        else if (i.equals(hopper))
            cart = new EntityMinecartHopper(m.worldObj, m.posX, m.posY, m.posZ);
        else if (i.equals(tnt))
            cart = new EntityMinecartTNT(m.worldObj, m.posX, m.posY, m.posZ);
        else if (i.equals(xpHopper))
            cart = new EntityMinecartXPHopper(m.worldObj, m.posX, m.posY, m.posZ);
        else if (i.equals(filteredHopper)) {
            // NYI
        } else {
            cart = new EntityMinecartCarriage(m.worldObj, m.posX, m.posY, m.posZ, stack);
        }

        cart.rotationPitch = m.rotationPitch;
        cart.rotationYaw = m.rotationYaw;
        cart.motionX = m.motionX;
        cart.motionY = m.motionY;
        cart.motionZ = m.motionZ;
        cart.fallDistance = m.fallDistance;

        m.worldObj.spawnEntityInWorld(cart);
        m.setDead();
        m.worldObj.removeEntity(m);
        return !cart.equals(m);
    }
}
