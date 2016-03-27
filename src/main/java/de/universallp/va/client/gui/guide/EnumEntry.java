package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.block.VABlocks;
import de.universallp.va.core.item.VAItems;

import static de.universallp.va.core.util.References.Local;

/**
 * Created by universallp on 21.03.2016 18:16.
 */
public enum EnumEntry {
    MENU(null, new String[]{Local.ENTRY_INTRO, Local.ENTRY_CREDITS, Local.ENTRY_BLOCKPLACER, Local.ENTRY_DISPENSER, Local.ENTRY_POKE_STICK, Local.ENTRY_XPHOPPER}),
    INTRO(new String[]{Local.getGuidePage("intro", 1)}),
    CREDITS(new String[]{Local.getGuidePage("credits", 1)}),

    // Actual entries

    BLOCK_PLACER(new String[]{Local.getGuidePage("blockplacer", 1), Local.getGuidePage("blockplacer", 2)}, VABlocks.placer.getRecipe(), 1),
    DISPENSER(new String[]{Local.getGuidePage("dispenser", 1), Local.getGuidePage("dispenser", 2)}),
    POKE_STICK(new String[]{Local.getGuidePage("pokestick", 1), Local.getGuidePage("pokestick", 2)}, VAItems.itemPokeStick.getRecipe(), 1),
    XPHOPPER(new String[]{Local.getGuidePage("xphopper", 1), Local.getGuidePage("xphopper", 2)}, VABlocks.xpHopper.getRecipe(), 1);


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
        if (ordinal() - 1 < EnumEntry.MENU.entries.length)
            return EnumEntry.MENU.entries[ordinal() - 1];
        return EnumEntry.MENU.entries[1];
    }
}
