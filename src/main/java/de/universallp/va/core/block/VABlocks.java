package de.universallp.va.core.block;

import de.universallp.va.core.tile.TileAutoTrader;
import de.universallp.va.core.tile.TileFilteredHopper;
import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.item.ItemBlock;
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
    public static BlockAutoTrader autoTrader;

    public static void init() {
        placer = new BlockPlacer();
        xpHopper = new BlockXPHopper();
        filterHopper = new BlockFilteredHopper();
        autoTrader = new BlockAutoTrader();
    }

    public static void register() {
        GameRegistry.register(placer);
        GameRegistry.register(xpHopper);
        GameRegistry.register(filterHopper);
        GameRegistry.register(autoTrader);

        GameRegistry.register(new ItemBlock(autoTrader), autoTrader.getRegistryName());
        GameRegistry.register(new ItemBlock(placer), placer.getRegistryName());
        GameRegistry.register(new ItemBlock(xpHopper), xpHopper.getRegistryName());
        GameRegistry.register(new ItemBlock(filterHopper), filterHopper.getRegistryName());

        placer.addRecipe();
        xpHopper.addRecipe();
        filterHopper.addRecipe();

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TilePlacer.class, LibNames.TILE_PLACER);
        GameRegistry.registerTileEntity(TileXPHopper.class, LibNames.TILE_XPHOPPER);
        GameRegistry.registerTileEntity(TileFilteredHopper.class, LibNames.TILE_FILTEREDHOPPER);
        GameRegistry.registerTileEntity(TileAutoTrader.class, LibNames.TILE_AUTOTRADER);
    }

    public static void registerModels() {
        placer.registerModel();
        xpHopper.registerModel();
        filterHopper.registerModel();
        autoTrader.registerModel();
    }
}
