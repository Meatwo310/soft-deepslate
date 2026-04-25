# custom-mdk template guide

A Minecraft mod template supporting multiple mod loaders, managed in a single Gradle multi-project build.

## Supported Platforms

| Minecraft | Fabric | Legacy Forge | NeoForge |
|-----------|:------:|:------------:|:--------:|
| 1.18.2    |   ⏳    |    🚧 *1     |    -     |
| 1.19.2    |   ⏳    |    🚧 *1     |    -     |
| 1.20.1    |   ⏳    |    🚧 *1     |    ❌     |
| 1.21.1    |   ⏳    |      ⏳       |    ✅     |
| 26.1      |   ✅    |      ❌       |    ✅     |

✅ Supported | 🚧 Partial support | ⏳ Planned | ❌ Not supported

*1 Mixin is not currently supported.

## Setup

1. Fork this repository (to update your repo with upstream changes later)
2. Select the platforms you need in `settings.gradle.kts` by removing unused `include(...)` lines to reduce Gradle's configuration and load time
3. Open in IntelliJ IDEA, then edit `gradle.properties` to set your mod's metadata (and fabric entry points)
4. Create a `README.md` *in the repository root*, and update or replace `LICENSE` to reflect your own copyright
   * Don't edit `docs/README.md` if you want to stay in sync with upstream updates

## Building

Build all platforms:

```sh
./gradlew build
```

Build a specific platform:

```sh
./gradlew :26.1-fabric:build
./gradlew :26.1-neo:build
```

## Running

```sh
./gradlew :1.18.2-forge:runClient
./gradlew :1.19.2-forge:runClient
./gradlew :1.20.1-forge:runClient
./gradlew :1.21.1-neo:runClient
./gradlew :26.1-fabric:runClient
./gradlew :26.1-neo:runClient
```

## Requirements

- Java 25 to configure Gradle (JetBrains Runtime recommended)
- The Java version used to compile and run the game is automatically downloaded per Minecraft version via [Foojay Toolchain Resolver](https://github.com/gradle/foojay-toolchains) — no manual installation needed

## Template License

[MIT](https://github.com/Meatwo310/custom-mdk/blob/main/LICENSE) - feel free to relicense your fork and update the copyright holder.
