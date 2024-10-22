package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.protocol.ToggleTrackEntity;
import com.mojang.logging.LogUtils;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.function.Consumer;

@Mixin(CustomPayloadC2SPacket.class)
public abstract class CustomPayloadC2SPacketMixin {
    @SuppressWarnings("unchecked")
    @Redirect(method="<clinit>",at= @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;make(Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;"))
    private static <T> T newArrayList(T object, Consumer<? super T> initializer) {
        try {
            ArrayList<CustomPayload.Type> list = (ArrayList<CustomPayload.Type>) object;
            list.add(new CustomPayload.Type(
                    ToggleTrackEntity.ID,
                    ToggleTrackEntity.CODEC
            ));
        } catch (Exception e) {
            LogUtils.getLogger().error("C2S payload init failed with" , e);
        }
        return Util.make(object,initializer);
    }
}
