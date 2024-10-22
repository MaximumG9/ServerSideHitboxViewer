package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.ServerPlayNetworkHandlerDuck;
import com.maximumg9.serversidehitboxviewer.protocol.AddEntityBoxCustomPayload;
import com.maximumg9.serversidehitboxviewer.protocol.RemoveEntityBoxCustomPayload;
import com.maximumg9.serversidehitboxviewer.protocol.ToggleTrackEntity;
import com.maximumg9.serversidehitboxviewer.protocol.UpdateEntityBoxCustomPayload;
import com.mojang.logging.LogUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin extends ServerCommonNetworkHandler implements ServerPlayNetworkHandlerDuck {
    private ServerPlayNetworkHandlerMixin(MinecraftServer server, ClientConnection connection, ConnectedClientData clientData) {
        super(server, connection, clientData);
    }

    @Shadow public ServerPlayerEntity player;
    @Unique
    Set<Entity> trackedEntities = new HashSet<>();

    @Inject(method="tick",at=@At("HEAD"))
    public void tick(CallbackInfo ci) {
        for(Entity e : trackedEntities) {
            this.sendPacket(new CustomPayloadS2CPacket(
                    new UpdateEntityBoxCustomPayload(e)
            ));
        }
    }

    @Unique
    @Override
    public void sshitboxviewer$removeTrackedEntity(Entity entity) {
        if(this.trackedEntities.remove(entity)) {
            this.sendPacket(new CustomPayloadS2CPacket(
                    new RemoveEntityBoxCustomPayload(entity)
            ));
        }
    }

    @Inject(method="onCustomPayload",at=@At("HEAD"))
    public void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        try {
            if (packet.payload().getId().equals(ToggleTrackEntity.ID)) {
                if (packet.payload() instanceof ToggleTrackEntity payload) {
                    int entityID = payload.getEntityID();
                    Entity trackedEntity = this.player.getServerWorld().getEntityById(entityID);
                    if (trackedEntity != null) {
                        if (payload.isStartTracking()) {
                            this.trackedEntities.add(trackedEntity);
                            this.sendPacket(new CustomPayloadS2CPacket(
                                    new AddEntityBoxCustomPayload(trackedEntity)
                            ));
                        } else {
                            this.trackedEntities.remove(trackedEntity);
                            this.sendPacket(new CustomPayloadS2CPacket(
                                    new RemoveEntityBoxCustomPayload(trackedEntity)
                            ));
                        }

                    }

                }

            }
        } catch(Exception e) {
            LogUtils.getLogger().error("Exception handling Server Side Hitbox Viewer Custom Payload", e);
        }
    }
}
