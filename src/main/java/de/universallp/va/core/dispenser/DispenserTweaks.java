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
        add(Items.DYE, dyeBehaviour);

        add(Items.DIAMOND_PICKAXE, pickaxeBehaviour);
        add(Items.IRON_PICKAXE, pickaxeBehaviour);
        add(Items.GOLDEN_PICKAXE, pickaxeBehaviour);
        add(Items.STONE_PICKAXE, pickaxeBehaviour);
        add(Items.WOODEN_PICKAXE, pickaxeBehaviour);

        add(Items.DIAMOND_SWORD, swordBehaviour);
        add(Items.IRON_SWORD, swordBehaviour);
        add(Items.GOLDEN_SWORD, swordBehaviour);
        add(Items.STONE_SWORD, swordBehaviour);
        add(Items.WOODEN_SWORD, swordBehaviour);

        add(Items.SHEARS, shearBehaviour);

        add(Items.RECORD_11, discBehaviour);
        add(Items.RECORD_13, discBehaviour);
        add(Items.RECORD_BLOCKS, discBehaviour);
        add(Items.RECORD_CAT, discBehaviour);
        add(Items.RECORD_CHIRP, discBehaviour);
        add(Items.RECORD_FAR, discBehaviour);
        add(Items.RECORD_MALL, discBehaviour);
        add(Items.RECORD_STAL, discBehaviour);
        add(Items.RECORD_STRAD, discBehaviour);
        add(Items.RECORD_WAIT, discBehaviour);
        add(Items.RECORD_WARD, discBehaviour);
        add(Items.RECORD_MELLOHI, discBehaviour);

        add(VAItems.itemPokeStick, pokeStickBehaviour);

        if (!Loader.isModLoaded(LibNames.MOD_QUARK)) {
            add(Items.WHEAT_SEEDS, new SeedBehaviour(Blocks.WHEAT));
            add(Items.BEETROOT_SEEDS, new SeedBehaviour(Blocks.BEETROOTS));
            add(Items.CARROT, new SeedBehaviour(Blocks.CARROTS));
            add(Items.PUMPKIN_SEEDS, new SeedBehaviour(Blocks.PUMPKIN_STEM));
            add(Items.MELON_SEEDS, new SeedBehaviour(Blocks.MELON_STEM));
            add(Items.POTATO, new SeedBehaviour(Blocks.POTATOES));
            add(Items.NETHER_WART, new SeedBehaviour(Blocks.NETHER_WART));
        } else {
            LogHelper.logInfo("Quark is loaded! Skipping seed dispenser behaviour");
        }
    }

    private static void add(Item i, IBehaviorDispenseItem b) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(i, b);
    }
}
