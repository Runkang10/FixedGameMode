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
- [Paper](https://papermc.io/downloads/paper) or any Paper fork
- Java 25
- Install [PacketEvents](https://modrinth.com/plugin/packetevents)

## Commands
| Command                 | Aliases | Description                   | Permission                     |
|-------------------------|---------|-------------------------------|--------------------------------|
| `/adventure [<target>]` | `/gma`  | Switch game mode to adventure | `fixedgamemode.adventure`      |
| `/creative [<target>]`  | `/gmc`  | Switch game mode to creative  | `fixedgamemode.creative`       |
| `/spectator [<target>]` | `/gmsp` | Switch game mode to spectator | `fixedgamemode.spectator`      |
| `/survival [<target>]`  | `/gms`  | Switch game mode to survival  | `fixedgamemode.survival`       |
| `/fixedgamemode`        | `/fgm`  |                               | `fixedgamemode.command`        |
| `/fixedgamemode reload` |         | Reload plugin configurations  | `fixedgamemode.command.reload` |

## Permissions
| Permission                     | Description                                            | Default |
|--------------------------------|--------------------------------------------------------|---------|
| `fixedgamemode.adventure`      | Switch game mode (both command and game mode switcher) | OP      |
| `fixedgamemode.creative`       | Switch game mode (both command and game mode switcher) | OP      |
| `fixedgamemode.spectator`      | Switch game mode (both command and game mode switcher) | OP      |
| `fixedgamemode.survival`       | Switch game mode (both command and game mode switcher) | OP      |
| `fixedgamemode.command`        | Access to `/fixedgamemode` command                     | OP      |
| `fixedgamemode.command.reload` | Reload the plugin configurations                       | OP      |

