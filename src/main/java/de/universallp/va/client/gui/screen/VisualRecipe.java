package de.universallp.va.client.gui.screen;

import de.universallp.va.client.gui.GuiGuide;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by universallp on 22.03.2016 15:59.
 */
public class VisualRecipe {

    private ItemStack[] stacks;
    private ItemStack   result;
    private List<String> tooltip;
    private FontRenderer f;
    private EnumRecipeType type;

    public VisualRecipe(ItemStack[] ingredients, ItemStack result, EnumRecipeType t) {
        this.stacks = ingredients;
        this.result = result;
        this.type = t;
    }

    @SideOnly(Side.CLIENT)
    public void draw(int x, int y, int mouseX, int mouseY, GuiGuide parent) {
        int stack = 0;
        boolean mouseOver = false;
        int titleWidth = parent.mc.fontRendererObj.getStringWidth(type.getLocalizedName());
        int offset = parent.mc.fontRendererObj.FONT_HEIGHT + 3;

        parent.mc.fontRendererObj.drawString(type.getLocalizedName(), x + 43 - titleWidth / 2, y, LibNames.TEXT_COLOR);
        parent.mc.renderEngine.bindTexture(GuiGuide.bg);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        parent.drawTexturedModalRect(x, (y + parent.mc.fontRendererObj.FONT_HEIGHT + 3), 134, 0, 83, 48);

        main:
        for (int i = 0; i < 3; i++) {
            for (int b = 0; b < 3; b++) {
                if (stack < stacks.length) {
                    int posX = x - 2 + b * 17;
                    int posY = (y + offset) - 1 + i * 17;
                    ItemStack current = stacks[stack];

                    if (current != null) {
                        RenderHelper.enableGUIStandardItemLighting();
                        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(current, posX, posY);

                        if (mouseX > posX && mouseX < posX + 17 && mouseY > posY && mouseY < posY + 17) {
                            f = current.getItem().getFontRenderer(current);
                            tooltip = current.getTooltip(parent.mc.thePlayer, parent.mc.gameSettings.advancedItemTooltips);
                            mouseOver = true;
                        }
                    }

                } else
                    break main;
                stack++;
            }
        }

        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(result, x + 66, y + offset + 16);

        if (mouseX > x + 66 && mouseX < x + 66 + 17 && mouseY > y + offset + 16 && mouseY < y + 16 + offset + 17) {
            f = result.getItem().getFontRenderer(result);
            tooltip = result.getTooltip(parent.mc.thePlayer, parent.mc.gameSettings.advancedItemTooltips);
            mouseOver = true;
        }

        if (!mouseOver)
            tooltip = null;
    }

    public List<String> getTooltip() {
        return tooltip;
    }

    public FontRenderer getFontRenderer() {
        return f != null ? f : Minecraft.getMinecraft().fontRendererObj;
    }

    public ItemStack[] getIngredients() {
        return stacks;
    }

    public ItemStack getResult() {
        return result;
    }

    public EnumRecipeType getType() {
        return type;
    }

    public enum EnumRecipeType {
        SHAPED(LibLocalization.RECIPE_SHAPED),
        SHAPELESS(LibLocalization.RECIPE_SHAPELESS),
        SHAPED_ORE(LibLocalization.RECIPE_SHAPED),
        SHAPELESS_ORE(LibLocalization.RECIPE_SHAPELESS);

        private String name;

        EnumRecipeType(String unlocalizedName) {
            this.name = unlocalizedName;
        }

        public String getLocalizedName() {
            return I18n.format(name);
        }
    }
}
