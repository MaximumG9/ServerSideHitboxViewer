package com.maximumg9.serversidehitboxviewer.protocol;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class RemoveEntityBoxCustomPayload implements CustomPayload {
    public static final Id<RemoveEntityBoxCustomPayload> ID = new CustomPayload.Id<>(
            Identifier.of("serversidehitboxviewer","remove_entity")
    );
    public static final PacketCodec<PacketByteBuf, RemoveEntityBoxCustomPayload> CODEC = CustomPayload.codecOf(
            RemoveEntityBoxCustomPayload::write,
            RemoveEntityBoxCustomPayload::new
    );
    private final int entityID;
    public RemoveEntityBoxCustomPayload(Entity entity) {
        this.entityID = entity.getId();
    }
    private void write(PacketByteBuf buf) {
        buf.writeVarInt(this.entityID);
    }
    public RemoveEntityBoxCustomPayload(PacketByteBuf buf) {
        this.entityID = buf.readVarInt();
    }
    public int getEntityID() { return this.entityID; }
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
