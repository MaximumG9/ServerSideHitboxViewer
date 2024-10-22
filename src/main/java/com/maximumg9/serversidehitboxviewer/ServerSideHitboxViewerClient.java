package com.maximumg9.serversidehitboxviewer;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ServerSideHitboxViewerClient implements ClientModInitializer {
    public static KeyBinding trackServerSideBinding;
    @Override
    public void onInitializeClient() {
        trackServerSideBinding = Utils.addKeybind(new KeyBinding(
                "key.serversidehitboxviewer.track", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_SEMICOLON, // The keycode of the key
                "category.serversidehitboxviewer.all" // The translation key of the keybinding's category.
        ));
    }
}
