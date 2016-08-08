package de.universallp.va.core.compat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by universallp on 02.07.2016 19:49.
 */
public interface ICompatModule {

    String INTERFACE = "de.universallp.va.core.compat.ICompatModule";

    void preInit(FMLPreInitializationEvent e);

    void init(FMLInitializationEvent e);

    void postInit(FMLPostInitializationEvent e);

    boolean doTCDamage(ItemStack s, EntityPlayer player, Entity e);

    enum EnumEventType {
        PRE_INIT,
        INIT,
        POST_INIT;
    }

}
