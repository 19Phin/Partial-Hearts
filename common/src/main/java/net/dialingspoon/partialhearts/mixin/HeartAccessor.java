package net.dialingspoon.partialhearts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import terrails.colorfulhearts.api.heart.drawing.Heart;
import terrails.colorfulhearts.api.heart.drawing.HeartDrawing;

@Mixin(Heart.class)
public interface HeartAccessor {
    @Accessor
    HeartDrawing getDrawing();

    @Accessor
    Heart getBackgroundHeart();

    @Accessor
    Heart getCONTAINER_FULL();
}
