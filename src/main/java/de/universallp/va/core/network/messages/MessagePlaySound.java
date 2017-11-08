package de.universallp.va.core.network.messages;

import de.universallp.va.core.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by universallp on 29.03.2016 22:13 16:31.
 * This file is part of VanillaAutomation which is licenced
 * under the MOZILLA PUBLIC LICENCE 2.0 - mozilla.org/en-US/MPL/2.0/
 * github.com/univrsal/VanillaAutomation
 */
public class MessagePlaySound implements IMessage, IMessageHandler<MessagePlaySound, IMessage> {

    public BlockPos soundPos;
    public String soundlocation;
    public float volume;
    public float pitch;

    public MessagePlaySound() {
    }

    public MessagePlaySound(String loc, BlockPos pos, float p, float v) {
        this.soundPos = pos;
        this.pitch = p;
        this.volume = v;
        this.soundlocation = loc;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        soundPos = PacketHandler.readBlockPos(buf);
        soundlocation = ByteBufUtils.readUTF8String(buf);
        pitch = buf.readFloat();
        volume = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketHandler.writeBlockPos(buf, soundPos);
        ByteBufUtils.writeUTF8String(buf, soundlocation);
        buf.writeFloat(pitch);
        buf.writeFloat(volume);
    }

    @Override
    public IMessage onMessage(MessagePlaySound message, MessageContext ctx) {
        Minecraft mc = Minecraft.getMinecraft();

        if (message.soundlocation == null)
            return null;

        SoundEvent e = new SoundEvent(new ResourceLocation(message.soundlocation));
        mc.world.playSound(message.soundPos, e, SoundCategory.BLOCKS, message.volume, message.pitch, false);
        return null;
    }
}
