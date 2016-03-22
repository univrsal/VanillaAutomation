package de.universallp.va.client;

import de.universallp.va.client.gui.guide.Entry;
import de.universallp.va.core.CommonProxy;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.ItemVA;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by universallp on 19.03.2016 11:28.
 */
public class ClientProxy extends CommonProxy {

    public static Entry lastEntry;
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
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
