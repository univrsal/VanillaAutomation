package de.universallp.va.core.compat;

import biomesoplenty.api.item.BOPItems;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.dispenser.DispenserTweaks;
import de.universallp.va.core.util.LogHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

/**
 * Created by universallp on 02.07.2016 19:42.
 */
@Optional.Interface(iface = ICompatModule.INTERFACE, modid = CompatBoP.MOD_ID)
public class CompatBoP implements ICompatModule {

    public static final String MOD_ID = "BiomesOPlenty";
    public static CompatBoP INSTANCE = new CompatBoP();

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
            EnumEntry.BOP_COMPAT.disable();
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {

    }

    @Override
    public void init(FMLInitializationEvent e) {
        LogHelper.logInfo("Biomes 'o Plenty detected, adding BoP tools and discs to dispenser registry...");

//        DispenserTweaks.add(BOPItems.amethyst_axe, DispenserTweaks.TOOL_BEHAVIOUR);
//        DispenserTweaks.add(BOPItems.amethyst_pickaxe, DispenserTweaks.TOOL_BEHAVIOUR);
//        DispenserTweaks.add(BOPItems.amethyst_shovel, DispenserTweaks.TOOL_BEHAVIOUR);
//        DispenserTweaks.add(BOPItems.amethyst_sword, DispenserTweaks.SWORD_BEHAVIOUR);

        DispenserTweaks.add(BOPItems.mud_axe, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(BOPItems.mud_pickaxe, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(BOPItems.mud_shovel, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(BOPItems.mud_sword, DispenserTweaks.SWORD_BEHAVIOUR);

        DispenserTweaks.add(BOPItems.record_corruption, DispenserTweaks.DISC_BEHAVIOUR);
        DispenserTweaks.add(BOPItems.record_wanderer, DispenserTweaks.DISC_BEHAVIOUR);


    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }
}
