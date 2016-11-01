package de.universallp.va.core.item;

/**
 * Created by universallp on 21.03.2016 16:39 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/UniversalLP/VanillaAutomation
 */
public class VAItems {

    public static ItemGuide itemGuide;
    public static ItemPokeStick itemPokeStick;
    public static ItemDescriptionTag itemDescriptionTag;

    public static void init() {
        itemGuide = new ItemGuide();
        itemPokeStick = new ItemPokeStick();
        itemDescriptionTag = new ItemDescriptionTag();
    }

    public static void register() {
        for (ItemVA item : ItemVA.items)
            item.register();
    }
}
