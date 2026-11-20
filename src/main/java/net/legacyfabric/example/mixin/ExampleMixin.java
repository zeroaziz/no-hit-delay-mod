package net.legacyfabric.example.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ExampleMixin {

    @Shadow
    private int attackCooldown;

    @Inject(method = "tick", at = @At("HEAD"))
    private void removeHitDelay(CallbackInfo ci) {
        this.attackCooldown = 0;
    }
}
