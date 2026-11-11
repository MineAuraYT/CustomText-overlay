package com.mineaura.customtext;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** In-game editor. Changes are applied to the HUD and saved as the user types. */
public final class OverlayEditorScreen extends Screen {
    private EditBox textField;
    private EditBox xField;
    private EditBox yField;
    private EditBox colorField;
    private Button enabledButton;
    private Button shadowButton;
    private Button strokeButton;

    public OverlayEditorScreen() {
        super(Component.literal("CustomText Overlay"));
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 120;
        OverlayConfig.Config config = OverlayConfig.current();

        textField = new EditBox(this.font, left, 54, 240, 20, Component.literal("Overlay text"));
        textField.setMaxLength(256);
        textField.setValue(config.text);
        textField.setResponder(value -> {
            config.text = value;
            OverlayConfig.save();
        });
        addRenderableWidget(textField);

        xField = numberField(left, 100, "X", config.x, true);
        yField = numberField(left + 126, 100, "Y", config.y, false);

        colorField = new EditBox(this.font, left, 146, 240, 20, Component.literal("Text colour (hex)"));
        colorField.setMaxLength(9);
        colorField.setValue(String.format("#%08X", config.color));
        colorField.setResponder(value -> {
            try {
                String hex = value.startsWith("#") ? value.substring(1) : value;
                long parsed = Long.parseLong(hex, 16);
                if (hex.length() == 6) parsed |= 0xFF000000L;
                if (hex.length() == 6 || hex.length() == 8) {
                    config.color = (int) parsed;
                    OverlayConfig.save();
                }
            } catch (NumberFormatException ignored) {
                // Keep the last valid colour while the user is typing.
            }
        });
        addRenderableWidget(colorField);

        enabledButton = addRenderableWidget(Button.builder(enabledLabel(config), button -> {
            config.enabled = !config.enabled;
            button.setMessage(enabledLabel(config));
            OverlayConfig.save();
        }).bounds(left, 184, 76, 20).build());

        shadowButton = addRenderableWidget(Button.builder(shadowLabel(config), button -> {
            config.shadow = !config.shadow;
            button.setMessage(shadowLabel(config));
            OverlayConfig.save();
        }).bounds(left + 82, 184, 76, 20).build());

        strokeButton = addRenderableWidget(Button.builder(strokeLabel(config), button -> {
            config.stroke = !config.stroke;
            button.setMessage(strokeLabel(config));
            OverlayConfig.save();
        }).bounds(left + 164, 184, 76, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Reset"), button -> {
            OverlayConfig.Config defaults = OverlayConfig.Config.defaults();
            config.enabled = defaults.enabled;
            config.text = defaults.text;
            config.x = defaults.x;
            config.y = defaults.y;
            config.color = defaults.color;
            config.shadow = defaults.shadow;
            config.stroke = defaults.stroke;
            config.strokeColor = defaults.strokeColor;
            textField.setValue(config.text);
            xField.setValue(Integer.toString(config.x));
            yField.setValue(Integer.toString(config.y));
            colorField.setValue(String.format("#%08X", config.color));
            enabledButton.setMessage(enabledLabel(config));
            shadowButton.setMessage(shadowLabel(config));
            strokeButton.setMessage(strokeLabel(config));
            OverlayConfig.save();
        }).bounds(left, 220, 76, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Done"), button -> onClose())
                .bounds(left + 164, 220, 76, 20).build());
    }

    private EditBox numberField(int x, int y, String label, int initial, boolean isX) {
        EditBox field = new EditBox(this.font, x, y, 114, 20, Component.literal(label));
        field.setMaxLength(5);
        field.setValue(Integer.toString(initial));
        field.setResponder(value -> {
            try {
                int parsed = Integer.parseInt(value);
                if (parsed >= 0 && parsed <= 10000) {
                    if (isX) OverlayConfig.current().x = parsed;
                    else OverlayConfig.current().y = parsed;
                    OverlayConfig.save();
                }
            } catch (NumberFormatException ignored) {
                // Ignore incomplete input until it becomes a valid number.
            }
        });
        addRenderableWidget(field);
        return field;
    }

    private static Component enabledLabel(OverlayConfig.Config c) {
        return Component.literal("Overlay: " + (c.enabled ? "ON" : "OFF"));
    }
    private static Component shadowLabel(OverlayConfig.Config c) {
        return Component.literal("Shadow: " + (c.shadow ? "ON" : "OFF"));
    }
    private static Component strokeLabel(OverlayConfig.Config c) {
        return Component.literal("Outline: " + (c.stroke ? "ON" : "OFF"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawString(this.font, "CustomText Overlay Editor", this.width / 2 - 120, 28, 0xFFFFFFFF, false);
        graphics.drawString(this.font, "Text", this.width / 2 - 120, 42, 0xFFAAAAAA, false);
        graphics.drawString(this.font, "X", this.width / 2 - 120, 88, 0xFFAAAAAA, false);
        graphics.drawString(this.font, "Y", this.width / 2 + 6, 88, 0xFFAAAAAA, false);
        graphics.drawString(this.font, "Colour: #RRGGBB or #AARRGGBB", this.width / 2 - 120, 134, 0xFFAAAAAA, false);
        graphics.drawString(this.font, "Changes apply instantly; no restart needed.", this.width / 2 - 120, 254, 0xFF80FF80, false);
    }

    @Override
    public void onClose() {
        OverlayConfig.save();
        if (this.minecraft != null) this.minecraft.setScreen(null);
    }
}
