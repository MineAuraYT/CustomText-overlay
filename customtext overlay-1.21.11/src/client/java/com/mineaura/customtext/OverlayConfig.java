package com.mineaura.customtext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** JSON-backed configuration, also edited live through the in-game screen. */
public final class OverlayConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("customtext.json");
    private static Config config = Config.defaults();

    private OverlayConfig() {}

    public static Config current() {
        return config;
    }

    public static void load() {
        try {
            Files.createDirectories(FILE.getParent());
            if (Files.exists(FILE)) {
                Config loaded = GSON.fromJson(Files.readString(FILE), Config.class);
                if (loaded != null) {
                    config = loaded.sanitized();
                }
            } else {
                save();
            }
        } catch (Exception exception) {
            System.err.println("[CustomText Overlay] Could not load config; using defaults: " + exception.getMessage());
            config = Config.defaults();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(config));
        } catch (IOException exception) {
            System.err.println("[CustomText Overlay] Could not save config: " + exception.getMessage());
        }
    }

    public static final class Config {
        public boolean enabled = true;
        public String text = "YOUR TEXT HERE";
        public int x = 12;
        public int y = 12;
        /** ARGB colour. Example: opaque white = 0xFFFFFFFF. */
        public int color = 0xFFFFFFFF;
        public boolean shadow = true;
        public boolean stroke = false;
        public int strokeColor = 0xFF000000;

        public static Config defaults() {
            return new Config();
        }

        private Config sanitized() {
            if (text == null) text = "";
            if (text.length() > 256) text = text.substring(0, 256);
            x = Math.max(0, Math.min(x, 10000));
            y = Math.max(0, Math.min(y, 10000));
            return this;
        }
    }
}
