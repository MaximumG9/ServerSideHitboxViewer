package com.maximumg9.serversidehitboxviewer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Box;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Utils {
    public static Box readBox(PacketByteBuf buf) {
        return new Box(
                buf.readDouble(),buf.readDouble(),buf.readDouble(),
                buf.readDouble(),buf.readDouble(),buf.readDouble()
        );
    }
    public static void writeBox(PacketByteBuf buf, Box box) {
        buf.writeDouble(box.minX);
        buf.writeDouble(box.minY);
        buf.writeDouble(box.minZ);
        buf.writeDouble(box.maxX);
        buf.writeDouble(box.maxY);
        buf.writeDouble(box.maxZ);
    }
    public static Set<KeyBinding> KEY_BINDINGS = new HashSet<>();

    public static KeyBinding addKeybind(KeyBinding binding) {
        if (MinecraftClient.getInstance().options != null) {
            throw new IllegalStateException("GameOptions has already been initialised");
        }

        // This will do nothing if the category already exists.
        addCategory(binding.getCategory());
        KEY_BINDINGS.add(binding);
        return binding;
    }

    public static void addCategory(String category) {
        Map<String,Integer> categoryOrderMap = KeyBinding.CATEGORY_ORDER_MAP;
        Optional<Integer> largest = categoryOrderMap.values().stream().max(Integer::compareTo);
        int max = largest.orElse(0);
        categoryOrderMap.put(category,max);
    }
}
