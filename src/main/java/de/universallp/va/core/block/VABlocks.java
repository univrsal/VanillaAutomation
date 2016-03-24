package de.universallp.va.core.block;

import de.universallp.va.core.tile.TilePlacer;
import de.universallp.va.core.util.References;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by universallp on 19.03.2016 12:54.
 */
public class VABlocks {
    public static BlockPlacer placer = new BlockPlacer();

    public static void register() {
        GameRegistry.registerBlock(placer, References.BLOCK_PLACER);
        placer.addRecipe();

        registerTiles();
    }

    private static void registerTiles() {
        GameRegistry.registerTileEntity(TilePlacer.class, References.TILE_PLACER);
    }

    public static void registerModels() {
        placer.registerModel();
    }
}
