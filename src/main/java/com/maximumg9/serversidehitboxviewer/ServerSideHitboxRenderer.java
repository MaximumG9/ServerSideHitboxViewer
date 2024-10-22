package com.maximumg9.serversidehitboxviewer;

import com.mojang.logging.LogUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.util.HashMap;
import java.util.Map;

public class ServerSideHitboxRenderer implements DebugRenderer.Renderer {
    private final Map<Integer,Box> hitboxesToRender = new HashMap<>();
    private static final RGBAColour innerColour = new RGBAColour(0,0.75f,0,0.25f);
    private static final RGBAColour outline = new RGBAColour(0,1,0,1.0f);
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        hitboxesToRender.values().forEach( (box) -> {
            DebugRenderer.drawBox(
                    matrices,
                    vertexConsumers,
                    box.offset(-cameraX, -cameraY, -cameraZ),
                    innerColour.red(),
                    innerColour.green(),
                    innerColour.blue(),
                    innerColour.alpha()
                    );
            VertexRendering.drawBox(
                    matrices,
                    vertexConsumers.getBuffer(RenderLayer.getDebugLineStrip(4)),
                    box.offset(-cameraX,-cameraY,-cameraZ),
                    outline.red(),
                    outline.green(),
                    outline.blue(),
                    outline.alpha()
            );
            }
        );
    }

    public void updateBox(int id, Box newBox) {
        this.hitboxesToRender.replace(id, newBox);
    }

    public void addBox(int id, Box box) {
        hitboxesToRender.put(id, box);
        LogUtils.getLogger().info("added box {}", id);
    }

    public void removeBox(int id) {
        hitboxesToRender.remove(id);
        LogUtils.getLogger().info("removed box {}", id);
    }

    public boolean hasBox(int id) {
        return hitboxesToRender.containsKey(id);
    }

    @Override
    public void clear() {
        DebugRenderer.Renderer.super.clear();
    }
}
