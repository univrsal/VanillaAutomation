package de.universallp.va.core.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by universallp on 19.03.2016 12:07.
 */
public class BlockVA extends Block {

    private String blockName;

    public BlockVA(Material materialIn, String blockName) {
        super(materialIn);
        this.blockName = blockName;
        setUnlocalizedName(blockName);
    }


    @SideOnly(Side.CLIENT)
    public void registerModel() {
        ModelResourceLocation mdlResource = new ModelResourceLocation(blockName, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, mdlResource);
    }
}
