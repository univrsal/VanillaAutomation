package de.universallp.va.core.block;

import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.References;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by universallp on 19.03.2016 12:54.
 */
public class VABlocks {

    public static BlockPlacer placer = new BlockPlacer();
    public static BlockXPHopper xpHopper = new BlockXPHopper();

    public static void register() {
        GameRegistry.registerBlock(placer, References.BLOCK_PLACER);
        GameRegistry.registerBlock(xpHopper, References.BLOCK_XPHOPPER);

        placer.addRecipe();

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TilePlacer.class, References.TILE_PLACER);
        GameRegistry.registerTileEntity(TileXPHopper.class, References.TILE_XPHOPPER);
    }

    public static void registerModels() {
        placer.registerModel();
        xpHopper.registerModel();
    }
}
