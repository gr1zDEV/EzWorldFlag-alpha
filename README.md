# EzWorldFlags

> Lightweight per-world flag management for Paper/Folia servers.

## Features
- Per-world flags with `ALLOW`, `DENY`, and `DEFAULT` inheritance.
- `/worldflags` chest GUI world selector and flag editor.
- Full command suite with tab completion.
- Paper 1.21.11 and Folia-aware scheduler utility.
- Config-driven defaults and localized messages.

## Installation
1. Download the latest jar from Releases.
2. Place the jar in your server `plugins/` folder.
3. Start or restart the server.
4. Use `/worldflags` as an operator.

## Commands
| Command | Permission | Description |
|---|---|---|
| `/worldflags` | `ezworldflags.gui` | Open world selector GUI |
| `/worldflags <world>` | `ezworldflags.gui` | Open world flag GUI |
| `/worldflags set <world> <flag> <ALLOW\|DENY\|DEFAULT>` | `ezworldflags.set` | Set flag |
| `/worldflags get <world> <flag>` | `ezworldflags.get` | Get flag |
| `/worldflags list <world>` | `ezworldflags.list` | List all flags |
| `/worldflags reload` | `ezworldflags.reload` | Reload configs |
| `/worldflags reset <world>` | `ezworldflags.reset` | Reset world flags |

## Permissions
Use `ezworldflags.*` for all permissions. Individual nodes are defined in `plugin.yml`.

## Flag Reference
See `WorldFlag` enum for all supported flags and icon metadata.

## Configuration
- `config.yml`: defaults and per-world overrides.
- `messages.yml`: all player/admin-facing messages.

## Build from source
```bash
mvn clean package
```

## Releases
https://github.com/EzInnovations/EzWorldFlags/releases

## License
MIT
