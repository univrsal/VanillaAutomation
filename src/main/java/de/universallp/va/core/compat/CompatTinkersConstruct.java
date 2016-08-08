package de.universallp.va.core.compat;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.util.LogHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

/**
 * Created by universallp on 08.08.2016 11:31.
 */
public class CompatTinkersConstruct implements ICompatModule {

    public static final String MOD_ID = "tconstruct";
    public static CompatTinkersConstruct INSTANCE = new CompatTinkersConstruct();

    public static void run(FMLStateEvent e, EnumEventType type) {
        if (Loader.isModLoaded(MOD_ID)) {
            switch (type) {
                case INIT:
                    INSTANCE.init((FMLInitializationEvent) e);
                    break;
                case POST_INIT:
                    INSTANCE.postInit((FMLPostInitializationEvent) e);
                    break;
                case PRE_INIT:
                    INSTANCE.preInit((FMLPreInitializationEvent) e);
                    break;
            }
        } else
            EnumEntry.TC_COMPAT.disable();
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {

    }

    @Override
    public void init(FMLInitializationEvent e) {
        LogHelper.logInfo("Tinkers Construct detected, adding TC tools to dispenser registry...");

    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }
}
