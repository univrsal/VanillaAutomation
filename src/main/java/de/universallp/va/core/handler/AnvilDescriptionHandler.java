package de.universallp.va.core.handler;

import de.universallp.va.core.item.ItemDescriptionTag;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 06.08.2016 16:01 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class AnvilDescriptionHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onCombinationComplete(AnvilRepairEvent e) {
        if (e.getIngredientInput() != null && e.getIngredientInput().getItem().equals(VAItems.itemDescriptionTag) && e.getIngredientInput().getCount() > 1) {
            ItemStack newInput = e.getIngredientInput().copy();
            newInput.grow(-1);

            if (!e.getEntityPlayer().inventory.addItemStackToInventory(newInput)) {
                e.getEntityPlayer().dropItem(newInput, false);
            }

        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent e) {
        if (e.getRight().getItem().equals(VAItems.itemDescriptionTag) && ItemDescriptionTag.hasDescription(e.getRight()) &&
                !e.getLeft().getItem().equals(VAItems.itemDescriptionTag)) {

            ItemDescriptionTag.EnumTagMode mode = ItemDescriptionTag.getMode(e.getRight());
            List<String> origDesc;
            List<String> newDesc;

            switch (mode) {
                case ADD:
                    origDesc = Utils.readDescFromStack(e.getLeft());
                    newDesc = ItemDescriptionTag.getDescription(e.getRight());
                    if (origDesc == null)
                        origDesc = new ArrayList<String>();
                    if (origDesc.isEmpty()) {
                        origDesc.add(newDesc.get(0));
                    } else {
                        origDesc.set(origDesc.size() - 1, origDesc.get(origDesc.size() - 1).concat(newDesc.get(0)));
                    }

                    e.setOutput(Utils.withDescription(e.getLeft().copy(), origDesc));
                    e.setCost(1);
                    break;
                case ADDBOTTOM:
                    newDesc = ItemDescriptionTag.getDescription(e.getRight());
                    origDesc = Utils.readDescFromStack(e.getLeft());

                    if (origDesc == null)
                        origDesc = new ArrayList<String>();

                    origDesc.addAll(newDesc);

                    e.setOutput(Utils.withDescription(e.getLeft().copy(), origDesc));
                    e.setCost(newDesc.size());
                    break;
                case CLEAR:
                    e.setOutput(Utils.withDescription(e.getLeft().copy(), null));
                    e.setCost(1);
                    break;
                case NONE:
                    newDesc = ItemDescriptionTag.getDescription(e.getRight());
                    e.setOutput(Utils.withDescription(e.getLeft().copy(), newDesc));
                    e.setCost(newDesc.size());
                    break;
            }
        }
    }
}
