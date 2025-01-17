package net.dialingspoon.partialhearts.mixin;

import net.dialingspoon.partialhearts.interfaces.IColorfulHeartRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import terrails.colorfulhearts.api.heart.drawing.SpriteHeartDrawing;

@Mixin(SpriteHeartDrawing.class)
public abstract class ColorfulSpriteHeartDrawingMixin implements IColorfulHeartRenderer {
    @Shadow @Final
    ResourceLocation full;

    @Shadow @Final
    ResourceLocation hardcoreFull;

    @Shadow @Final
    ResourceLocation fullBlinking;

    @Shadow @Final
    ResourceLocation hardcoreFullBlinking;

    @Override
    public ResourceLocation getTexture(boolean hardcore, boolean highlight) {
        if (hardcore) {
            return highlight ? hardcoreFullBlinking : hardcoreFull;
        } else {
            return highlight ? fullBlinking : full;
        }
    }
}
