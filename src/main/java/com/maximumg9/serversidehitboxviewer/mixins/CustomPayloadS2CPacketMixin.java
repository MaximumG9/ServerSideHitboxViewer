package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.protocol.AddEntityBoxCustomPayload;
import com.maximumg9.serversidehitboxviewer.protocol.RemoveEntityBoxCustomPayload;
import com.maximumg9.serversidehitboxviewer.protocol.UpdateEntityBoxCustomPayload;
import com.mojang.logging.LogUtils;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.function.Consumer;

@Mixin(CustomPayloadS2CPacket.class)
public abstract class CustomPayloadS2CPacketMixin {
    @SuppressWarnings("unchecked")
    @Redirect(method="<clinit>",at= @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;make(Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;"))
    private static <T> T newArrayList(T object, Consumer<? super T> initializer) {
        try {
            ArrayList<CustomPayload.Type> list = (ArrayList<CustomPayload.Type>) object;
            list.add(new CustomPayload.Type(
                    AddEntityBoxCustomPayload.ID,
                    AddEntityBoxCustomPayload.CODEC
            ));
            list.add(new CustomPayload.Type(
                    RemoveEntityBoxCustomPayload.ID,
                    RemoveEntityBoxCustomPayload.CODEC
            ));
            list.add(new CustomPayload.Type(
                    UpdateEntityBoxCustomPayload.ID,
                    UpdateEntityBoxCustomPayload.CODEC
            ));
        } catch (Exception e) {
            LogUtils.getLogger().error("S2C payload init failed with" , e);
        }

        return Util.make(object,initializer);
    }
}
