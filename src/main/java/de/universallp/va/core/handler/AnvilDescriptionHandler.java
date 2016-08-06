package de.universallp.va.core.handler;

import de.universallp.va.core.item.ItemDescriptionTag;
import de.universallp.va.core.item.VAItems;
import de.universallp.va.core.util.Utils;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universallp on 06.08.2016 16:01.
 */
public class AnvilDescriptionHandler {

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent e) {
        if (e.getRight().getItem().equals(VAItems.itemDescriptionTag) && ItemDescriptionTag.hasDescription(e.getRight())) {

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
