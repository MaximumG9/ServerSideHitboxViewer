package com.maximumg9.serversidehitboxviewer.protocol;

import com.maximumg9.serversidehitboxviewer.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

public class UpdateEntityBoxCustomPayload implements CustomPayload {
    public static final CustomPayload.Id<UpdateEntityBoxCustomPayload> ID = new CustomPayload.Id<>(
            Identifier.of("serversidehitboxviewer","update_entity")
    );
    public static final PacketCodec<PacketByteBuf, UpdateEntityBoxCustomPayload> CODEC = CustomPayload.codecOf(
            UpdateEntityBoxCustomPayload::write,
            UpdateEntityBoxCustomPayload::new
    );
    private final int entityID;
    private final Box box;
    public UpdateEntityBoxCustomPayload(Entity entity) {
        this.entityID = entity.getId();
        this.box = entity.getBoundingBox();
    }
    private void write(PacketByteBuf buf) {
        buf.writeVarInt(this.entityID);
        Utils.writeBox(buf,box);
    }
    public UpdateEntityBoxCustomPayload(PacketByteBuf buf) {
        this.entityID = buf.readVarInt();
        this.box = Utils.readBox(buf);
    }
    public Box getBox() { return this.box; }
    public int getEntityID() { return this.entityID; }
    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
