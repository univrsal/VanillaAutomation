package de.universallp.va.core.item;

import de.universallp.va.client.gui.screen.VisualRecipe;
import de.universallp.va.core.util.IEntryProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by universallp on 21.03.2016 14:45 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class ItemVA extends Item implements IEntryProvider {

    private String itemName;
    protected ItemVA(String name) {
        setUnlocalizedName(name);
        itemName = name;
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        if (getHasSubtypes()) {
            NonNullList<ItemStack> subItems = NonNullList.create();
            getSubItems(getCreativeTab(), subItems);
            String name = ForgeRegistries.ITEMS.getKey(this).toString();
            for (int i = 0; i < subItems.size(); i++) {
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(name, "inventory"));
            }
        } else {
            String name = ForgeRegistries.ITEMS.getKey(this).toString();
            ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(name, "inventory"));
        }
    }

    @Override
    public VisualRecipe getRecipe() {
        return null;
    }

    @Override
    public int getEntryID() {
        return -1;
    }

    public void register() {
        setRegistryName(itemName);
        ForgeRegistries.ITEMS.register(this);//, new ResourceLocation(itemName));
    }
}
