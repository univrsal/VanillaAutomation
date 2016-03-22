package de.universallp.va.client.gui.guide;

import de.universallp.va.client.gui.GuiGuide;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.References;
import de.universallp.va.core.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by universallp on 21.03.2016 18:18.
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
        this.image = new ResourceLocation(References.MOD_ID, imageLoc);
        this.xOffset = imgXOffset;
        this.yOffset = imgYOffset;
        this.height = height;
        this.width  = width;
    }

    public void draw(int mouseX, int mouseY, int x, int y, GuiGuide parent, float partialTicks) {
        if (image == null && recipe == null) {
            if (text != null) {
                String locText = I18n.format(text);
                Utils.drawWrappedString(locText, x, y + 10, 115, parent.mc.fontRendererObj);
            }

            for (int i = 0; i < menuEntries.size(); i++)
                menuEntries.get(i).draw(mouseX, mouseY, x, y + 9 + (parent.mc.fontRendererObj.FONT_HEIGHT + 2) * i, parent);
        } else {
            if (image != null) {
                parent.mc.renderEngine.bindTexture(image);
                parent.drawTexturedModalRect(x + xOffset, y + yOffset, 0, 0, width, height);

                if (text != null) {
                    String locText = I18n.format(text);
                    Utils.drawWrappedString(locText, x, y + 10 + yOffset + height, 115, parent.mc.fontRendererObj);
                }
            }

            if (recipe != null) {
                recipe.draw(x + 18, y + 10, mouseX, mouseY, parent);

                if (text != null) {
                    String locText = I18n.format(text);
                    Utils.drawWrappedString(locText, x, y + 65, 115, parent.mc.fontRendererObj);
                }
            }
        }
    }

    public void onMouseDown(int mouseX, int mouseY, int x, int y, int button, GuiGuide p) {
        for (int i = 0; i < menuEntries.size(); i++) {

            if (menuEntries.get(i).mouseOver(mouseX, mouseY, x, y + 9 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2) * i)) {
                menuEntries.get(i).onClick(mouseX, mouseY, button, p);
            }
        }
    }

    public VisualRecipe getRecipe() {
        return recipe;
    }
}
