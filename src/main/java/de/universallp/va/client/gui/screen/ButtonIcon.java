package de.universallp.va.client.gui.screen;

import de.universallp.va.core.util.libs.LibResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Created by universallp on 31.03.2016 16:25 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class ButtonIcon extends GuiButton {

    public final boolean drawDefaultTexture;
    private IconType icon;
    public ButtonIcon(int buttonId, int x, int y, IconType type) {
        super(buttonId, x, y, 18, 20, "");
        drawDefaultTexture = true;
        this.icon = type;
    }

    public ButtonIcon(int buttonId, int x, int y, IconType type, boolean drawDefaultTexture) {
        super(buttonId, x, y, type.getWidht(), type.getHeight(), "");
        this.drawDefaultTexture = drawDefaultTexture;
        this.icon = type;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (drawDefaultTexture)
            super.drawButton(mc, mouseX, mouseY);
        else {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        }
        if (this.visible) {
            icon.draw(this, drawDefaultTexture);
        }
    }

    public IconType getIcon() {
        return icon;
    }

    public void setIcon(IconType icon) {
        this.icon = icon;
    }

    public enum IconType {
        WHITELIST(0, 0),
        BLACKLIST(16, 0),
        CHECKED(32, 0),
        UNCHECKED(48, 0),
        ARROW_RIGHT(64, 0, 10, 15),
        ARROW_LEFT(74, 0, 10, 15),
        ARROW_RIGHT_SELECTED(84, 0, 10, 15),
        ARROW_LEFT_SELECTED(94, 0, 10, 15),
        ARROW_RIGHT_DISABLED(104, 0, 10, 15),
        ARROW_LEFT_DISABLED(114, 0, 10, 15);

        private int x, y, widht, height;

        IconType(int x, int y) {
            this.x = x;
            this.y = y;
            this.widht = 16;
            this.height = 16;
        }

        IconType(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.widht = w;
            this.height = h;
        }

        public void draw(GuiButton parent, boolean drawDefaultTexture) {
            GlStateManager.color(1, 1, 1);
            Minecraft.getMinecraft().getTextureManager().bindTexture(LibResources.GUI_ICONS);
            if (drawDefaultTexture) {
                int xPos = parent.xPosition + 2;
                if (parent.displayString.equals(""))
                    xPos = parent.xPosition + parent.width / 2 - this.widht / 2;
                int yPos = parent.yPosition + parent.height / 2 - this.widht / 2;
                Minecraft.getMinecraft().getTextureManager().bindTexture(LibResources.GUI_ICONS);
                parent.drawTexturedModalRect(xPos, yPos, x, y, widht, height);
            } else {
                parent.drawTexturedModalRect(parent.xPosition, parent.yPosition, x, y, widht, height);
            }
        }

        public int getWidht() {
            return widht;
        }

        public int getHeight() {
            return height;
        }

        public IconType toggle() {
            if (ordinal() % 2 == 0)
                return IconType.values()[ordinal() + 1];
            else
                return IconType.values()[ordinal() - 1];
        }
    }
}
