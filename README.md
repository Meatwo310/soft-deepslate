# Soft Deepslate

[![Latest Release](https://img.shields.io/github/v/release/Meatwo310/soft-deepslate)](https://github.com/Meatwo310/soft-deepslate/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue)](https://github.com/Meatwo310/soft-deepslate/blob/26.1-neo/LICENSE)

A Minecraft mod that adjusts the mining speed of Deepslate to match that of Stone.
Target blocks and speed can be customized via config.

深層岩を石と同等の採掘速度に調整する Minecraft MOD。
対象となるブロックや速さはコンフィグで変更可能。


## Configuration / コンフィグ

Config file: `config/softdeepslate-server.toml`

| Key | Default | Description |
|---|---|---|
| `miningSpeed` | `2.0` | Speed multiplier. `2.0` = same rate as Stone. |
| `blocks` | `["#softdeepslate:deepslates"]` | Block IDs or tags (prefix with `#`) to adjust. Run `/reload` to apply. |


