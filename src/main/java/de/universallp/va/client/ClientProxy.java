package de.universallp.va.client;

import de.universallp.va.client.gui.guide.Entries;
import de.universallp.va.client.gui.guide.Entry;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.handler.GuideHandler;
import de.universallp.va.core.CommonProxy;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.ItemVA;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.VAFakePlayer;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by universallp on 23.03.2016 19:16 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ClientProxy extends CommonProxy {

    public static Entry lastEntry;
    public static int hoveredEntry;
    public static int guiScale = 0;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        VAItems.registerModels();
        VABlocks.registerModels();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        MinecraftForge.EVENT_BUS.register(new GuideHandler());
        MinecraftForge.EVENT_BUS.register(new ClientProxy());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        Entries.init();
        GuideHandler.initVanillaEntries();
    }

    @SubscribeEvent
    public void onWorldClose(PlayerEvent.PlayerLoggedOutEvent e) {
        VAFakePlayer.RESET_FLAG = true;
    }

    @Override
    public boolean isServer() {
        return false;
    }
}
