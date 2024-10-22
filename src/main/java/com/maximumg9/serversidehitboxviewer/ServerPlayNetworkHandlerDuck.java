package com.maximumg9.serversidehitboxviewer;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Unique;

public interface ServerPlayNetworkHandlerDuck {
    @Unique
    void sshitboxviewer$removeTrackedEntity(Entity entity);
}
