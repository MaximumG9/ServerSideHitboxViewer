package com.maximumg9.serversidehitboxviewer.protocol;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;


public class ToggleTrackEntity implements CustomPayload {
    public static final Id<ToggleTrackEntity> ID = new CustomPayload.Id<>(
            Identifier.of("serversidehitboxviewer","track_entity")
    );
    public static final PacketCodec<PacketByteBuf, ToggleTrackEntity> CODEC = CustomPayload.codecOf(
            ToggleTrackEntity::write,
            ToggleTrackEntity::new
    );
    private final int entityID;
    private final boolean startTracking;
    public ToggleTrackEntity(Entity entity, boolean startTracking) {
        this.entityID = entity.getId();
        this.startTracking = startTracking;
    }
    private void write(PacketByteBuf buf) {
        buf.writeVarInt(this.entityID);
        buf.writeBoolean(this.startTracking);
    }
    public ToggleTrackEntity(PacketByteBuf buf) {
        this.entityID = buf.readVarInt();
        this.startTracking = buf.readBoolean();
    }
    public int getEntityID() {return this.entityID;}

    public boolean isStartTracking() {
        return startTracking;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
