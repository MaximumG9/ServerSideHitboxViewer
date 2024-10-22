package com.maximumg9.serversidehitboxviewer.mixins;

import com.google.common.collect.Lists;
import com.maximumg9.serversidehitboxviewer.Utils;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Mutable
    @Shadow @Final public KeyBinding[] allKeys;

    @Inject(method="load",at=@At("HEAD"))
    public void loadModdedBindings(CallbackInfo ci) {
        List<KeyBinding> newKeys = Lists.newArrayList(allKeys);
        newKeys.addAll(Utils.KEY_BINDINGS);
        allKeys = newKeys.toArray(new KeyBinding[0]);
    }
}
