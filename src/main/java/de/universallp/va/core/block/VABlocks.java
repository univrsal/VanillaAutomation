package de.universallp.va.core.block;

import de.universallp.va.core.tile.*;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by universallp on 08.08.2016 17:47.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class VABlocks {

    public static BlockPlacer placer;
    public static BlockXPHopper xpHopper;
    public static BlockFilteredHopper filterHopper;
    public static BlockClock redstoneclock;

    public static void init() {
        placer = new BlockPlacer();
        xpHopper = new BlockXPHopper();
        filterHopper = new BlockFilteredHopper();
        redstoneclock = new BlockClock();
    }

    public static void register() {
        ForgeRegistries.BLOCKS.register(placer);
        ForgeRegistries.BLOCKS.register(xpHopper);
        ForgeRegistries.BLOCKS.register(filterHopper);
        ForgeRegistries.BLOCKS.register(redstoneclock);

        //ForgeRegistries.ITEMS.register(new ItemBlock(placer));
        //ForgeRegistries.ITEMS.register(new ItemBlock(xpHopper));
        //ForgeRegistries.ITEMS.register(new ItemBlock(filterHopper));
        //ForgeRegistries.ITEMS.register(new ItemBlock(redstoneclock));

        placer.addRecipe();
        xpHopper.addRecipe();
        filterHopper.addRecipe();
        redstoneclock.addRecipe();
        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TilePlacer.class, LibNames.TILE_PLACER);
        GameRegistry.registerTileEntity(TileXPHopper.class, LibNames.TILE_XPHOPPER);
        GameRegistry.registerTileEntity(TileFilteredHopper.class, LibNames.TILE_FILTEREDHOPPER);
        GameRegistry.registerTileEntity(TileClock.class, LibNames.TILE_CLOCK);
    }

    public static void registerModels() {
        placer.registerModel();
        xpHopper.registerModel();
        filterHopper.registerModel();
        redstoneclock.registerModel();
    }
}
