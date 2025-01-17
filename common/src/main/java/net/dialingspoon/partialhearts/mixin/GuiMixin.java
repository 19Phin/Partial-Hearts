package net.dialingspoon.partialhearts.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.dialingspoon.partialhearts.PartialHearts;
import net.dialingspoon.partialhearts.gui.PatternManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    protected abstract void renderHeart(GuiGraphics arg, Gui.HeartType arg2, int i, int j, boolean bl, boolean bl2, boolean bl3);
    @Unique
    public float partialhearts$displayHealthFloat;
    @Unique
    private boolean partialhearts$first = true;
    @Unique
    private boolean partialhearts$aborptionFirst = true;

    @Redirect(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V", ordinal = 1))
    private void renderAbsorptionHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int heartX, int heartY, boolean hardcore, boolean blinking, boolean half) {
        float absorptionAmount = Minecraft.getInstance().player.getAbsorptionAmount();

        if (partialhearts$aborptionFirst) {
            partialhearts$aborptionFirst = false;
            PatternManager.renderHearts(getImage(heartType, hardcore, blinking), guiGraphics, absorptionAmount, heartX, heartY);
        } else {
            renderHeart(guiGraphics, heartType, heartX, heartY, hardcore, blinking, false);
        }
    }
    @Redirect(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V", ordinal = 2))
    private void renderFlashingHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int heartX, int heartY, boolean hardcore, boolean blinking, boolean half) {
        float healthAmount = blinking ? partialhearts$displayHealthFloat : Minecraft.getInstance().player.getHealth();

        if (partialhearts$first) {
            partialhearts$first = false;
            PatternManager.renderHearts(getImage(heartType, hardcore, blinking), guiGraphics, healthAmount, heartX, heartY);
        } else {
            renderHeart(guiGraphics, heartType, heartX, heartY, hardcore, blinking, false);
        }
    }
    @Redirect(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V", ordinal = 3))
    private void renderHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int heartX, int heartY, boolean hardcore, boolean blinking, boolean half) {
        float healthAmount = Minecraft.getInstance().player.getHealth();

        if (partialhearts$first) {
            partialhearts$first = false;
            PatternManager.renderHearts(getImage(heartType, hardcore, blinking), guiGraphics, healthAmount, heartX, heartY);
        } else {
            renderHeart(guiGraphics, heartType, heartX, heartY, hardcore, blinking, false);
        }
    }

    @Unique
    private NativeImage getImage(Gui.HeartType heartType, boolean hardcore, boolean blinking) {
        try {
            return NativeImage.read(PartialHearts.CAPTURED_SPRITES.get(heartType.getSprite(hardcore, false, blinking)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Inject(method = "renderHearts", at = @At("TAIL"))
    public void resetFirstHeart(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, float f, int m, int n, int o, boolean bl, CallbackInfo ci) {
        partialhearts$first = true;
        partialhearts$aborptionFirst = true;
    }
}
