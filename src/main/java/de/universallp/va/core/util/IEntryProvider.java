package de.universallp.va.core.util;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;

/**
 * Created by universallp on 22.03.2016 16:29 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public interface IEntryProvider {

    VisualRecipe getRecipe();

    EnumEntry getEntry();

    void addRecipe();
}
