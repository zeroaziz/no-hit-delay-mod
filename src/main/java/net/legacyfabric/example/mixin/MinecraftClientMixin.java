package net.legacyfabric.example.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Shadow private int attackCooldown;
    @Shadow public LocalClientPlayerEntity player;
    @Unique private boolean announced = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        this.attackCooldown = 0;
        if (this.player != null && !this.announced) {
            this.player.sendMessage(new LiteralText("§a[NoHitDelay] §fActive!"));
            this.announced = true;
        }
    }
}
