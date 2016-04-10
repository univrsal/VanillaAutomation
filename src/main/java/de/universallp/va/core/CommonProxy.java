package de.universallp.va.core;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.dispenser.DispenserTweaks;
import de.universallp.va.core.entity.EntityXPHopperMinecart;
import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.handler.CrashReportHandler;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.network.GuiHandler;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Created by universallp on 19.03.2016 11:28.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        PacketHandler.register();
        VAItems.init();
        VABlocks.register();
        ConfigHandler.loadConfig(e.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new CrashReportHandler());
        CrashReportHandler.readCrashes(e.getSide());
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(VanillaAutomation.instance, new GuiHandler());

        DispenserTweaks.register();

        int id = 0;
        EntityRegistry.registerModEntity(EntityXPHopperMinecart.class, LibNames.ENTITY_XPHOPPERMINECART, id++, VanillaAutomation.instance, 80, 3, true);
//        EntityRegistry.registerModEntity(EntityXPHopperMinecart.class, LibNames.ENTITY_XPHOPPERMINECART, id++, VanillaAutomation.instance, 80, 3, true);
    }

    public void postInit(FMLPostInitializationEvent e) {
        ConfigHandler.loadPostInit();
    }

    /**
     * Shamelessly stolen from Botania
     * github.com/vazkii/Botania
     *
     * @param entity
     * @param reach
     */
    public void setReach(EntityLivingBase entity, float reach) {
        if (entity instanceof EntityPlayerMP)
            ((EntityPlayerMP) entity).interactionManager.setBlockReachDistance(reach);
    }


}
