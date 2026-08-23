[![Latest Release](https://img.shields.io/github/v/release/Meatwo310/soft-deepslate?logo=github&label=Release)](https://github.com/Meatwo310/soft-deepslate/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue)](https://github.com/Meatwo310/soft-deepslate/blob/main/LICENSE)

[GitHub](https://github.com/Meatwo310/soft-deepslate) | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/soft-deepslate) | [Modrinth](https://modrinth.com/mod/soft-deepslate)

# Soft Deepslate

## English

A Minecraft mod that speeds up mining for Deepslate and related blocks.
By default, it applies a `2.0` mining-speed multiplier, making regular Deepslate mine at roughly the same speed as Stone.

The multiplier and affected blocks can be customized using block IDs and tags.

### Configuration

Config file: `config/softdeepslate-server.toml`

#### `miningSpeedMultiplier`

Mining-speed multiplier applied to configured blocks. Default: `2.0`.

#### `blocks`

A list of block IDs and block tags that determines which blocks receive the multiplier.

- `minecraft:deepslate`: add a block
- `#c:ores_in_ground/deepslate`: add a tag
- `!minecraft:infested_deepslate`: exclude a block
- `!#example:excluded_tag`: exclude all blocks in a tag
- `!!minecraft:infested_deepslate`: force-add a block, overriding exclusions
- `!!#example:force_added_tag`: force-add all blocks in a tag, overriding exclusions

Priority: `!!` force-add → `!` exclude → normal addition

Default:

```toml
blocks = [
    "#softdeepslate:ore_bearing_ground/deepslate",
    "#softdeepslate:cobblestones/deepslate",
    "#softdeepslate:ores_in_ground/deepslate",
    "#softdeepslate:building_blocks"
]
```

Changes are applied when the config or block tags reload.

#### Minecraft 1.7.10

The 1.7.10 version uses `config/softdeepslate.cfg` and supports only `miningSpeedMultiplier`.
It automatically detects Deepslate blocks from Et Futurum Requiem and Deepslate ores registered in the Ore Dictionary.

## 日本語

深層岩とその関連ブロックの採掘速度を向上させる Minecraft MODです。
デフォルトでは採掘速度に `2.0` 倍の補正をかけ、通常の深層岩を石とおおむね同じ速さで採掘できるようにします。

倍率と対象ブロックは、ブロック ID やタグを使って変更できます。

### 設定

設定ファイル: `config/softdeepslate-server.toml`

#### `miningSpeedMultiplier`

対象ブロックに適用する採掘速度の倍率です。デフォルト: `2.0`

#### `blocks`

採掘速度の倍率を適用するブロック ID およびブロックタグのリストです。

- `minecraft:deepslate`: ブロックを追加
- `#c:ores_in_ground/deepslate`: タグを追加
- `!minecraft:infested_deepslate`: ブロックを除外
- `!#example:excluded_tag`: タグ内のすべてのブロックを除外
- `!!minecraft:infested_deepslate`: 除外より優先してブロックを追加
- `!!#example:force_added_tag`: 除外より優先してタグ内のすべてのブロックを追加

優先順位: `!!` 強制追加 → `!` 除外 → 通常追加

デフォルト:

```toml
blocks = [
    "#softdeepslate:ore_bearing_ground/deepslate",
    "#softdeepslate:cobblestones/deepslate",
    "#softdeepslate:ores_in_ground/deepslate",
    "#softdeepslate:building_blocks"
]
```

設定またはブロックタグのリロード時に変更が反映されます。

#### Minecraft 1.7.10

1.7.10 版は `config/softdeepslate.cfg` を使用し、`miningSpeedMultiplier` のみ設定できます。
Et Futurum Requiem の深層岩ブロックと、Ore Dictionary に登録された深層岩鉱石を自動検出します。
