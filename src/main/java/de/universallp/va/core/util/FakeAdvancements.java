package de.universallp.va.core.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.io.File;

public class FakeAdvancements extends PlayerAdvancements {
    public FakeAdvancements(MinecraftServer server, File p_i47422_2_, EntityPlayerMP player) {
        super(server, p_i47422_2_, player);
    }

    @Override
    public boolean grantCriterion(Advancement p_192750_1_, String p_192750_2_) {
        return false;
    }
}
