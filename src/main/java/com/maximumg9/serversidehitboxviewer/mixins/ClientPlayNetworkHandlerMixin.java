package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.DebugRendererDuck;
import com.maximumg9.serversidehitboxviewer.ServerSideHitboxRenderer;
import com.maximumg9.serversidehitboxviewer.protocol.AddEntityBoxCustomPayload;
import com.maximumg9.serversidehitboxviewer.protocol.RemoveEntityBoxCustomPayload;
import com.maximumg9.serversidehitboxviewer.protocol.UpdateEntityBoxCustomPayload;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.CustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin extends ClientCommonNetworkHandler {
    private ClientPlayNetworkHandlerMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method="onCustomPayload",at=@At("HEAD"), cancellable = true)
    public void onCustomPayload(CustomPayload payload, CallbackInfo ci) {
        switch (payload) {
            case AddEntityBoxCustomPayload cPayload -> {
                ServerSideHitboxRenderer ssHitboxRenderer = ((DebugRendererDuck) this.client.debugRenderer).sshibotviewer$getServerSideHitboxRenderer();
                ssHitboxRenderer.addBox(cPayload.getEntityID(), cPayload.getBox());
            }
            case RemoveEntityBoxCustomPayload cPayload -> {
                ServerSideHitboxRenderer ssHitboxRenderer = ((DebugRendererDuck) this.client.debugRenderer).sshibotviewer$getServerSideHitboxRenderer();
                ssHitboxRenderer.removeBox(cPayload.getEntityID());
            }
            case UpdateEntityBoxCustomPayload cPayload -> {
                ServerSideHitboxRenderer ssHitboxRenderer = ((DebugRendererDuck) this.client.debugRenderer).sshibotviewer$getServerSideHitboxRenderer();
                ssHitboxRenderer.updateBox(cPayload.getEntityID(), cPayload.getBox());
            }
            case null, default -> {
                return;
            }
        }
        ci.cancel();
    }
}
