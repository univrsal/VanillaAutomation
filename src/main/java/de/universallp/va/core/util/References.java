package de.universallp.va.core.util;

import net.minecraft.util.EnumFacing;

/**
 * Created by universallp on 19.03.2016 11:28.
 */
public class References {

    public static final String MOD_ID = "va";
    public static final String MOD_NAME = "VanillaAutomation";
    public static final String MOD_VERSION = "1.9-1.0";
    public static final String PREFIX = MOD_ID + ":";

    // Blocks
    public static final String BLOCK_PLACER = PREFIX + "blockplacer";

    // Tiles
    public static final String TILE_PLACER  = "tilePlacer";

    // Items
    public static final String ITEM_GUIDE = PREFIX + "vaguide";
    public static final String ITEM_POKESTICK = PREFIX + "pokestick";

    public static final String GUI_DIR = "va.dir.";

    public static EnumFacing getNext(EnumFacing f) {
        if (f == EnumFacing.EAST)
            return EnumFacing.DOWN;
        else
            return EnumFacing.values()[f.ordinal() + 1];
    }

    public static class Local {
        public static final String GUI_DIST = "va.gui.reachdistance";
        public static final String GUI_FACE = "va.gui.face";

        public static final String BTN_MENU = "va.gui.buttonmenu";
        public static final String BTN_BACK = "va.gui.buttonback";

        public static final String GUIDE_DESC = "item.va:vaguide.desc";
        public static final String GUIDE_LOOK = "va.guide.entry.lookup";

        public static final String ENTRY_INTRO = "va.guide.entry.intro";
        public static final String ENTRY_CREDITS = "va.guide.entry.credits";
        public static final String ENTRY_BLOCKPLACER = "va.guide.entry.blockplacer";
        public static final String ENTRY_DISPENSER = "va.guide.entry.dispenser";
        public static final String ENTRY_POKE_STICK = "va.guide.entry.pokestick";

        // Guide
        private static final String PAGE = "va.guide.entry.";

        public static String getGuidePage(String entry, int p) {
            return PAGE + entry + ".page" + p;
        }
    }
}
