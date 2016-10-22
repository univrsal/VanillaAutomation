package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;

/**
 * Created by universallp on 21.03.2016 18:16.
 */
public enum EnumEntry {

    MENU(new String[] { LibLocalization.ENTRY_INTRO, LibLocalization.ENTRY_CREDITS, LibLocalization.ENTRY_BLOCKPLACER,
            LibLocalization.ENTRY_DISPENSER, LibLocalization.ENTRY_POKE_STICK, LibLocalization.ENTRY_XPHOPPER,
            LibLocalization.ENTRY_FILTEREDHOPPER, LibLocalization.ENTRY_DESCRIPTION_TAG, LibLocalization.ENTRY_AUTOTRADER,
            LibLocalization.ENTRY_CLOCK,

            // Compat
            LibLocalization.ENTRY_BOPCOMPAT, LibLocalization.ENTRY_TCONCOMPAT }),

    INTRO(getGuidePage("intro", 1, 1)),
    CREDITS(getGuidePage("credits", 1, 1)),

    // Actual entries

    BLOCK_PLACER(getGuidePage("blockplacer", 1, 2), VABlocks.placer.getRecipe(), 1),
    DISPENSER(getGuidePage("dispenser", 1, 2)),
    POKE_STICK(getGuidePage("pokestick", 1, 2), VAItems.itemPokeStick.getRecipe(), 1),
    XPHOPPER(getGuidePage("xphopper", 1, 2), VABlocks.xpHopper.getRecipe(), 1),
    FILTERED_HOPPER(getGuidePage("filteredhopper", 1, 2), VABlocks.filterHopper.getRecipe(), 1),
    DESCRIPTION_TAG(getGuidePage("descriptiontag", 1, 2), VAItems.itemDescriptionTag.getRecipe(), 1),
    AUTO_TRADER(getGuidePage("autotrader", 1, 2), VABlocks.autoTrader.getRecipe(), 1),
    CLOCK(getGuidePage("redstoneclock", 1, 2), VABlocks.redstoneclock.getRecipe(), 1),

    // Compat entries

    BOP_COMPAT(getGuidePage("bopcompat", 1, 1)),
    TC_COMPAT(getGuidePage("tconcompat", 1, 1));

    private static final String PAGE = "va.guide.entry.";
    private String[] entries;
    private Entry instance;
    private VisualRecipe vR;
    private int recipePage;
    private boolean enabled = true;

    EnumEntry(String[] entries) {
        this.entries = entries;
    }

    EnumEntry(String[] entries, VisualRecipe recipe, int pageForRecipe) {
        this.entries = entries;
        this.vR = recipe;
        this.recipePage = pageForRecipe;
    }

    public static String[] getGuidePage(String entry, int startPage, int endPage) {
        String[] pages = new String[(endPage - startPage) + 1];
        for (int i = startPage; i <= endPage; i++)
            pages[i - 1] = PAGE + entry + ".page" + i;
        return pages;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Entry getEntry() {
        if (instance != null)
            return instance;

        if (this == EnumEntry.MENU) {
            MenuEntry[] menuEntries = new MenuEntry[entries.length];

            for (int i = 0; i < entries.length; i++)
                menuEntries[i] = new MenuEntry(entries[i], EnumEntry.values()[1 + i]); // First entry is skipped

            EntryPage menuPage = new EntryPage(menuEntries);
            instance = new Entry(getEntryTitle(), menuPage);
            return instance;
        } else {
            EntryPage[] pages = new EntryPage[entries.length];

            for (int i = 0; i < entries.length; i++)
                if (vR != null && i == recipePage)
                    pages[i] = new EntryPage(entries[i], vR);
                else
                    pages[i] = new EntryPage(entries[i]);

            instance = new Entry(getEntryTitle(), pages);
            return instance;
        }
    }

    public String getEntryTitle() {
        if (this == EnumEntry.MENU)
            return "va.gui.guide.menu";
        if (ordinal() - 1 < EnumEntry.MENU.entries.length)
            return EnumEntry.MENU.entries[ordinal() - 1];
        return EnumEntry.MENU.entries[1];
    }
}
