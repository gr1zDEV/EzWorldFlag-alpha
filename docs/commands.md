---
title: Commands
nav_order: 4
---

# Commands

Main command:

- `/worldflags`
- Alias: `/wf`

## Command reference

| Command | Permission | Description |
|---|---|---|
| `/worldflags` | `ezworldflags.gui` | Open world selector GUI |
| `/worldflags <world>` | `ezworldflags.gui` | Open GUI for one world |
| `/worldflags set <world> <flag> <ALLOW\|DENY\|DEFAULT>` | `ezworldflags.set` | Set a world flag |
| `/worldflags get <world> <flag>` | `ezworldflags.get` | Check one world flag |
| `/worldflags list <world>` | `ezworldflags.list` | List all flags for a world |
| `/worldflags reload` | `ezworldflags.reload` | Reload `config.yml` + `messages.yml` |
| `/worldflags reset <world>` | `ezworldflags.reset` | Reset all flags in a world |

## Examples

```text
/worldflags set world pvp DENY
/worldflags get world pvp
/worldflags list world_nether
/worldflags reset world_the_end
/worldflags reload
```

## Tab completion

EzWorldFlags provides tab completion for:

- subcommands (`set`, `get`, `list`, `reload`, `reset`)
- loaded world names
- flag IDs
- set values (`ALLOW`, `DENY`, `DEFAULT`)

---

[⬅ Previous: Using the GUI](gui.md){: .btn }
[Next: Permissions ➡](permissions.md){: .btn }
