---
title: Troubleshooting & FAQ
nav_order: 8
---

# Troubleshooting & FAQ

## "I can't open the GUI"

Check:

- You are a player (console cannot open GUI).
- You have `ezworldflags.gui`.
- The command is correct: `/worldflags`.

## "A flag change doesn't seem to apply"

Check:

1. You edited the intended world.
2. The flag is not still `DEFAULT` when you expected override behavior.
3. Another plugin is not re-enabling behavior at a different priority.

## "What does DEFAULT do exactly?"

`DEFAULT` tells the world-level flag to inherit from `config.yml > defaults`.
It is not a fourth behavior, only an inheritance mode.

## "Can I manage from console?"

Yes, all non-GUI subcommands work from console (`set`, `get`, `list`, `reload`, `reset`).

## "How do I lock down a hub world?"

A common setup:

```text
/worldflags set world pvp DENY
/worldflags set world block-break DENY
/worldflags set world block-place DENY
/worldflags set world item-drop DENY
/worldflags set world item-pickup DENY
```
