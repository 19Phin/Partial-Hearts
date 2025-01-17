package net.dialingspoon.partialhearts.interfaces;

import net.minecraft.resources.ResourceLocation;

public interface IColorfulHeartRenderer {
    ResourceLocation getTexture(boolean hardcore, boolean highlight);
}
