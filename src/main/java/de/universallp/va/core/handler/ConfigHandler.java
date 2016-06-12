package de.universallp.va.core.handler;

import de.universallp.va.core.util.libs.LibNames;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by universallp on 08.04.2016 20:21.
 */
public class ConfigHandler {

    private static final String CATEGORY_MISC = "misc";
    public static Configuration config;

    public static String LATEST_CRASH = "";
    public static boolean READ_LOGS = true;

    public static void loadConfig(File configFile) {
        if (config == null)
            config = new Configuration(configFile);
        load();
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }

    public static void load() {
        LATEST_CRASH = config.getString("latestCrash", CATEGORY_MISC, "none", "Do not modify this unless you want unexpected behaviour");
        READ_LOGS    = config.getBoolean("readLogs",   CATEGORY_MISC, true,   "Set to false to prevent vanillaautomation from reading crashlogs");
    }

    public static void loadPostInit() {
        if (config.hasChanged()) config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.getModID().equals(LibNames.MOD_ID)) {
            config.save();
            load();
        }
    }
}
