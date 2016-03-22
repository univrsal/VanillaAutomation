package de.universallp.va.core;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.dispenser.DispenserTweaks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.network.GuiHandler;
import de.universallp.va.core.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by universallp on 19.03.2016 11:28.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        PacketHandler.register();
        VAItems.init();
    }

    public void init(FMLInitializationEvent e) {
        VABlocks.register();
        NetworkRegistry.INSTANCE.registerGuiHandler(VanillaAutomation.instance, new GuiHandler());
        DispenserTweaks.register();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
