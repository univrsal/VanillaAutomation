package de.universallp.va.core.handler;

import de.universallp.va.core.util.libs.LibNames;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by universallp on 08.04.2016 20:21 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ConfigHandler {

    private static final String CATEGORY_MISC = "misc";
    private static final String CATEGORY_BLOCKS = "blocks";
    private static final String CATEGORY_DISPENSER = "dispenser";

    public static Configuration config;

    public static byte BLOCK_PLACER_REACH = 16;
    public static String LATEST_CRASH = "";
    public static boolean READ_LOGS = true;
    public static byte DISPENSER_REACH_MAX = 4;

    public static int CLOCK_MAX_VALUE = 1000;

    public static boolean DISPENSER_USE_TOOLS = true;
    public static boolean DISPENSER_USE_WEAPONS = true;
    public static boolean DISPENSER_USE_SEEDS = true;
    public static boolean DISPENSER_USE_NAMETAGS = true;
    public static boolean DISPENSER_USE_DISCS = true;
    public static boolean DISPENSER_USE_DYE = true;

    public static boolean DISPENSER_SEARCH_FOR_TOOLS = true;

    public static void loadConfig(File configFile) {
        if (config == null)
            config = new Configuration(configFile);
        load();
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
    }

    public static void load() {
        LATEST_CRASH = config.getString("latestCrash", CATEGORY_MISC, "none", "Do not modify this unless you want unexpected behaviour");
        READ_LOGS    = config.getBoolean("readLogs",   CATEGORY_MISC, true,   "Set to false to prevent vanillaautomation from reading crashlogs");

        BLOCK_PLACER_REACH = (byte) config.getInt("blockPlacerReach", CATEGORY_BLOCKS, 16, 1, 64, "Maximum reach distance for the block placer");
        DISPENSER_REACH_MAX = (byte) config.getInt("dispenserReach", CATEGORY_BLOCKS, 4, 1, 32, "Maximum reach distance for the dispenser tweaks. 1 to disable");

        DISPENSER_USE_DISCS = config.getBoolean("useDiscs", CATEGORY_DISPENSER, true, "Set to false to prevent the dispenser from inserting discs into juke boxes");
        DISPENSER_USE_TOOLS = config.getBoolean("useTools", CATEGORY_DISPENSER, true, "Set to false to prevent the dispenser from using tools");
        DISPENSER_USE_WEAPONS = config.getBoolean("useWeapons", CATEGORY_DISPENSER, true, "Set to false to prevent the dispenser from using weapons");
        DISPENSER_USE_SEEDS = config.getBoolean("useSeeds", CATEGORY_DISPENSER, true, "Set to false to prevent the dispenser from planting seeds");
        DISPENSER_USE_NAMETAGS = config.getBoolean("useNameTags", CATEGORY_DISPENSER, true, "Set to false to prevent the dispenser from naming entities with name tags");
        DISPENSER_USE_DYE = config.getBoolean("useDye", CATEGORY_DISPENSER, true, "Set to false to prevent the dispenser from dying sheep with");

        DISPENSER_SEARCH_FOR_TOOLS = config.getBoolean("searchTools", CATEGORY_DISPENSER, true, "Set to false to prevent VanillaAutomation from searching the registry for tools and weapons and make them usable by the dispenser");

        CLOCK_MAX_VALUE = config.getInt("clockMaxValue", CATEGORY_BLOCKS, 1000, 10, 10000, "Max pulse length and delay in ticks");
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
