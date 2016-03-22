package de.universallp.va.core.item;

/**
 * Created by universallp on 21.03.2016 16:39.
 */
public class VAItems {
    public static ItemVA itemGuide = new ItemGuide();

    public static void init() {
        ItemVA.items.add(itemGuide);

        registerAllItems();
    }

    private static void registerAllItems() {
        for (ItemVA item : ItemVA.items)
            item.register();
    }
}
