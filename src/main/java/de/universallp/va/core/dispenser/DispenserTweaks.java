package de.universallp.va.core.dispenser;

import de.universallp.va.core.item.VAItems;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by universallp on 19.03.2016 17:35.
 */
public class DispenserTweaks {

    public static DyeBehaviour dyeBehaviour = new DyeBehaviour();
    public static PickaxeBehaviour pickaxeBehaviour = new PickaxeBehaviour();
    public static SwordBehaviour swordBehaviour = new SwordBehaviour();
    public static ShearBehaviour shearBehaviour = new ShearBehaviour();
    public static DiscBehaviour discBehaviour = new DiscBehaviour();
    public static PokeStickBehaviour pokeStickBehaviour = new PokeStickBehaviour();
    public static SeedBehaviour seedBehaviour = new SeedBehaviour();
    public static void register() {
        add(Items.dye, dyeBehaviour);

        add(Items.diamond_pickaxe, pickaxeBehaviour);
        add(Items.iron_pickaxe, pickaxeBehaviour);
        add(Items.golden_pickaxe, pickaxeBehaviour);
        add(Items.stone_pickaxe, pickaxeBehaviour);
        add(Items.wooden_pickaxe, pickaxeBehaviour);

        add(Items.diamond_sword, swordBehaviour);
        add(Items.iron_sword, swordBehaviour);
        add(Items.golden_sword, swordBehaviour);
        add(Items.stone_sword, swordBehaviour);
        add(Items.wooden_sword, swordBehaviour);

        add(Items.shears, shearBehaviour);

        add(Items.record_11, discBehaviour);
        add(Items.record_13, discBehaviour);
        add(Items.record_blocks, discBehaviour);
        add(Items.record_cat, discBehaviour);
        add(Items.record_chirp, discBehaviour);
        add(Items.record_far, discBehaviour);
        add(Items.record_mall, discBehaviour);
        add(Items.record_stal, discBehaviour);
        add(Items.record_strad, discBehaviour);
        add(Items.record_wait, discBehaviour);
        add(Items.record_ward, discBehaviour);

        add(VAItems.itemPokeStick, pokeStickBehaviour);

        add(Items.wheat_seeds, seedBehaviour);
    }

    private static void add(Item i, IBehaviorDispenseItem b) {
        BlockDispenser.dispenseBehaviorRegistry.putObject(i, b);
    }
}
