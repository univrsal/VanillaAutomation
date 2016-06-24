package de.universallp.va.core.tile;

import de.universallp.va.core.util.ICustomField;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 22.06.2016 19:20.
 */
public class TileAdvancedAnvil extends TileVA implements ICustomField, ITickable {

    private List<String> desc = new ArrayList<String>();

    private String name;

    public TileAdvancedAnvil() {
        super(4);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        name = compound.getString("itemName");
        if (compound.hasKey("itemDesc")) {
            NBTTagList list = compound.getTagList("itemDesc", 9);

            for (int i = 0; i < list.tagCount(); i++) {
                desc.set(i, list.getCompoundTagAt(i).getString("line"));
            }
        }

        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("itemName", name);

        NBTTagList l = new NBTTagList();

        for (int i = 0; i < desc.size(); i++) {
            NBTTagCompound t = new NBTTagCompound();
            t.setString("line", desc.get(i));
            l.set(i, t);
        }
        compound.setTag("itemDesc", l);
        return super.writeToNBT(compound);
    }

    @Override
    public void setStringField(int id, String val) {
        if (id == -1) {
            name = val;
        } else {
            desc.set(id, val);
        }
    }

    @Override
    public String getStringField(int id) {
        if (id == -1) {
            return name;
        } else {
            if (id < desc.size())
                return desc.get(id);
        }
        return "NULL";
    }

    @Override
    public void update() {
        ItemStack input = items[1];

        if (input != null) {
            name = input.getDisplayName();
            if (input.hasTagCompound()) {
                NBTTagCompound tag = input.getTagCompound();

                if (tag.hasKey("display")) {
                    NBTTagCompound display = tag.getCompoundTag("Display");
                    //System.out.println(display.hasKey("description"));
                    if (display.hasKey("Lore")) {
                        NBTTagList lore = tag.getTagList("Lore", 9);
                        System.out.println(lore);
                    }
                }
            }
        }
    }
}
