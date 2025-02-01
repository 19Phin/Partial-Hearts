package net.dialingspoon.partialhearts.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.NativeImage;
import net.dialingspoon.partialhearts.PartialHearts;
import net.dialingspoon.partialhearts.PatternManager;
import net.dialingspoon.partialhearts.interfaces.IGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin implements IGui {
    @Unique
    private boolean partialhearts$first = true;
    @Unique
    private boolean partialhearts$aborptionFirst = true;
    @Unique
    private boolean partialhearts$blinkingCalled = false;
    @Unique
    public float partialhearts$displayHealthFloat;
    public void setdisplayHealthFloat(float value) {
        partialhearts$displayHealthFloat = value;
    }

    @WrapOperation(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V", ordinal = 1))
    private void renderAbsorptionHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int heartX, int heartY, boolean hardcore, boolean blinking, boolean half, Operation<Void> original) {
        float absorptionAmount = Minecraft.getInstance().player.getAbsorptionAmount();

        if (partialhearts$aborptionFirst) {
            partialhearts$aborptionFirst = false;
            PatternManager.renderHeart(getImage(heartType, hardcore, blinking), guiGraphics, absorptionAmount, heartX, heartY);
        } else {
            original.call(instance, guiGraphics, heartType, heartX, heartY, hardcore, blinking, false);
        }
    }
    @WrapOperation(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V", ordinal = 2))
    private void renderFlashingHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int heartX, int heartY, boolean hardcore, boolean blinking, boolean half, Operation<Void> original) {
        float healthAmount = partialhearts$displayHealthFloat;

        if (partialhearts$first) {
            partialhearts$first = false;
            partialhearts$blinkingCalled = true;
            PatternManager.renderHeart(getImage(heartType, hardcore, blinking), guiGraphics, healthAmount, heartX, heartY);
        } else {
            original.call(instance, guiGraphics, heartType, heartX, heartY, hardcore, blinking, false);
        }
    }
    @WrapOperation(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V", ordinal = 3))
    private void renderHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int heartX, int heartY, boolean hardcore, boolean blinking, boolean half, Operation<Void> original) {
        if (!partialhearts$blinkingCalled) {
            float healthAmount = Minecraft.getInstance().player.getHealth();

            if (partialhearts$first) {
                partialhearts$first = false;
                PatternManager.renderHeart(getImage(heartType, hardcore, blinking), guiGraphics, healthAmount, heartX, heartY);
            } else {
                original.call(instance, guiGraphics, heartType, heartX, heartY, hardcore, blinking, false);
            }
        }
    }

    @Unique
    private NativeImage getImage(Gui.HeartType heartType, boolean hardcore, boolean blinking) {
        return PatternManager.loadImageFromArray(PartialHearts.CAPTURED_SPRITES.get(heartType.getSprite(hardcore, false, blinking)));
    }

    @Inject(method = "renderHearts", at = @At("TAIL"))
    public void resetFirstHeart(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, float f, int m, int n, int o, boolean bl, CallbackInfo ci) {
        partialhearts$first = true;
        partialhearts$aborptionFirst = true;
        partialhearts$blinkingCalled = false;
    }
}
