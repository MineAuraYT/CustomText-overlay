# CustomText Overlay

**A lightweight Minecraft Fabric mod that lets you display customizable text directly on your HUD!**

Developed by **MineAura**.

## ✨ Features

- Display custom text on your Minecraft HUD.
- Edit your text directly inside the game.
- Change text position using X and Y coordinates.
- Customize text colour using HEX codes.
- Toggle text shadow and outline.
- Enable or disable the overlay.
- Changes apply instantly without restarting Minecraft.
- Settings are saved automatically.

## 🎮 In-game Controls

| Key | Action |
|---|---|
| `Ctrl + O` | Open the CustomText Overlay editor |
| `Esc` | Close the editor |

## 📦 Available Versions

This repository contains source code for two Minecraft versions:

| Minecraft Version | Mod Loader |
|---|---|
| 1.21.11 | Fabric |
| 26.2 | Fabric |

### Source Code Folders

- `customtext overlay-1.21.11` — Source code for Minecraft 1.21.11.
- `customtext overlay-26.2` — Source code for Minecraft 26.2.

Each version has its own Gradle project and build instructions.

## 🛠️ Requirements

**Minecraft 1.21.11**
- Fabric Loader 0.18.4 or newer
- Fabric API 0.141.3+1.21.11
- Java 21

**Minecraft 26.2**
- Fabric Loader 0.19.3 or newer
- Fabric API 0.156.0+26.2
- Java 25

## 🔧 Building From Source

1. Download or clone this repository.
2. Open the folder for your Minecraft version.
3. Open PowerShell in that folder.
4. Run:

```powershell
gradle build
```

5. After a successful build, find the mod JAR inside `build/libs/`.

## ⚙️ Configuration

The configuration file is stored at:

`.minecraft/config/customtext.json`

## 📜 License

This project is licensed under the MIT License. See the individual version folders for their license files.

## 👨‍💻 Developer

**MineAura**

GitHub: https://github.com/MineAuraYT

YouTube: https://www.youtube.com/@MineAuraYT

---

*CustomText Overlay — Your text, your HUD!*
