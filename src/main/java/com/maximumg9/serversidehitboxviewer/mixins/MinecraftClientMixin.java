package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.DebugRendererDuck;
import com.maximumg9.serversidehitboxviewer.ServerSideHitboxViewerClient;
import com.maximumg9.serversidehitboxviewer.protocol.ToggleTrackEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public Entity targetedEntity;

    @Shadow @Nullable public abstract ClientPlayNetworkHandler getNetworkHandler();

    @Shadow @Final public DebugRenderer debugRenderer;

    @Inject(method="tick",at=@At("RETURN"))
    private void onEndTick(CallbackInfo ci) {
        if(ServerSideHitboxViewerClient.trackServerSideBinding.wasPressed()) {
            if(this.targetedEntity != null) {
                ClientPlayNetworkHandler handler = this.getNetworkHandler();
                if(handler != null) {
                    handler.sendPacket(new CustomPayloadC2SPacket(
                            new ToggleTrackEntity(this.targetedEntity,
                                    !(
                                            ((DebugRendererDuck)this.debugRenderer)
                                            .sshibotviewer$getServerSideHitboxRenderer()
                                                    .hasBox(this.targetedEntity.getId())
                                    )
                            )

                    ));
                }
            }
        }
    }
}
