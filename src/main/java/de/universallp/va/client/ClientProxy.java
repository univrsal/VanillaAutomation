package de.universallp.va.client;

import de.universallp.va.client.gui.guide.Entry;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.handler.GuideHandler;
import de.universallp.va.core.CommonProxy;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.ItemVA;
import de.universallp.va.core.util.libs.LibReflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * Created by universallp on 23.03.2016 19:16 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class ClientProxy extends CommonProxy {

    public static Entry lastEntry;
    public static EnumEntry hoveredEntry;
    public static int guiScale = 0;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        ItemVA.registerModels();
        VABlocks.registerModels();
        MinecraftForge.EVENT_BUS.register(new GuideHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        //GuideHandler.initVanillaEntries();
    }


    @Override
    public boolean isServer() {
        return false;
    }
}
