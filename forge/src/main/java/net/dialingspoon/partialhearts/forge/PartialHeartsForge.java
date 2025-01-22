package net.dialingspoon.partialhearts.forge;

import net.dialingspoon.partialhearts.PartialHearts;
import net.dialingspoon.partialhearts.gui.PatternListScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(PartialHearts.MOD_ID)
public final class PartialHeartsForge {
    public PartialHeartsForge() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((container, screen) -> new PatternListScreen(screen)));
        }
    }
}
