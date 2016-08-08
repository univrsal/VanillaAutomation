package de.universallp.va.core.compat;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.core.dispenser.DispenserTweaks;
import de.universallp.va.core.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

/**
 * Created by universallp on 08.08.2016 11:31.
 */
@Optional.Interface(iface = ICompatModule.INTERFACE, modid = CompatTinkersConstruct.MOD_ID)
public class CompatTinkersConstruct implements ICompatModule {

    public static final String MOD_ID = "tconstruct";
    public static CompatTinkersConstruct INSTANCE = new CompatTinkersConstruct();
    public static boolean isEnabled = false;

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
            isEnabled = true;
        } else
            EnumEntry.TC_COMPAT.disable();
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {

    }

    @Override
    public void init(FMLInitializationEvent e) {
        LogHelper.logInfo("Tinkers Construct detected, adding TC tools to dispenser registry...");

        DispenserTweaks.add(TinkerTools.pickaxe, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.shovel, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.hatchet, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.hammer, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.excavator, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.lumberAxe, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.mattock, DispenserTweaks.TOOL_BEHAVIOUR);

        DispenserTweaks.add(TinkerTools.broadSword, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.longSword, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.cleaver, DispenserTweaks.SWORD_BEHAVIOUR);
//        DispenserTweaks.add(TinkerTools.battleAxe, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.battleSign, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.longSword, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.rapier, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerTools.fryPan, DispenserTweaks.SWORD_BEHAVIOUR);
//        DispenserTweaks.add(TinkerTools.cutlass, DispenserTweaks.SWORD_BEHAVIOUR);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }

    @Override
    public boolean doTCDamage(ItemStack s, EntityPlayer player, Entity e) {
        if (s.getItem() instanceof ToolCore) {
            ToolHelper.attackEntity(s, (ToolCore) s.getItem(), player, e);
            return true;
        }
        return false;
    }
}
