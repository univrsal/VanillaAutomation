package de.universallp.va.client.gui;

import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiVAConfig extends GuiConfig {

    public GuiVAConfig(GuiScreen parentScreen) {
        super(parentScreen, getElements(), LibNames.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));

    }

    public static List<IConfigElement> getElements() {
        List<IConfigElement> entries = new ArrayList<>();
        for (String name : ConfigHandler.config.getCategoryNames())
                entries.add(new ConfigElement(ConfigHandler.config.getCategory(name)));
        return entries;
    }
}
