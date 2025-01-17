package net.dialingspoon.partialhearts.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static final String KEY_CATEGORY_SPEEDCAP = "key.category.partialhearts";
    public static KeyMapping PARTIALHEARTS_MENU = new KeyMapping("key.partialhearts.menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, KEY_CATEGORY_SPEEDCAP);
}
