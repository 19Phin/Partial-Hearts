package net.dialingspoon.partialhearts.neoforge.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Nullable protected abstract Player getCameraPlayer();
    @Unique
    public float displayHealthFloat;

    @Inject(method = "renderHealthLevel(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;displayHealth:I", opcode = Opcodes.PUTFIELD))
    private void floatDisplayHealthUpdate(GuiGraphics guiGraphics, CallbackInfo ci) {
        displayHealthFloat = getCameraPlayer().getHealth();
    }
}
