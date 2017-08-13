/**
 * Created by universallp on 21.03.2016 18:16 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;

public enum EnumEntry {

    MENU(new String[]{LibLocalization.ENTRY_INTRO, LibLocalization.ENTRY_CREDITS, LibLocalization.ENTRY_BLOCKPLACER,
            LibLocalization.ENTRY_DISPENSER, LibLocalization.ENTRY_XPHOPPER,
            LibLocalization.ENTRY_FILTEREDHOPPER, LibLocalization.ENTRY_DESCRIPTION_TAG,
            LibLocalization.ENTRY_CLOCK,

            // Compat
    LibLocalization.ENTRY_BOPCOMPAT, LibLocalization.ENTRY_TCONCOMPAT }),

    INTRO(LibLocalization.PAGES_INTRO),
    CREDITS(LibLocalization.PAGES_CREDITS),

    // Actual entries

    BLOCK_PLACER(LibLocalization.PAGES_BLOCK_PLACER, VABlocks.placer.getRecipe(), 1),
    DISPENSER(LibLocalization.PAGES_DISPENSER),
    XPHOPPER(LibLocalization.PAGES_XPHOPPER, VABlocks.xpHopper.getRecipe(), 1),
    FILTERED_HOPPER(LibLocalization.PAGES_FILTERED_HOPPER, VABlocks.filterHopper.getRecipe(), 1),
    DESCRIPTION_TAG(LibLocalization.PAGES_DESCRIPTION_TAG, VAItems.itemDescriptionTag.getRecipe(), 1),
    CLOCK(LibLocalization.PAGES_CLOCK, VABlocks.redstoneclock.getRecipe(), 1);

    // Compat entries

    //BOP_COMPAT(LibLocalization.PAGES_BOP_COMPAT),
    //TC_COMPAT(LibLocalization.PAGES_TCON_COMPAT);

    private String[] entries = null;
    private Entry instance = null;
    private VisualRecipe vR = null;
    private int recipePage = -1;
    private boolean enabled = true;

    EnumEntry(String[] entries) {
        this.entries = entries;
    }

    EnumEntry(String[] entries, VisualRecipe recipe, int pageForRecipe) {
        this.entries = entries;
        this.vR = recipe;
        this.recipePage = pageForRecipe;
        this.instance = null;
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

            //for (int i = 0; i < entries.length; i++)
            //    menuEntries[i] = new MenuEntry(entries[i], EnumEntry.values()[i]); // First entry is skipped

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
