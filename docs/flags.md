---
title: Flag Reference
nav_order: 6
---

# Flag Reference

Each flag can be set to `ALLOW`, `DENY`, or `DEFAULT` per world.

| Flag ID | What it controls |
|---|---|
| `mob-spawning` | Natural mob spawning overall |
| `monster-spawning` | Natural hostile mob spawning |
| `animal-spawning` | Natural passive mob spawning |
| `pvp` | Player-vs-player damage |
| `block-break` | Breaking blocks |
| `block-place` | Placing blocks |
| `explosions` | Explosion events in general |
| `fire-spread` | Fire spread and ignition |
| `leaf-decay` | Natural leaf decay |
| `hunger-drain` | Hunger depletion |
| `fall-damage` | Fall damage to players |
| `weather` | Weather/thunder change cycle |
| `ice-form` | Ice/frosted ice formation |
| `snow-form` | Snow layer/block formation |
| `redstone` | Redstone current updates |
| `item-drop` | Player item dropping |
| `item-pickup` | Player item pickup |
| `crop-growth` | Crop growth ticks |
| `enderman-grief` | Enderman block pickup/changes |
| `creeper-block-damage` | Creeper explosions breaking blocks |
| `wither-block-damage` | Wither explosions/changes breaking blocks |

## Interaction notes

- `explosions = DENY` cancels explosion events entirely.
- `creeper-block-damage` and `wither-block-damage` are granular controls for block damage from those entities.
- `mob-spawning = DENY` blocks natural spawning globally, including monsters/animals.

---

[⬅ Previous: Permissions](permissions.md){: .btn }
[Next: Configuration ➡](configuration.md){: .btn }
