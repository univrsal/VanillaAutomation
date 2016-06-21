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

    private static final DyeBehaviour DYE_BEHAVIOUR = new DyeBehaviour();
    private static final ToolBehaviour TOOL_BEHAVIOUR = new ToolBehaviour();
    private static final SwordBehaviour SWORD_BEHAVIOUR = new SwordBehaviour();
    private static final ShearBehaviour SHEAR_BEHAVIOUR = new ShearBehaviour();
    private static final DiscBehaviour DISC_BEHAVIOUR = new DiscBehaviour();
    private static final PokeStickBehaviour POKE_STICK_BEHAVIOUR = new PokeStickBehaviour();
    private static final NameTagBehaviour NAME_TAG_BEHAVIOUR = new NameTagBehaviour();

    public static void register() {
        add(Items.DYE, DYE_BEHAVIOUR);

        add(Items.DIAMOND_PICKAXE, TOOL_BEHAVIOUR);
        add(Items.IRON_PICKAXE, TOOL_BEHAVIOUR);
        add(Items.GOLDEN_PICKAXE, TOOL_BEHAVIOUR);
        add(Items.STONE_PICKAXE, TOOL_BEHAVIOUR);
        add(Items.WOODEN_PICKAXE, TOOL_BEHAVIOUR);

        add(Items.DIAMOND_AXE, TOOL_BEHAVIOUR);
        add(Items.IRON_AXE, TOOL_BEHAVIOUR);
        add(Items.GOLDEN_AXE, TOOL_BEHAVIOUR);
        add(Items.STONE_AXE, TOOL_BEHAVIOUR);
        add(Items.WOODEN_PICKAXE, TOOL_BEHAVIOUR);

        add(Items.DIAMOND_SHOVEL, TOOL_BEHAVIOUR);
        add(Items.IRON_SHOVEL, TOOL_BEHAVIOUR);
        add(Items.GOLDEN_SHOVEL, TOOL_BEHAVIOUR);
        add(Items.STONE_SHOVEL, TOOL_BEHAVIOUR);
        add(Items.WOODEN_SHOVEL, TOOL_BEHAVIOUR);

        add(Items.DIAMOND_SWORD, SWORD_BEHAVIOUR);
        add(Items.IRON_SWORD, SWORD_BEHAVIOUR);
        add(Items.GOLDEN_SWORD, SWORD_BEHAVIOUR);
        add(Items.STONE_SWORD, SWORD_BEHAVIOUR);
        add(Items.WOODEN_SWORD, SWORD_BEHAVIOUR);

        add(Items.SHEARS, SHEAR_BEHAVIOUR);

        add(Items.RECORD_11, DISC_BEHAVIOUR);
        add(Items.RECORD_13, DISC_BEHAVIOUR);
        add(Items.RECORD_BLOCKS, DISC_BEHAVIOUR);
        add(Items.RECORD_CAT, DISC_BEHAVIOUR);
        add(Items.RECORD_CHIRP, DISC_BEHAVIOUR);
        add(Items.RECORD_FAR, DISC_BEHAVIOUR);
        add(Items.RECORD_MALL, DISC_BEHAVIOUR);
        add(Items.RECORD_STAL, DISC_BEHAVIOUR);
        add(Items.RECORD_MELLOHI, DISC_BEHAVIOUR);
        add(Items.RECORD_STRAD, DISC_BEHAVIOUR);
        add(Items.RECORD_WAIT, DISC_BEHAVIOUR);
        add(Items.RECORD_WARD, DISC_BEHAVIOUR);

        add(VAItems.itemPokeStick, POKE_STICK_BEHAVIOUR);

        add(Items.NAME_TAG, NAME_TAG_BEHAVIOUR);

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
