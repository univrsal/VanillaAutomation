package de.universallp.va.core;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.compat.CompatTinkersConstruct;
import de.universallp.va.core.compat.ICompatModule;
import de.universallp.va.core.dispenser.DispenserTweaks;
import de.universallp.va.core.handler.AnvilDescriptionHandler;
import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.handler.CrashReportHandler;
import de.universallp.va.core.handler.EndermiteHandler;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.network.GuiHandler;
import de.universallp.va.core.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by universallp on 19.03.2016 11:28 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class CommonProxy {


    public void preInit(FMLPreInitializationEvent e) {
        VABlocks.register();
        VAItems.register();
        ConfigHandler.loadConfig(e.getSuggestedConfigurationFile());
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(VanillaAutomation.instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new CrashReportHandler());
        MinecraftForge.EVENT_BUS.register(new AnvilDescriptionHandler());
        MinecraftForge.EVENT_BUS.register(new EndermiteHandler());

        PacketHandler.register();
        DispenserTweaks.register();

        CrashReportHandler.readCrashes(e.getSide());

        if (Loader.isModLoaded("tconstruct")) {
           // CompatTinkersConstruct.run(e, ICompatModule.EnumEventType.INIT);
        } else {
          //  EnumEntry.TC_COMPAT.disable();
        }

        if (!Loader.isModLoaded("biomesoplenty")) {
          ///  EnumEntry.BOP_COMPAT.disable();
        }
    }

    public void postInit(FMLPostInitializationEvent e) {
        ConfigHandler.loadPostInit();
        if (ConfigHandler.DISPENSER_SEARCH_FOR_TOOLS)
            DispenserTweaks.searchRegistry();
    }

    public boolean isServer() {
        return true;
    }
}
