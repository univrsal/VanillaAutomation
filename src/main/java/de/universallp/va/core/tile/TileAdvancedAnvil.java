package de.universallp.va.core.tile;

import de.universallp.va.core.util.ICustomField;
import de.universallp.va.core.util.Utils;
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

    private List<String> origDesc = new ArrayList<String>();
    private String origname;

    private List<String> newDesc = new ArrayList<String>();
    private String newName;

    public TileAdvancedAnvil() {
        super(4);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        origname = compound.getString("itemName");
        if (compound.hasKey("itemDesc")) {
            NBTTagList list = compound.getTagList("itemDesc", 9);

            for (int i = 0; i < list.tagCount(); i++) {
                origDesc.set(i, list.getCompoundTagAt(i).getString("line"));
            }
        }

        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (origname != null)
            compound.setString("itemName", origname);

        NBTTagList l = new NBTTagList();

        if (origDesc != null) {
            for (int i = 0; i < origDesc.size(); i++) {
                NBTTagCompound t = new NBTTagCompound();
                t.setString("line", origDesc.get(i));
                l.set(i, t);
            }
            compound.setTag("itemDesc", l);
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void setStringField(int id, String val) {
        if (id == -1) {
            newName = val;
        } else {
            newDesc.add(id, val);
        }
        System.out.println(val);
        if (!origDesc.equals(newDesc)) {
            if (items[1] != null)
                items[3] = Utils.withDescription(items[1], newDesc);
        }
    }

    @Override
    public String getStringField(int id) {
        if (id == -1) {
            return origname;
        } else {
            if (id < origDesc.size())
                return origDesc.get(id);
        }
        return "NULL";
    }

    @Override
    public void update() {

    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index == 1 && stack != null) {
            ItemStack input = items[1];

            if (input != null) {
                origname = input.getDisplayName();
                newName = origname;
                origDesc = Utils.readDescFromStack(stack);
                if (origDesc != null)
                    newDesc.addAll(origDesc);
            }
        }


        super.setInventorySlotContents(index, stack);
    }

    public List<String> getOrigDesc() {
        return origDesc;
    }
}
