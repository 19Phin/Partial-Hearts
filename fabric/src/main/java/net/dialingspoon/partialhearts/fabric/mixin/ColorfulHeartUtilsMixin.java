package net.dialingspoon.partialhearts.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terrails.colorfulhearts.render.HeartUtils;

@Mixin(value = HeartUtils.class, remap = false)
public abstract class ColorfulHeartUtilsMixin {

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private static boolean forceColorfulBackground(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "topHealth") int topHealth) {
        return shouldRenderBack(half, index, topHealth);
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    private static boolean forceFullVanillaBackground(boolean half) {
        return false;
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 1), ordinal = 0)
    private static boolean forceVanillaBackground(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "health") int health) {
        return shouldRenderBack(half, index, health);
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 2), ordinal = 0)
    private static boolean forceContainersFull(boolean half) {
        return false;
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 3), ordinal = 0)
    private static boolean forceColorfulAbsorptionBackground(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "topAbsorbing") int topAbsorbing) {
        return shouldRenderBack(half, index, topAbsorbing);
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 4), ordinal = 0)
    private static boolean forceAbsorbingContainersFull(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "maxAbsorbing") int maxAbsorbing, @Local(name = "absorbing") int absorbing) {
        if (!(index < maxAbsorbing))
            return shouldRenderBack(half, index, absorbing);
        else return half;
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 5), ordinal = 0)
    private static boolean forceOverlayColorfulBackground(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "topHealth") int topHealth) {
        return shouldRenderBack(half, index, topHealth);
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 1), ordinal = 1)
    private static boolean forceOverlayFullVanillaBackground(boolean half) {
        return false;
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 6), ordinal = 0)
    private static boolean forceOverlayVanillaBackground(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "health") int health) {
        return shouldRenderBack(half, index, health);
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 7), ordinal = 0)
    private static boolean forceOverlayColorfulAbsorptionBackground(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "topAbsorbing") int topAbsorbing) {
        return shouldRenderBack(half, index, topAbsorbing);
    }

    @ModifyVariable(method = "calculateHearts(Lterrails/colorfulhearts/api/heart/drawing/OverlayHeart;III)[Lterrails/colorfulhearts/api/heart/drawing/Heart;",
            at = @At(value = "STORE", ordinal = 8), ordinal = 0)
    private static boolean forceOverlayAbsorbingContainersFull(boolean half, @Local(name = "i") int index,
                                                     @Local(name = "absorbing") int absorbing) {
        return shouldRenderBack(half, index, absorbing);
    }

    private static boolean shouldRenderBack(boolean half, int heart, int lastHeart) {
        return heart == (lastHeart - 1) / 2 || half;
    }
}
