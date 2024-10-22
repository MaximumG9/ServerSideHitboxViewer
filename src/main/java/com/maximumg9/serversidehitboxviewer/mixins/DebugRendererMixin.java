package com.maximumg9.serversidehitboxviewer.mixins;

import com.maximumg9.serversidehitboxviewer.DebugRendererDuck;
import com.maximumg9.serversidehitboxviewer.ServerSideHitboxRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.debug.DebugRenderer.class)
public abstract class DebugRendererMixin implements DebugRendererDuck {
    @Unique
    private ServerSideHitboxRenderer serverSideHitboxRenderer;

    @Inject(method = "<init>", at = @At("CTOR_HEAD"))
    public void init(MinecraftClient client, CallbackInfo ci) {
        this.serverSideHitboxRenderer = new ServerSideHitboxRenderer();
    }

    @Inject(method = "render",at = @At("HEAD"))
    public void render(MatrixStack matrices, Frustum frustum, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        serverSideHitboxRenderer.render(matrices,vertexConsumers,cameraX,cameraY,cameraZ);
    }

    @Unique
    @Override
    public ServerSideHitboxRenderer sshibotviewer$getServerSideHitboxRenderer() {
        return serverSideHitboxRenderer;
    }
}
