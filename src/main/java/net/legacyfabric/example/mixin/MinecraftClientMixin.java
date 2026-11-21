package net.legacyfabric.example.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.LiteralText;
import org.lwjgl.input.Keyboard;
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
    @Shadow public GameOptions options;
    @Shadow public Screen screen;

    @Unique private boolean announced = false;
    @Unique private boolean enabled = true;
    @Unique private boolean keyPressed = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTickEnd(CallbackInfo ci) {
        if (this.options != null && this.options.smoothCamera) {
            this.options.smoothCamera = false;
        }

        if (this.enabled) {
            this.attackCooldown = 0;
        }

        if (this.player != null && !this.announced) {
            this.player.sendMessage(new LiteralText("§a[NoHitDelay] §fActive! (Press Z to toggle)"));
            this.announced = true;
        }

        if (this.player != null && this.screen == null) {
            boolean isDown = Keyboard.isKeyDown(Keyboard.KEY_Z);
            if (isDown && !this.keyPressed) {
                this.enabled = !this.enabled;
                String status = this.enabled ? "§aON" : "§cOFF";
                this.player.sendMessage(new LiteralText("§a[NoHitDelay] " + status));
            }
            this.keyPressed = isDown;
        }
    }
}
