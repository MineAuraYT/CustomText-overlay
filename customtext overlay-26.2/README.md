# CustomText Overlay — Minecraft 26.2 / Fabric

Client-side HUD text overlay with an in-game editor.

## In-game controls

- Press **Ctrl + O** to open the editor.
- Edit the text, X/Y position, and colour (`#RRGGBB` or `#AARRGGBB`).
- Toggle the overlay, shadow, and outline.
- Changes are applied to the HUD as you type/click and saved automatically. **No game restart is required.**
- Press Done or Esc to close the editor.

The config is stored at `.minecraft/config/customtext.json`.

## Build (Windows PowerShell)

Requirements: JDK 25, Gradle 9.6.1 or compatible, and internet access for the first dependency download.

```powershell
gradle build --parallel --build-cache
```

After a successful build, the installable remapped mod JAR is in `build\libs`. Copy the non-sources mod JAR into the `JAR` folder in this project, then put that JAR into your Minecraft instance's `mods` folder.

## Compatibility

Targets Minecraft 26.2, Fabric Loader 0.19.3, Fabric API 0.156.0+26.2, Loom 1.17.17, Java 25.

## Verification status

The editor and live-update implementation has been added to the source. It has **not been compiled or tested in-game in this environment**, so do not treat this ZIP as a verified release. Run the build command above; if compilation reports an error, share the complete output so the exact API mismatch can be corrected.


## Mod icon

The 128x128 CustomText Overlay logo is included at `src/main/resources/assets/customtext/icon.png` and registered through the `icon` field in `fabric.mod.json`.
