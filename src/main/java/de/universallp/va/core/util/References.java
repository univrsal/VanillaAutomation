package de.universallp.va.core.util;

import net.minecraft.util.EnumFacing;

/**
 * Created by universallp on 19.03.2016 11:28.
 */
public class References {

    public static final String MOD_ID = "va";
    public static final String MOD_VERSION = "1.9-1.0";
    public static final String PREFIX = MOD_ID + ":";


    public static final String BLOCK_PLACER = PREFIX + "blockplacer";
    public static final String TILE_PLACER  = "tilePlacer";

    public static final String GUI_DIR = "va.dir.";

    public static final String ITEM_GUIDE = PREFIX + "vaguide";

    public static EnumFacing getNext(EnumFacing f) {
        if (f == EnumFacing.EAST)
            return EnumFacing.DOWN;
        else
            return EnumFacing.values()[f.ordinal() + 1];
    }

    public static class Local {
        public static final String GUI_DIST = "va.gui.reachdistance";
        public static final String GUI_FACE = "va.gui.face";
        public static final String ENTRY_INTRO = "guide.entry.intro";
        public static final String ENTRY_CREDITS = "guide.entry.credits";
        public static final String ENTRY_BLOCKPLACER = "guide.entry.blockplacer";
        public static final String ENTRY_DISPENSER = "guide.entry.dispenser";
        public static final String BTN_MENU = "gui.va.buttonmenu";
        public static final String BTN_BACK = "gui.va.buttonback";
        public static final String GUIDE_DESC = "item.va:vaguide.desc";
        public static final String GUIDE_LOOK = "guide.entry.lookup";
        // Guide
        private static final String PAGE = "guide.entry.";

        public static String getGuidePage(String entry, int p) {
            return PAGE + entry + ".page." + p;
        }
    }
}
