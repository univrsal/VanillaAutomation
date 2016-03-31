package de.universallp.va.core.block;

import de.universallp.va.VanillaAutomation;
import de.universallp.va.client.gui.guide.EnumEntry;
import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.tile.TileXPHopper;
import de.universallp.va.core.util.IEntryProvider;
import de.universallp.va.core.util.libs.LibGuiIDs;
import de.universallp.va.core.util.libs.LibNames;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by universallp on 27.03.2016 22:56.
 */
public class BlockXPHopper extends BlockHopper implements IEntryProvider {

    private static VisualRecipe recipe;

    public BlockXPHopper() {
        setUnlocalizedName(LibNames.BLOCK_XPHOPPER);
        setStepSound(SoundType.METAL);
        setHardness(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation mdlResource = new ModelResourceLocation(LibNames.BLOCK_XPHOPPER, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, mdlResource);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileXPHopper)
                playerIn.openGui(VanillaAutomation.instance, LibGuiIDs.GUI_XPHOPPER, worldIn, pos.getX(), pos.getY(), pos.getZ());

            return true;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileXPHopper();
    }

    @Override
    public VisualRecipe getRecipe() {
        if (recipe != null)
            return recipe;

        ItemStack endereye = new ItemStack(Items.ender_eye, 1);
        ItemStack hopper = new ItemStack(Blocks.hopper, 1);
        recipe = new VisualRecipe(new ItemStack[] { endereye, null, null, hopper }, new ItemStack(VABlocks.xpHopper, 1), VisualRecipe.EnumRecipeType.SHAPED);

        return recipe;
    }

    @Override
    public EnumEntry getEntry() {
        return EnumEntry.XPHOPPER;
    }

    @Override
    public void addRecipe() {
        if (getRecipe() != null)
            GameRegistry.addShapedRecipe(getRecipe().getResult(), "E", "H", 'E', Items.ender_eye, 'H', Blocks.hopper);
    }
}
