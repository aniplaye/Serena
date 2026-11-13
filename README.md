# Serena Client — Fabric 26.3

Serena is a lightweight, dark-pink utility client for Minecraft Java Edition 26.3.

## What's included

- Complete Java source code and Fabric mod metadata
- Gradle build configuration and dependency versions
- GitHub Actions workflow at `.github/workflows/build.yml`
- Build artifact upload step, so the finished JAR is downloadable from the workflow run

## Build on GitHub (no Gradle wrapper required)

1. Extract this ZIP.
2. Upload **the contents** of the `Serena-Client-Fabric-26.3` folder to the root of a GitHub repository (so `build.gradle` and `.github` are at the repository root).
3. Open the repository's **Actions** tab.
4. Select **Build Serena Client** and run it, or push a commit to `main`/`master`.
5. When the workflow finishes, open the run and download the `serena-client-fabric-26.3` artifact.

The workflow installs JDK 25 and Gradle 9.6.0 itself. It intentionally runs `gradle build` rather than `./gradlew`, so it does not need a `gradlew` script or wrapper JAR and avoids the “No such file or directory” error.

## Build locally

Requirements: JDK 25 and Gradle 9.6.0. From this folder, run:

```bash
gradle --no-daemon clean build
```

The JAR is created in `build/libs/`.

## Install

Copy the built Serena JAR into your Minecraft `mods` folder. Use the Fabric 26.3 profile with Java 25 and install the matching Fabric API version (`0.166.0+26.3`).

## Main controls

- **Right Shift:** open/close the Serena dashboard in-game.
- **Escape:** close the dashboard.

Settings and profiles are stored in `config/serena/`.
