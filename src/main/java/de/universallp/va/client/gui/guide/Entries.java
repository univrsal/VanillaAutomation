package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.libs.LibLocalization;

import java.util.ArrayList;
import java.util.List;

public class Entries {

    private static List<Entry> ENTRIES = new ArrayList<Entry>();

    public static Entry MENU;
    public static Entry INTRO;
    public static Entry CREDITS;

    public static Entry BLOCK_PLACER;
    public static Entry DISPENSER;
    public static Entry XPHOPPER;
    public static Entry FILTERED_HOPPER;
    public static Entry DESCRIPTION_TAG;
    public static Entry CLOCK;
    public static Entry BOP_COMPAT;
    public static Entry TCON_COMPAT;

    public static void init() {
        MenuEntry[] menuEntries = new MenuEntry[LibLocalization.ENTRIES_MENU.length];
        for (int i = 0; i < menuEntries.length; i++)
            menuEntries[i] = new MenuEntry(LibLocalization.ENTRIES_MENU[i], i + 1);

        EntryPage menu = new EntryPage(menuEntries);
        MENU = new Entry(LibLocalization.ENTRY_MENU_TITLE, menu);
        ENTRIES.add(MENU);

        INTRO = addEntry(getPages(LibLocalization.PAGES_INTRO));
        CREDITS = addEntry(getPages(LibLocalization.PAGES_CREDITS));

        BLOCK_PLACER = addEntry(getPages(LibLocalization.PAGES_BLOCK_PLACER, VABlocks.placer.getRecipe(), 1));
        DISPENSER = addEntry(getPages(LibLocalization.PAGES_DISPENSER));
        XPHOPPER = addEntry(getPages(LibLocalization.PAGES_XPHOPPER, VABlocks.xpHopper.getRecipe(), 1));
        FILTERED_HOPPER = addEntry(getPages(LibLocalization.PAGES_FILTERED_HOPPER, VABlocks.filterHopper.getRecipe(), 1));
        DESCRIPTION_TAG = addEntry(getPages(LibLocalization.PAGES_DESCRIPTION_TAG, VAItems.itemDescriptionTag.getRecipe(), 1));
        CLOCK = addEntry(getPages(LibLocalization.PAGES_CLOCK, VABlocks.redstoneclock.getRecipe(), 1));

        BOP_COMPAT = addEntry(getPages(LibLocalization.PAGES_BOP_COMPAT));
        TCON_COMPAT = addEntry(getPages(LibLocalization.PAGES_TCON_COMPAT));
    }

    public static Entry addEntry(EntryPage[] pages) {
        Entry e = new Entry(getTitle(ENTRIES.size() - 1), pages);
        e.setEntryID(ENTRIES.size());
        ENTRIES.add(e);
        return e;
    }

    private static String getTitle(int index) {
        return LibLocalization.ENTRIES_MENU[index];
    }

    private static EntryPage[] getPages(String[] entries) {
        return getPages(entries, null, -1);
    }

    private static EntryPage[] getPages(String[] entries, VisualRecipe vR, int recipePage) {
        EntryPage[] pages = new EntryPage[entries.length];

        for (int i = 0; i < entries.length; i++) {
            if (vR != null && i == recipePage) {
                pages[i] = new EntryPage(entries[i], vR);
            }
            else {
                pages[i] = new EntryPage(entries[i]);
            }
        }
        return pages;
    }

    public static Entry getEntryById(int i) {
        return ENTRIES.isEmpty() ? null : ENTRIES.get(Math.max(Math.min(i, ENTRIES.size() - 1), 0 ));
    }
}
