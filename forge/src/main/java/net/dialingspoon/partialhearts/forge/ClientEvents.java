package net.dialingspoon.partialhearts.forge;

import net.dialingspoon.partialhearts.PartialHearts;
import net.dialingspoon.partialhearts.gui.PatternListScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
public class ClientEvents {
    @Mod.EventBusSubscriber(modid = PartialHearts.MOD_ID, value = Dist.CLIENT)
    public static class ClientNeoForgeEvents {
        @SubscribeEvent
        public static void onKey(InputEvent.Key event) {
            if (ModKeys.PARTIALHEARTS_MENU.consumeClick()) {
                Minecraft.getInstance().setScreen(new PatternListScreen(null));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = PartialHearts.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void initKeys(RegisterKeyMappingsEvent event) {
            event.register(ModKeys.PARTIALHEARTS_MENU);
        }

        @SubscribeEvent
        public static void registerClientReloadListener(RegisterClientReloadListenersEvent event) {
            ((ReloadableResourceManager)Minecraft.getInstance().getResourceManager()).registerReloadListener(new ResourceReloadListener());
        }
    }
}
