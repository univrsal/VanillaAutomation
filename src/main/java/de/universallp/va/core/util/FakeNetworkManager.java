package de.universallp.va.core.util;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

public class FakeNetworkManager extends NetworkManager {

    public FakeNetworkManager(EnumPacketDirection packetDirection) {
        super(packetDirection);
    }

    @Override
    public boolean isChannelOpen() {
        return true;
    }


    @Override
    public void sendPacket(Packet<?> packetIn) {
        // NO OP
    }
}
