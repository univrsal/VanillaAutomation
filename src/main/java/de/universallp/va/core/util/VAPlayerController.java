package de.universallp.va.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copy pasted by universallp on 24.03.2016 21:41 from github.com/vazkii/Botania.
 */
@SideOnly(Side.CLIENT)
public class VAPlayerController extends PlayerControllerMP {


    private float distance = 0F;

    public VAPlayerController(Minecraft mcIn, NetHandlerPlayClient netHandler) {
        super(mcIn, netHandler);
    }

    @Override
    public float getBlockReachDistance() {
        return distance;
    }

    public void setReachDistance(float f) {
        distance = f;
    }
}
