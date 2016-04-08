package de.universallp.va.core.dispenser;

import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.LogHelper;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

/**
 * Created by universallp on 19.03.2016 17:35.
 */
public class DispenserTweaks {

    public static final DyeBehaviour dyeBehaviour = new DyeBehaviour();
    public static final PickaxeBehaviour pickaxeBehaviour = new PickaxeBehaviour();
    public static final SwordBehaviour swordBehaviour = new SwordBehaviour();
    public static final ShearBehaviour shearBehaviour = new ShearBehaviour();
    public static final DiscBehaviour discBehaviour = new DiscBehaviour();
    public static final PokeStickBehaviour pokeStickBehaviour = new PokeStickBehaviour();

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

        if (!Loader.isModLoaded(LibNames.MOD_QUARK)) {
            add(Items.wheat_seeds, new SeedBehaviour(Blocks.wheat));
            add(Items.beetroot_seeds, new SeedBehaviour(Blocks.beetroots));
            add(Items.carrot, new SeedBehaviour(Blocks.carrots));
            add(Items.pumpkin_seeds, new SeedBehaviour(Blocks.pumpkin_stem));
            add(Items.melon_seeds, new SeedBehaviour(Blocks.melon_stem));
            add(Items.potato, new SeedBehaviour(Blocks.potatoes));
            add(Items.nether_wart, new SeedBehaviour(Blocks.nether_wart));
        } else {
            LogHelper.logInfo("Quark is loaded! Skipping seed dispenser behaviour");
        }
    }

    private static void add(Item i, IBehaviorDispenseItem b) {
        BlockDispenser.dispenseBehaviorRegistry.putObject(i, b);
    }
}
