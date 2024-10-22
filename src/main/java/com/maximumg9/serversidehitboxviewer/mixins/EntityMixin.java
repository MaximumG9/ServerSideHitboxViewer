package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.ServerPlayNetworkHandlerDuck;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private World world;

    @Inject(method = "remove",at = @At("HEAD"))
    public void removeFromTrackedList(CallbackInfo ci) {
        if(!this.world.isClient) {
            if(this.world instanceof ServerWorld sWorld) {
                sWorld.getServer().getPlayerManager().getPlayerList().forEach((player) -> ((ServerPlayNetworkHandlerDuck) player.networkHandler)
                        .sshitboxviewer$removeTrackedEntity((Entity) (Object) this));
            }
        }
    }
}
