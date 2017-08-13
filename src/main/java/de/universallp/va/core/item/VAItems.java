package de.universallp.va.core.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by universallp on 21.03.2016 16:39 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class VAItems {

    public static ItemGuide itemGuide = new ItemGuide();
    public static ItemDescriptionTag itemDescriptionTag = new ItemDescriptionTag();

    public static void register() {
        itemGuide.register();
        itemDescriptionTag.register();
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        itemGuide.registerModel();
        itemDescriptionTag.registerModel();
    }
}
