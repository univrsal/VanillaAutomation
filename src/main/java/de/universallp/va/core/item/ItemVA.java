package de.universallp.va.core.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 21.03.2016 14:45.
 */
public class ItemVA extends Item {

    public static List<ItemVA> items = new ArrayList<ItemVA>();
    private String itemName;

    protected ItemVA(String name) {
        this.itemName = name;
        setUnlocalizedName(name);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        for (ItemVA item : items)
            item.registerModel();
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        if (getHasSubtypes()) {
            ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
            getSubItems(this, getCreativeTab(), subItems);

            for (int i = 0; i < subItems.size(); i++) {
                ModelResourceLocation mdlResource = new ModelResourceLocation(itemName + (i == 0 ? "" : i), "inventory"); // Different resource location for sub items
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, i, mdlResource);
                ModelBakery.registerItemVariants(this, mdlResource);
            }
        } else {
            ModelResourceLocation mdlResource = new ModelResourceLocation(itemName, "inventory");
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, 0, mdlResource);
        }
    }

    public void register() {
        GameRegistry.registerItem(this, itemName);
    }
}
