package com.mineaura.customtext;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class CustomTextClient implements ClientModInitializer {
    public static final String MOD_ID = "customtext";
    private static boolean editorShortcutWasDown;

    @Override
    public void onInitializeClient() {
        OverlayConfig.load();

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.fromNamespaceAndPath(MOD_ID, "text_overlay"),
                (graphics, deltaTracker) -> {
                    OverlayConfig.Config config = OverlayConfig.current();
                    if (!config.enabled || config.text == null || config.text.isBlank()) return;

                    var font = net.minecraft.client.Minecraft.getInstance().font;
                    int x = Math.max(0, config.x);
                    int y = Math.max(0, config.y);
                    if (config.stroke) {
                        int outline = config.strokeColor;
                        graphics.drawString(font, config.text, x - 1, y, outline, false);
                        graphics.drawString(font, config.text, x + 1, y, outline, false);
                        graphics.drawString(font, config.text, x, y - 1, outline, false);
                        graphics.drawString(font, config.text, x, y + 1, outline, false);
                        graphics.drawString(font, config.text, x - 1, y - 1, outline, false);
                        graphics.drawString(font, config.text, x + 1, y - 1, outline, false);
                        graphics.drawString(font, config.text, x - 1, y + 1, outline, false);
                        graphics.drawString(font, config.text, x + 1, y + 1, outline, false);
                    }
                    graphics.drawString(font, config.text, x, y, config.color, config.shadow);
                }
        );

        // Ctrl+O opens the editor. Polling GLFW avoids relying on a version-specific keybinding constructor.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            long window = client.getWindow().handle();
            boolean ctrl = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
                    || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
            boolean down = ctrl && GLFW.glfwGetKey(window, GLFW.GLFW_KEY_O) == GLFW.GLFW_PRESS;
            if (down && !editorShortcutWasDown && client.screen == null) {
                client.setScreen(new OverlayEditorScreen());
            }
            editorShortcutWasDown = down;
        });
    }
}
