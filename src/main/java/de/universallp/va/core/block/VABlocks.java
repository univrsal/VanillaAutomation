package de.universallp.va.core.block;

import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by universallp on 19.03.2016 12:54.
 */
public class VABlocks {

    public static BlockPlacer placer = new BlockPlacer();
    public static BlockXPHopper xpHopper = new BlockXPHopper();
    public static BlockFilteredHopper filterHopper = new BlockFilteredHopper();

    public static void register() {
        GameRegistry.registerBlock(placer, LibNames.BLOCK_PLACER);
        GameRegistry.registerBlock(xpHopper, LibNames.BLOCK_XPHOPPER);
        GameRegistry.registerBlock(filterHopper, LibNames.BLOCK_FILTEREDHOPPER);

        placer.addRecipe();
        xpHopper.addRecipe();
        filterHopper.addRecipe();

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TilePlacer.class, LibNames.TILE_PLACER);
        GameRegistry.registerTileEntity(TileXPHopper.class, LibNames.TILE_XPHOPPER);
    }

    public static void registerModels() {
        placer.registerModel();
        xpHopper.registerModel();
        filterHopper.registerModel();
    }
}
