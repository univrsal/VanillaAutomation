package de.universallp.va.core.util;

import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;

/**
 * Created by universallp on 22.03.2016 16:29.
 */
public interface IEntryProvider {

    VisualRecipe getRecipe();

    EnumEntry getEntry();
}
