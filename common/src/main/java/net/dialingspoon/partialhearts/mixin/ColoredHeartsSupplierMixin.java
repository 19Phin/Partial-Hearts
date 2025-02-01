package net.dialingspoon.partialhearts.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
import net.dialingspoon.partialhearts.PartialHearts;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "terrails.colorfulhearts.render.atlas.sources.ColoredHearts$ColoredHeartsSupplier")
public abstract class ColoredHeartsSupplierMixin {
    @Shadow @Final private ResourceLocation spriteLocation;

    @Inject(method = "apply(Lnet/minecraft/client/renderer/texture/atlas/SpriteResourceLoader;)Lnet/minecraft/client/renderer/texture/SpriteContents;", at = @At(value = "RETURN", ordinal = 0))
    private void captureSprites(SpriteResourceLoader spriteResourceLoader, CallbackInfoReturnable<SpriteContents> cir, @Local(name = "image") NativeImage image) {
        PartialHearts.CAPTURED_SPRITES.put(spriteLocation, image.getPixelsRGBA());
    }

}
