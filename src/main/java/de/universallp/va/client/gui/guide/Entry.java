package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.GuiGuide;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by universallp on 08.08.2016 17:48.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class Entry {

    private List<EntryPage> pages = new ArrayList<EntryPage>();
    private int currentPage = 0;
    private String title;

    Entry(String t, EntryPage... pages) {
        Collections.addAll(this.pages, pages);
        this.title = t;
    }

    public EntryPage getCurrentPage() {
        return pages.get(currentPage);
    }

    public void draw(int mouseX, int mouseY, GuiGuide parent, float partialTicks) {
        parent.mc.fontRenderer.setUnicodeFlag(true);
        if (pages != null && currentPage < pages.size() && currentPage >= 0) {
            int x = getX(parent.width);
            int y = getY(parent.height);
            parent.mc.fontRenderer.drawString(I18n.format(title), x + (120 / 2) - parent.mc.fontRenderer.getStringWidth(I18n.format(title)) / 2, y - 3, new Color(200, 200, 200).getRGB());
            pages.get(currentPage).draw(mouseX, mouseY, getX(parent.width), getY(parent.height), parent, partialTicks);
        }
        parent.mc.fontRenderer.setUnicodeFlag(false);
    }

    public void onClick(int mouseX, int mouseY, int button, GuiGuide parent) {
        pages.get(currentPage).onMouseDown(mouseX, mouseY, getX(parent.width), getY(parent.height), button, parent);
    }

    private int getX(int width) {
        return width / 2 - 57;
    }

    private int getY(int height) {
        return height / 2 - 76;
    }

    public int getMaxPages() {
        return pages.size();
    }

    public int getPage() {
        return currentPage;
    }

    public Entry setPage(int p) {
        currentPage = p;
        return this;
    }
}
