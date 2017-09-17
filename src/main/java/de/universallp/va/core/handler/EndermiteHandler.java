package de.universallp.va.core.handler;

import de.universallp.va.core.dispenser.EnderPearlBehaviour;
import de.universallp.va.core.util.VAFakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EndermiteHandler {

    @SubscribeEvent
    public void onEnderPearlThrown(EnderTeleportEvent e) {
        if (e.getEntity() instanceof VAFakePlayer && EnderPearlBehaviour.rng.nextFloat() < 0.05F
                && e.getEntity().getEntityWorld().getGameRules().getBoolean("doMobSpawning")) {

            Entity entity = e.getEntity();
            EntityEndermite entityendermite = new EntityEndermite(entity.world);

            entityendermite.setLocationAndAngles(entity.posX, entity.posY + ((VAFakePlayer) e.getEntity()).eyeHeight, entity.posZ, entity.rotationYaw, entity.rotationPitch);

            e.getEntity().world.spawnEntity(entityendermite);
        }

    }
}
