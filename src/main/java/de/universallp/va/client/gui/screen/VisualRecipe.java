package de.universallp.va.client.gui.screen;

import de.universallp.va.client.gui.GuiGuide;
import de.universallp.va.core.util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Created by universallp on 22.03.2016 15:59.
 */
public class VisualRecipe {

    private static ResourceLocation grid = new ResourceLocation(References.MOD_ID, "textures/gui/recipe.png");
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

    public void draw(int x, int y, int mouseX, int mouseY, GuiGuide parent) {
        int stack = 0;
        parent.mc.renderEngine.bindTexture(grid);
        ScaledResolution sR = new ScaledResolution(parent.mc);

        parent.drawTexturedModalRect(x, y, 0, 0, 83, 48);
        boolean mouseOver = false;
        main:
        for (int i = 0; i < 3; i++) {
            for (int b = 0; b < 3; b++) {
                if (stack < stacks.length) {
                    int posX = x - 1 + b * 17;
                    int posY = y - 1 + i * 17;
                    ItemStack current = stacks[stack];

                    if (current != null && current.getItem() != null) {
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

        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(result, x + 66, y + 16);

        if (mouseX > x + 66 && mouseX < x + 66 + 17 && mouseY > y + 16 && mouseY < y + 16 + 17) {
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
        SHAPED,
        SHAPELESS,
        SHAPED_ORE,
        SHAPELESS_ORE;
    }
}
