package de.universallp.va.client.gui;

import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.network.PacketHandler;
import de.universallp.va.core.network.messages.MessageSetFieldServer;
import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.util.Utils;
import de.universallp.va.core.util.libs.LibLocalization;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by universallp on 20.03.2016 15:01.
 */
public class GuiPlacer extends GuiDispenser {

    private int reachDistance;
    private EnumFacing placeFace;
    private boolean useRedstone;

    private GuiButton btnUp;
    private GuiButton btnDown;
    private GuiButton btnFace;
    private GuiButton btnUseRedstone;

    private TilePlacer placer;

    public GuiPlacer(InventoryPlayer playerInv, TilePlacer dispenserInv, int reachDistance, EnumFacing face, boolean useRedstone) {
        super(playerInv, dispenserInv);
        this.reachDistance = reachDistance;
        this.placeFace     = face;
        this.useRedstone = useRedstone;
        placer = dispenserInv;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (Utils.mouseInRect(guiLeft + 135, guiTop + 38, 17, 10, mouseX, mouseY) && ConfigHandler.BLOCK_PLACER_REACH > 1 && !useRedstone)
            drawHoveringText(Collections.singletonList(I18n.format(LibLocalization.GUI_DIST)), mouseX, mouseY);

        if (Utils.mouseInRect(guiLeft + 8, guiTop + 10, 25, 20, mouseX, mouseY))
            drawHoveringText(Collections.singletonList(I18n.format(LibLocalization.BTN_REDSTONE)), mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        if (ConfigHandler.BLOCK_PLACER_REACH > 1 && !useRedstone)
            fontRendererObj.drawString(String.valueOf(reachDistance), (reachDistance > 9 ? 138 : 141), 39, LibNames.TEXT_COLOR);
        fontRendererObj.drawString(I18n.format(LibLocalization.GUI_FACE), 9, 38, LibNames.TEXT_COLOR);
    }

    @Override
    public void initGui() {
        super.initGui();
        btnFace = new GuiButton(0, guiLeft + 8, guiTop + 48, 50, 20, I18n.format(LibNames.GUI_DIR + placeFace.getName()));
        if (ConfigHandler.BLOCK_PLACER_REACH > 1) {
            btnUp = new GuiButton(2, guiLeft + 134, guiTop + 15, 20, 20, "+");
            btnDown = new GuiButton(1, guiLeft + 134, guiTop + 50, 20, 20, "-");
            btnUseRedstone = new GuiButton(3, guiLeft + 8, guiTop + 10, 25, 20, useRedstone ? I18n.format(LibLocalization.YES) : I18n.format(LibLocalization.NO));

            if (reachDistance < 2)
                btnDown.enabled = false;
            if (reachDistance > 15)
                btnUp.enabled = false;


            btnUp.visible = !useRedstone;
            btnDown.visible = !useRedstone;

            buttonList.add(btnDown);
            buttonList.add(btnUp);
            buttonList.add(btnUseRedstone);
        }

        buttonList.add(btnFace);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            placeFace = Utils.getNextFacing(placeFace);
            button.displayString = I18n.format(LibNames.GUI_DIR + placeFace.getName());
            PacketHandler.INSTANCE.sendToServer(new MessageSetFieldServer(1, placeFace.ordinal(), placer.getPos()));

            placer.placeFace = placeFace;
        }

        if (button.id == 1) {
            if (reachDistance > 1) {
                reachDistance--;
                btnUp.enabled = true;
                if (reachDistance < 2)
                    button.enabled = false;
                placer.reachDistance = (byte) reachDistance;
                PacketHandler.INSTANCE.sendToServer(new MessageSetFieldServer(0, reachDistance, placer.getPos()));

            }
        }

        if (button.id == 2) {
            if (reachDistance < ConfigHandler.BLOCK_PLACER_REACH) {
                reachDistance++;
                btnDown.enabled = true;
                if (reachDistance == ConfigHandler.BLOCK_PLACER_REACH)
                    button.enabled = false;
                placer.reachDistance = (byte) reachDistance;
                PacketHandler.INSTANCE.sendToServer(new MessageSetFieldServer(0, reachDistance, placer.getPos()));
            }
        }

        if (button.id == 3) {
            useRedstone = !useRedstone;

            btnDown.visible = !useRedstone;
            btnUp.visible = !useRedstone;
            btnUseRedstone.displayString = useRedstone ? I18n.format(LibLocalization.YES) : I18n.format(LibLocalization.NO);
            PacketHandler.INSTANCE.sendToServer(new MessageSetFieldServer(2, useRedstone ? 1 : 0, placer.getPos()));
        }

        super.actionPerformed(button);
    }
}
