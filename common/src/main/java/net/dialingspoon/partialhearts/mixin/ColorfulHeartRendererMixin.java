package net.dialingspoon.partialhearts.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
import net.dialingspoon.partialhearts.PartialHearts;
import net.dialingspoon.partialhearts.gui.PatternManager;
import net.dialingspoon.partialhearts.interfaces.IColorfulHeartRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import terrails.colorfulhearts.api.heart.drawing.Heart;
import terrails.colorfulhearts.render.HeartRenderer;

import java.io.IOException;

@Mixin(value = HeartRenderer.class, remap = false)
public abstract class ColorfulHeartRendererMixin {

    @Redirect(method = "renderPlayerHearts", at = @At(value = "INVOKE", target = "Lterrails/colorfulhearts/api/heart/drawing/Heart;draw(Lnet/minecraft/client/gui/GuiGraphics;IIZZZ)V"))
    private void renderPartialHeart(Heart heartInstance, GuiGraphics guiGraphics, int heartX, int heartY, boolean hardcore, boolean highlightContainer, boolean highlightHeart, @Local(name = "player") Player player,
                                    @Local(name = "currentHealth") int currentHealth, @Local(name = "absorption") int absorption, @Local(name = "healthHearts") int healthHearts, @Local(name = "index") int index) {
        int lastHeart = Mth.ceil((currentHealth % 20) / 2.0) - 1;
        if (lastHeart == -1 && healthHearts > 0) lastHeart = 9;

        int lastAbsorptionHeart = -1;
        if (absorption != 0) {
            lastAbsorptionHeart = Mth.ceil(((absorption / 2.0) % (20 - healthHearts)) + Math.min(healthHearts, 10)) - 1;
        }
        if (lastAbsorptionHeart == 0 && absorption > 0) lastAbsorptionHeart = 10;

        if (index == lastHeart || index == lastAbsorptionHeart && !heartInstance.isContainer()) {
            try {
                drawBackgroundHeart(guiGraphics, heartInstance, heartX, heartY, hardcore, highlightContainer, highlightHeart);

                boolean highlight = heartInstance.isContainer() ? highlightContainer : highlightHeart;

                NativeImage heartImage = NativeImage.read(PartialHearts.CAPTURED_SPRITES.get(((IColorfulHeartRenderer) ((HeartAccessor) heartInstance).getDrawing()).getTexture(hardcore, highlight)));
                if (index == lastHeart) {
                    PatternManager.renderHearts(heartImage, guiGraphics, player.getHealth(), heartX, heartY);
                } else {
                    PatternManager.renderHearts(heartImage, guiGraphics, player.getAbsorptionAmount(), heartX, heartY);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            heartInstance.draw(guiGraphics, heartX, heartY, hardcore, highlightContainer, highlightHeart);
        }
    }

    private static void drawBackgroundHeart(GuiGraphics guiGraphics, Heart heartInstance, int heartX, int heartY, boolean hardcore, boolean highlightContainer, boolean highlightHeart) throws IOException {
        Heart backgroundHeart = ((HeartAccessor) heartInstance).getBackgroundHeart();
        if (backgroundHeart != null) {
            if (backgroundHeart.isContainer()) {
                ((HeartAccessor) backgroundHeart).getCONTAINER_FULL().draw(guiGraphics, heartX, heartY, hardcore, highlightContainer, highlightHeart);
            } else {
                backgroundHeart.draw(guiGraphics, heartX, heartY, hardcore, highlightContainer, highlightHeart);
            }
        }
    }
}
