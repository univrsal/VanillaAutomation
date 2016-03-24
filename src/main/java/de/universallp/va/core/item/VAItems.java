package de.universallp.va.core.item;

/**
 * Created by universallp on 21.03.2016 16:39.
 */
public class VAItems {

    public static ItemGuide itemGuide;
    public static ItemPokeStick itemPokeStick;

    public static void init() {
        itemGuide = new ItemGuide();
        itemPokeStick = new ItemPokeStick();

        ItemVA.items.add(itemGuide);
        ItemVA.items.add(itemPokeStick);

        registerAllItems();
    }

    private static void registerAllItems() {
        for (ItemVA item : ItemVA.items)
            item.register();
    }
}
