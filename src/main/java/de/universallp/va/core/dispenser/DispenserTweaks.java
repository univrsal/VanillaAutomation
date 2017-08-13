package de.universallp.va.core.dispenser;

import de.universallp.va.core.handler.ConfigHandler;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.LogHelper;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Iterator;

/**
 * Created by universallp on 19.03.2016 17:35 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class DispenserTweaks {

    public static final DyeBehaviour DYE_BEHAVIOUR = new DyeBehaviour();
    public static final ToolBehaviour TOOL_BEHAVIOUR = new ToolBehaviour();
    public static final SwordBehaviour SWORD_BEHAVIOUR = new SwordBehaviour();
    public static final ShearBehaviour SHEAR_BEHAVIOUR = new ShearBehaviour();
    public static final DiscBehaviour DISC_BEHAVIOUR = new DiscBehaviour();
    public static final NameTagBehaviour NAME_TAG_BEHAVIOUR = new NameTagBehaviour();
    public static final StickBehaviour STICK_BEHAVIOUR = new StickBehaviour();

    public static void register() {
        if (ConfigHandler.DISPENSER_USE_DYE)
            add(Items.DYE, DYE_BEHAVIOUR);

        if (ConfigHandler.DISPENSER_USE_TOOLS) {
            add(Items.SHEARS, SHEAR_BEHAVIOUR);
        }

        add(Items.STICK, STICK_BEHAVIOUR);

        if (ConfigHandler.DISPENSER_USE_DISCS) {
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
        }

        if (ConfigHandler.DISPENSER_USE_NAMETAGS)
            add(Items.NAME_TAG, NAME_TAG_BEHAVIOUR);

        if (!Loader.isModLoaded(LibNames.MOD_QUARK) && ConfigHandler.DISPENSER_USE_SEEDS) {
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

    public static void searchRegistry() {
        Iterator<Item> itemIterator = ForgeRegistries.ITEMS.iterator();

        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();

            if (item != null) {
                if (item instanceof ItemSword && ConfigHandler.DISPENSER_USE_WEAPONS) {
                    try {
                        add(item, SWORD_BEHAVIOUR);
                        LogHelper.logError("Added %s to weapon registry", item.getRegistryName());
                    } catch (Exception e) {
                        LogHelper.logError("Couldn't add %s to weapon registry", item.getRegistryName());
                    }
                } else if (item instanceof ItemTool && ConfigHandler.DISPENSER_USE_TOOLS) {
                    try {
                        add(item, TOOL_BEHAVIOUR);
                        LogHelper.logError("Added %s to tool registry", item.getRegistryName());
                    } catch (Exception e) {
                        LogHelper.logError("Couldn't add %s to tool registry", item.getRegistryName());
                    }
                }
            }
        }
    }

    public static void add(Item i, IBehaviorDispenseItem b) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(i, b);
    }
}
