<div align="center">

# FixedGameMode
[![MODRINTH DOWNLOADS](https://img.shields.io/modrinth/dt/xbr87sYf?style=flat-square&logo=modrinth)](https://modrinth.com/plugin/fixedgamemode)
[![GITHUB RELEASE](https://img.shields.io/github/v/release/Runkang10/FixedGameMode?style=flat-square)](https://github.com/Runkang10/FixedGameMode)
[![GITHUB TEST WORKFLOW](https://img.shields.io/github/actions/workflow/status/Runkang10/FixedGameMode/test.yml?branch=main&style=flat-square)](https://github.com/Runkang10/FixedGameMode)
[![LICENSE](https://img.shields.io/badge/license-MIT-orange?style=flat-square)](https://github.com/Runkang10/FixedGameMode?tab=MIT-1-ov-file)

Have you ever gotten annoyed by `[Debug]: Unable to open game mode switcher; no permission`?\
If so, this plugin is the solution for you.

</div>

## How it works
- If a player doesn't have OP level 2 or greater (full OP is level 4), this plugin will trick the player into thinking
  they have OP level 2. This fixes their client being unable to use the game mode switcher without actually giving them
  OP.
- When a player tries to switch their game mode, the server will check if they have permission to change into that game
  mode. If they don't have permission, the server will send the default no permission message to the player.
- The plugin additionally provides commands like `/gmc` to switch game mode.

In other words: the game mode switcher works normally if you have permission; otherwise, you'll see the default error
message.

## Example Usage
- **Want to give some players permission for creative?**\
  Give them `fixedgamemode.creative` permission, and now they can toggle to creative mode with both the game mode
  switcher and the `/creative` command.
- **Want to give them permission to change other players' game mode?**\
  Give them `fixedgamemode.creative.others` permission, and now they can change other players' game mode to creative
  through the command.

## Requirements
- [Paper](https://papermc.io/downloads/paper) or any <u>Paper fork</u>
  - [Folia](https://papermc.io/downloads/folia) or any <u>Folia fork</u> is also supported.
- Java 25
- Any permission plugin like [LuckPerms](https://luckperms.net) (optional)
- Have [PacketEvents](https://modrinth.com/plugin/packetevents) installed
  - You can also not install this plugin, but you will not be able to change the game mode through game mode switcher
    unless you have real OP.

## Commands
See the **Permissions** section to find the required permission for each command.

| Command                 | Aliases | Description                                  |
|-------------------------|---------|----------------------------------------------|
| `/adventure`            | `/gma`  | Switches game mode to adventure              |
| `/adventure <target>`   |         | Switches the target's game mode to adventure |
| `/creative`             | `/gmc`  | Switches game mode to creative               |
| `/creative <target>`    |         | Switches the target's game mode to creative  |
| `/spectator`            | `/gmsp` | Switches game mode to spectator              |
| `/spectator <target>`   |         | Switches the target's game mode to spectator |
| `/survival`             | `/gms`  | Switches game mode to survival               |
| `/survival <target>`    |         | Switches the target's game mode to survival  |
| `/fixedgamemode`        | `/fgm`  | Gives access to the `/fixedgamemode` command |
| `/fixedgamemode reload` |         | Reloads the plugin configurations            |

## Permissions
All permissions below are granted to OP players by default.

| Permission                       | Description                                                        |
|----------------------------------|--------------------------------------------------------------------|
| `fixedgamemode.adventure`        | Allows switching game mode (via command or the game mode switcher) |
| `fixedgamemode.adventure.others` | Allows switching the target's game mode                            |
| `fixedgamemode.creative`         | Allows switching game mode (via command or the game mode switcher) |
| `fixedgamemode.creative.others`  | Allows switching the target's game mode                            |
| `fixedgamemode.spectator`        | Allows switching game mode (via command or the game mode switcher) |
| `fixedgamemode.spectator.others` | Allows switching the target's game mode                            |
| `fixedgamemode.survival`         | Allows switching game mode (via command or the game mode switcher) |
| `fixedgamemode.survival.others`  | Allows switching the target's game mode                            |
| `fixedgamemode.command`          | Grants access to the `/fixedgamemode` command                      |
| `fixedgamemode.command.reload`   | Allows reloading the plugin configurations                         |