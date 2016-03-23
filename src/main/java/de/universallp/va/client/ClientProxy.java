package de.universallp.va.client;

import de.universallp.va.client.gui.guide.Entry;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.handler.GuideHandler;
import de.universallp.va.core.CommonProxy;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.ItemVA;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by universallp on 23.03.2016 19:16.
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
        GuideHandler.initVanillaEntries();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
