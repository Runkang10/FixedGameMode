# FixedGameMode
[![GITHUB](https://img.shields.io/badge/github-repo-blue?style=flat-square&logo=github)](https://github.com/Runkang10/FixedGameMode)
[![PUBLISH WORKFLOW](https://img.shields.io/github/actions/workflow/status/Runkang10/FixedGameMode/publish.yml?style=flat-square&label=publish)](https://github.com/Runkang10/FixedGameMode/actions/workflows/publish.yml)
[![TEST WORKFLOW](https://img.shields.io/github/actions/workflow/status/Runkang10/FixedGameMode/test.yml?style=flat-square&label=test)](https://github.com/Runkang10/FixedGameMode/actions/workflows/test.yml)
[![LICENSE](https://img.shields.io/badge/license-MIT-orange?style=flat-square)](https://github.com/Runkang10/FixedGameMode?tab=MIT-1-ov-file)

Have you ever gotten annoyed by `[Debug]: Unable to open game mode switcher; no permission`? If so, this plugin is the
solution for you.

## How it works
- If a player doesn't have OP level 2 or greater (full OP is level 4), this plugin will trick the player into thinking
  they have OP level 2. This fixes their client being unable to use the game mode switcher without actually giving them
  OP.
- When a player tries to switch their game mode, the server will check if they have permission to change into that game
  mode. If they don't have permission, the server will send the default no permission message to the player.
- The plugin additionally provides commands like `/gmc` to switch game mode.

In other words: allow game mode switcher usage if you have permission, otherwise default error message.

## Requirements
- [Paper](https://papermc.io/downloads/paper) or any <u>Paper fork</u>
- Java 25
- Install [PacketEvents](https://modrinth.com/plugin/packetevents)

## Commands
| Command                 | Aliases | Description                                  | Permission                       |
|-------------------------|---------|----------------------------------------------|----------------------------------|
| `/adventure`            | `/gma`  | Switches game mode to adventure              | `fixedgamemode.adventure`        |
| `/adventure [<target>]` |         | Switches the target's game mode to adventure | `fixedgamemode.adventure.others` |
| `/creative`             | `/gmc`  | Switches game mode to creative               | `fixedgamemode.creative`         |
| `/creative [<target>]`  |         | Switches the target's game mode creative     | `fixedgamemode.creative.others`  |
| `/spectator`            | `/gmsp` | Switches game mode to spectator              | `fixedgamemode.spectator`        |
| `/spectator [<target>]` |         | Switches the target's game mode spectator    | `fixedgamemode.spectator.others` |
| `/survival`             | `/gms`  | Switches game mode to survival               | `fixedgamemode.survival`         |
| `/survival [<target>]`  |         | Switches the target's game mode survival     | `fixedgamemode.survival.others`  |
| `/fixedgamemode`        | `/fgm`  | Gives access to the `/fixedgamemode` command | `fixedgamemode.command`          |
| `/fixedgamemode reload` |         | Reloads the plugin configurations            | `fixedgamemode.command.reload`   |

## Permissions
| Permission                       | Description                                                        | Default |
|----------------------------------|--------------------------------------------------------------------|---------|
| `fixedgamemode.adventure`        | Allows switching game mode (via command or the game mode switcher) | OP      |
| `fixedgamemode.adventure.others` | Allows switching the target's game mode                            | OP      |
| `fixedgamemode.creative`         | Allows switching game mode (via command or the game mode switcher) | OP      |
| `fixedgamemode.creative.others`  | Allows switching the target's game mode                            | OP      |
| `fixedgamemode.spectator`        | Allows switching game mode (via command or the game mode switcher) | OP      |
| `fixedgamemode.spectator.others` | Allows switching the target's game mode                            | OP      |
| `fixedgamemode.survival`         | Allows switching game mode (via command or the game mode switcher) | OP      |
| `fixedgamemode.survival.others`  | Allows switching the target's game mode                            | OP      |
| `fixedgamemode.command`          | Grants access to the `/fixedgamemode` command                      | OP      |
| `fixedgamemode.command.reload`   | Allows reloading the plugin configurations                         | OP      |

