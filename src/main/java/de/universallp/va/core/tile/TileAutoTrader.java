package de.universallp.va.core.tile;

import net.minecraft.util.ITickable;

import java.util.UUID;

/**
 * Created by universallp on 08.08.2016 17:45.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENSE 1.1
 * github.com/UniversalLP/VanillaAutomation
 */
public class TileAutoTrader extends TileVA implements ITickable {

    private int tradeID = 0;
    private int maxTrades = 0;
    private UUID villagerUUID;

    public TileAutoTrader() {
        super(3);
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0)
            tradeID = value;
        else if (id == 1)
            maxTrades = value;
        else
            super.setField(id, value);
    }

    @Override
    public int getField(int id) {
        if (id == 0)
            return tradeID;
        else if (id == 1)
            return maxTrades;
        else
            return super.getField(id);
    }

    public UUID getVillagerUUID() {
        return villagerUUID;
    }

    @Override
    public void update() {

    }
}
