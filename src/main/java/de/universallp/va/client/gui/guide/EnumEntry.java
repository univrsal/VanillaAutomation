package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;

/**
 * Created by universallp on 21.03.2016 18:16.
 */
public enum EnumEntry {
    MENU(new String[] { LibLocalization.ENTRY_INTRO, LibLocalization.ENTRY_CREDITS, LibLocalization.ENTRY_BLOCKPLACER, LibLocalization.ENTRY_DISPENSER, LibLocalization.ENTRY_POKE_STICK, LibLocalization.ENTRY_XPHOPPER, LibLocalization.ENTRY_FILTEREDHOPPER }),
    INTRO(new String[] { LibLocalization.getGuidePage("intro", 1) }),
    CREDITS(new String[] { LibLocalization.getGuidePage("credits", 1) }),

    // Actual entries

    BLOCK_PLACER(new String[] { LibLocalization.getGuidePage("blockplacer", 1), LibLocalization.getGuidePage("blockplacer", 2) }, VABlocks.placer.getRecipe(), 1),
    DISPENSER(new String[] { LibLocalization.getGuidePage("dispenser", 1), LibLocalization.getGuidePage("dispenser", 2) }),
    POKE_STICK(new String[] { LibLocalization.getGuidePage("pokestick", 1), LibLocalization.getGuidePage("pokestick", 2) }, VAItems.itemPokeStick.getRecipe(), 1),
    XPHOPPER(new String[] { LibLocalization.getGuidePage("xphopper", 1), LibLocalization.getGuidePage("xphopper", 2) }, VABlocks.xpHopper.getRecipe(), 1),
    FILTERED_HOPPER(new String[] { LibLocalization.getGuidePage("filteredhopper", 1), LibLocalization.getGuidePage("filteredhopper", 2) }, VABlocks.filterHopper.getRecipe(), 1);

    private String[] entries;
    private Entry instance;
    private VisualRecipe vR;
    private int recipePage;

    EnumEntry(String[] entries) {
        this.entries = entries;
    }

    EnumEntry(String[] entries, VisualRecipe recipe, int pageForRecipe) {
        this.entries = entries;
        this.vR = recipe;
        this.recipePage = pageForRecipe;
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
