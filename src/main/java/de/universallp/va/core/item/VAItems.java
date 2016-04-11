package de.universallp.va.core.item;

import de.universallp.va.core.item.minecart.ItemVAMinecart;
import de.universallp.va.core.util.libs.LibNames;

/**
 * Created by universallp on 21.03.2016 16:39.
 */
public class VAItems {

    public static ItemGuide itemGuide;
    public static ItemPokeStick itemPokeStick;
    public static ItemVAMinecart itemCartXPHopper;

    public static void init() {
        itemGuide = new ItemGuide();
        itemPokeStick = new ItemPokeStick();
        itemCartXPHopper = new ItemVAMinecart(LibNames.ITEM_XPHOPPERMINECART, ItemVAMinecart.CartType.XP);

        ItemVA.items.add(itemGuide);
        ItemVA.items.add(itemPokeStick);
        ItemVA.items.add(itemCartXPHopper);
    }

    public static void register() {
        for (ItemVA item : ItemVA.items)
            item.register();
    }
}
