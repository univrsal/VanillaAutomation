package de.universallp.va.core.compat;

import de.universallp.va.core.dispenser.DispenserTweaks;
import de.universallp.va.core.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.harvest.TinkerHarvestTools;
import slimeknights.tconstruct.tools.melee.TinkerMeleeWeapons;

/**
 * Created by universallp on 08.08.2016 11:31.
 */
@Optional.Interface(iface = ICompatModule.INTERFACE, modid = CompatTinkersConstruct.MOD_ID)
public class CompatTinkersConstruct implements ICompatModule {

    public static final String MOD_ID = "tconstruct";
    public static CompatTinkersConstruct INSTANCE = new CompatTinkersConstruct();
    public static boolean isEnabled = false;

    public static void run(FMLStateEvent e, EnumEventType type) {
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
    }

    @Override
    public void preInit(FMLPreInitializationEvent e) {

    }

    @Override
    public void init(FMLInitializationEvent e) {
        LogHelper.logInfo("Tinkers Construct detected, adding TC tools to dispenser registry...");

        DispenserTweaks.add(TinkerHarvestTools.pickaxe, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerHarvestTools.shovel, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerHarvestTools.hatchet, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerHarvestTools.hammer, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerHarvestTools.excavator, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerHarvestTools.lumberAxe, DispenserTweaks.TOOL_BEHAVIOUR);
        DispenserTweaks.add(TinkerHarvestTools.mattock, DispenserTweaks.TOOL_BEHAVIOUR);

        DispenserTweaks.add(TinkerMeleeWeapons.broadSword, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerMeleeWeapons.longSword, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerMeleeWeapons.cleaver, DispenserTweaks.SWORD_BEHAVIOUR);
//        DispenserTweaks.add(TinkerHarvestTools.battleAxe, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerMeleeWeapons.battleSign, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerMeleeWeapons.longSword, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerMeleeWeapons.rapier, DispenserTweaks.SWORD_BEHAVIOUR);
        DispenserTweaks.add(TinkerMeleeWeapons.fryPan, DispenserTweaks.SWORD_BEHAVIOUR);
//        DispenserTweaks.add(TinkerHarvestTools.cutlass, DispenserTweaks.SWORD_BEHAVIOUR);
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
