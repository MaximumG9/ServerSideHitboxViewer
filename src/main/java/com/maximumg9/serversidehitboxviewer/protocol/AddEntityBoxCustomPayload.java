package com.maximumg9.serversidehitboxviewer.protocol;

import com.maximumg9.serversidehitboxviewer.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

public class AddEntityBoxCustomPayload implements CustomPayload {
    public static final CustomPayload.Id<AddEntityBoxCustomPayload> ID = new CustomPayload.Id<>(
            Identifier.of("serversidehitboxviewer","add_entity_box")
    );
    public static final PacketCodec<PacketByteBuf, AddEntityBoxCustomPayload> CODEC = CustomPayload.codecOf(
            AddEntityBoxCustomPayload::write,
            AddEntityBoxCustomPayload::new
    );
    private final int entityID;
    private final Box box;
    public AddEntityBoxCustomPayload(Entity entity) {
        this.entityID = entity.getId();
        this.box = entity.getBoundingBox().offset(entity.getPos());
    }
    private void write(PacketByteBuf buf) {
        buf.writeVarInt(this.entityID);
        Utils.writeBox(buf,box);
    }
    public AddEntityBoxCustomPayload(PacketByteBuf buf) {
        this.entityID = buf.readVarInt();
        this.box = Utils.readBox(buf);
    }
    public Box getBox() { return this.box; }
    public int getEntityID() { return this.entityID; }
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
