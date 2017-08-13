package de.universallp.va.core.util.libs;

/**
 * Created by universallp on 30.03.2016 13:32 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class LibLocalization {

    // Gui
    public static final String GUI_DIST = "va.gui.reachdistance";
    public static final String GUI_FACE = "va.gui.face";
    public static final String GUI_XPHOPPER = "va.gui.xphopper";
    public static final String GUI_FILTEREDHOPPER = "va.gui.filteredhopper";
    public static final String GUI_FILTER = "va.gui.filteredhopper.filter";
    public static final String GUI_CLOCK = "va.gui.redstoneclock";
    public static final String GUI_CLOCK_DELAY = "va.gui.redstoneclock.delay";
    public static final String GUI_CLOCK_TICKS = "va.gui.redstoneclock.ticks";
    public static final String GUI_CLOCK_LENGTH = "va.gui.redstoneclock.length";
    public static final String GUI_CLOCK_SECONDS = "va.gui.redstoneclock.seconds";

    // Buttons
    public static final String BTN_MENU = "va.gui.buttonmenu";
    public static final String BTN_BACK = "va.gui.buttonback";
    public static final String BTN_META = "va.gui.btn.matchmeta";
    public static final String BTN_NBT = "va.gui.btn.matchnbt";
    public static final String BTN_MOD = "va.gui.btn.matchmod";

    // Tooltips
    public static final String GUIDE_DESC = "item.va:vaguide.desc";
    public static final String TIP_DESCRIPTIONMODE = "tooltip.va:descriptionmode";
    public static final String TIP_DESCRIPTION = "tootlip.va:description";
    public static final String TAGMODE_NONE = "tagmode.va:none";
    public static final String TAGMODE_ADD = "tagmode.va:add";
    public static final String TAGMODE_ADDBOTTOM = "tagmode.va:addbottom";
    public static final String TAGMODE_CLEAR = "tagmode.va:clear";

    // Guide
    public static final String GUIDE_LOOK = "va.guide.entry.lookup";
    public static final String ENTRY_INTRO = "va.guide.entry.intro";
    public static final String ENTRY_CREDITS = "va.guide.entry.credits";
    public static final String ENTRY_BLOCKPLACER = "va.guide.entry.blockplacer";
    public static final String ENTRY_DISPENSER = "va.guide.entry.dispenser";
    public static final String ENTRY_XPHOPPER = "va.guide.entry.xphopper";
    public static final String ENTRY_FILTEREDHOPPER = "va.guide.entry.filteredhopper";
    public static final String ENTRY_DESCRIPTION_TAG = "va.guide.entry.descriptiontag";
    public static final String ENTRY_BOPCOMPAT = "va.guide.entry.bopcompat";
    public static final String ENTRY_TCONCOMPAT = "va.guide.entry.tconcompat";
    public static final String ENTRY_CLOCK = "va.guide.entry.redstoneclock";

    public static final String RECIPE_SHAPED = "va.recipe.shaped";
    public static final String RECIPE_SHAPELESS = "va.recipe.shapeless";

    public static final String MSG_CRASH1 = "va.msg.crash1";
    public static final String MSG_CRASH2 = "va.msg.crash2";

    public static final String JEI_GUIDE = "va.jei.guideinfo";

    public static final String YES = "gui.yes";
    public static final String NO = "gui.no";
    public static final String BTN_REDSTONE = "va.gui.btn.useredstone";

    // Guide Entries
    public static String[] getGuidePage(String entry, int startPage, int endPage) {
        String[] pages = new String[(endPage - startPage) + 1];
        for (int i = startPage; i <= endPage; i++)
            pages[i - 1] = PAGE + entry + ".page" + i;
        return pages;
    }

    private static final String PAGE = "va.guide.entry.";

    public static final String[] ENTRIES_MENU = new String[]{LibLocalization.ENTRY_INTRO, LibLocalization.ENTRY_CREDITS, LibLocalization.ENTRY_BLOCKPLACER,
            LibLocalization.ENTRY_DISPENSER, LibLocalization.ENTRY_XPHOPPER,
            LibLocalization.ENTRY_FILTEREDHOPPER, LibLocalization.ENTRY_DESCRIPTION_TAG,
            LibLocalization.ENTRY_CLOCK,

            // Compat
            LibLocalization.ENTRY_BOPCOMPAT, LibLocalization.ENTRY_TCONCOMPAT };

    public static final String[] PAGES_INTRO = getGuidePage("intro", 1, 1);
    public static final String[] PAGES_CREDITS = getGuidePage("credits", 1, 1);
    public static final String[] PAGES_BLOCK_PLACER = getGuidePage("blockplacer", 1, 2);
    public static final String[] PAGES_DISPENSER = getGuidePage("dispenser", 1, 3);
    public static final String[] PAGES_XPHOPPER = getGuidePage("xphopper", 1, 2);
    public static final String[] PAGES_FILTERED_HOPPER = getGuidePage("filteredhopper", 1, 2);
    public static final String[] PAGES_DESCRIPTION_TAG = getGuidePage("descriptiontag", 1, 2);
    public static final String[] PAGES_CLOCK = getGuidePage("redstoneclock", 1, 2);
    public static final String[] PAGES_BOP_COMPAT = getGuidePage("bopcompat", 1, 1);
    public static final String[] PAGES_TCON_COMPAT = getGuidePage("tconcompat", 1, 1);

    public static final String ENTRY_MENU_TITLE = "va.gui.guide.menu";
}
