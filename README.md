# Serena Client — Fabric 26.3

Serena is a lightweight, dark-pink utility client for Minecraft Java Edition 26.3. It replaces the vanilla title screen with a custom Serena-themed main menu and adds a large in-game dashboard opened with **Right Shift**. The dashboard has no X or minimize buttons; press Right Shift again or use Escape to close it.

## Included

- **Custom main menu:** procedural space backdrop, Serena branding, Singleplayer, Multiplayer, Options and Quit buttons.
- **Client dashboard:** Home, Modules, HUD Editor, Settings, Profiles, Themes and About pages.
- **Utility modules:** FPS Counter, Ping Counter, CPS Counter, FullBright, Keystrokes and Armor HUD.
- **Categories:** HUD, Interface, World and Performance.
- **HUD controls:** horizontal/vertical offsets and a compact scale setting.
- **Settings:** GUI scale, panel opacity, blur intensity, animation toggle and HUD visibility.
- **Profiles and themes:** persistent settings and named module profiles.
- **No combat or movement cheats:** the original prototype's XRay, ESP, fly, speed and similar modules have been removed.

## Requirements

- JDK 25
- Gradle compatible with the Fabric Loom version in `build.gradle`
- Fabric Loader 0.20.1 for Minecraft 26.3
- Fabric API `0.165.2+26.3`

## Build

1. Open the `serena` folder as a Gradle project in IntelliJ IDEA (or use a local Gradle installation).
2. Make sure the project uses JDK 25 for both Gradle and Java compilation.
3. Run `gradle build` from the project folder.
4. The remapped mod jar should be generated under `build/libs/`.

## Install

Place the built Serena jar and Fabric API jar in the Minecraft `mods` folder, then launch the Fabric 26.3 profile using Java 25.

## Controls

- **Right Shift:** open/close the Serena dashboard in a world.
- **Escape:** close the dashboard normally.
- Module keybinds can be changed under Minecraft Options → Controls → Serena.

## Configuration

Settings and profiles are stored in `config/serena/`.
