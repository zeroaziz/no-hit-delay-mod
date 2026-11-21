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
    @Unique private boolean keyPressed = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        this.attackCooldown = 0;

        if (this.player != null && !this.announced) {
            this.player.sendMessage(new LiteralText("§a[NoHitDelay + SmoothCam] §fActive! (Press Z)"));
            this.announced = true;
        }

        if (this.player != null && this.screen == null) {
            boolean isDown = Keyboard.isKeyDown(Keyboard.KEY_Z);
            if (isDown && !this.keyPressed && this.options != null) {
                this.options.smoothCamera = !this.options.smoothCamera;
                String status = this.options.smoothCamera ? "§aON" : "§cOFF";
                this.player.sendMessage(new LiteralText("§b[Smooth Camera] " + status));
            }
            this.keyPressed = isDown;
        }
    }
}
