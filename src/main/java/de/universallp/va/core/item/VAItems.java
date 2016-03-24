package de.universallp.va.core.item;

import de.universallp.va.core.block.VABlocks;

/**
 * Created by universallp on 21.03.2016 16:39.
 */
public class VAItems {

    public static ItemVA itemGuide;
    public static ItemVABlock itemPlacer;

    public static void init() {
        itemGuide = new ItemGuide();
        itemPlacer = new ItemVABlock(VABlocks.placer);

        ItemVA.items.add(itemGuide);
        registerAllItems();
    }

    private static void registerAllItems() {
        for (ItemVA item : ItemVA.items)
            item.register();
    }
}
