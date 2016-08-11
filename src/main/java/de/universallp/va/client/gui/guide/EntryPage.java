package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.GuiGuide;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by universallp on 08.08.2016 17:47.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class EntryPage {

    private String text;

    private ResourceLocation image;
    private int xOffset, yOffset;
    private int width, height;

    private VisualRecipe recipe;

    private ArrayList<MenuEntry> menuEntries = new ArrayList<MenuEntry>();

    public EntryPage(String text) {
        this.text = text;
    }

    public EntryPage(String text, VisualRecipe vS) {
        this.text = text;
        this.recipe = vS;
    }


    public EntryPage(MenuEntry ... entries) {
        Collections.addAll(menuEntries, entries);
    }

    public EntryPage(String text, String imageLoc, int imgXOffset, int imgYOffset, int width, int height) {
        this.text = text;
        this.image = new ResourceLocation(LibNames.MOD_ID, imageLoc);
        this.xOffset = imgXOffset;
        this.yOffset = imgYOffset;
        this.height = height;
        this.width  = width;
    }

    public void draw(int mouseX, int mouseY, int x, int y, GuiGuide parent, float partialTicks) {
        if (image == null && recipe == null) {
            if (text != null) {
                String locText = I18n.format(text);
                Utils.drawWrappedString(locText, x + 3, y + 13, 115, parent.mc.fontRendererObj);
            } else {

                for (int i = 0; i < menuEntries.size(); i++)
                    menuEntries.get(i).draw(mouseX, mouseY, x + 3, y + 11 + (parent.mc.fontRendererObj.FONT_HEIGHT + 2) * i, parent);

            }
        } else {
            if (image != null) {
                parent.mc.renderEngine.bindTexture(image);
                parent.drawTexturedModalRect(x + xOffset, y + yOffset, 0, 0, width, height);

                if (text != null) {
                    String locText = I18n.format(text);
                    Utils.drawWrappedString(locText, x + 3, y + 10 + yOffset + height, 115, parent.mc.fontRendererObj);
                }
            }

            if (recipe != null) {
                recipe.draw(x + 18, y + 10, mouseX, mouseY, parent);

                if (text != null) {
                    String locText = I18n.format(text);
                    Utils.drawWrappedString(locText, x + 3, y + 78, 115, parent.mc.fontRendererObj);
                }
            }
        }
    }

    public void onMouseDown(int mouseX, int mouseY, int x, int y, int button, GuiGuide p) {
        for (int i = 0; i < menuEntries.size(); i++) {

            if (menuEntries.get(i).mouseOver(mouseX, mouseY, x + 3, y + 11 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2) * i)) {
                menuEntries.get(i).onClick(mouseX, mouseY, button, p);
            }
        }
    }

    public VisualRecipe getRecipe() {
        return recipe;
    }
}
