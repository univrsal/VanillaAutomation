package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;

/**
 * Created by universallp on 21.03.2016 18:16.
 */
public enum EnumEntry {
    MENU(null, new String[] { "guide.entry.intro", "guide.entry.credits", "guide.entry.blockplacer", "guide.entry.dispenser" }),
    INTRO(new String[] { "guide.entry.intro.page1" }),
    CREDITS(new String[] { "guide.entry.credits.page1" }),

    // Actual entries

    BLOCK_PLACER(new String[] { "guide.entry.blockplacer.page1", "guide.entry.blockplacer.page2" }, VABlocks.placer.getRecipe(), 1),
    DISPENSER(new String[] { "guide.entry.dispenser.page1" });

    private String[] entries;
    private String text;
    private Entry instance;
    private VisualRecipe vR;
    private int recipePage;

    EnumEntry(String title, String[] entries) {
        this.text = title;
        this.entries = entries;
    }

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
        return EnumEntry.MENU.entries[ordinal() - 1];
    }
}
