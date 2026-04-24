# custom-mdk

A Minecraft mod template supporting multiple mod loaders, managed in a single Gradle multi-project build.

## Supported Platforms

| Subproject     | Minecraft | Loader       |
|----------------|-----------|--------------|
| `26.1-fabric`  | 26.1      | Fabric       |
| `26.1-neo`     | 26.1      | NeoForge     |
| `1.21.1-neo`   | 1.21.1    | NeoForge     |
| `1.20.1-forge` | 1.20.1    | Legacy Forge |

## Setup

1. Fork this repository (to update your repo with upstream changes later)
2. Select the platforms you need in `settings.gradle.kts` by removing unused `include(...)` lines
3. Edit `gradle.properties` to set your mod's metadata and fabric entry points

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
./gradlew :26.1-fabric:runClient
./gradlew :26.1-neo:runClient
./gradlew :1.21.1-neo:runClient
./gradlew :1.20.1-forge:runClient
```

## Requirements

- Java 25 to configure Gradle (JetBrains Runtime recommended)
- The Java version used to compile and run the game is automatically downloaded per Minecraft version via [Foojay Toolchain Resolver](https://github.com/gradle/foojay-toolchains) — no manual installation needed

## License

[MIT](LICENSE)
