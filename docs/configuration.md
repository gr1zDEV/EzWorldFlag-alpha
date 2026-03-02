# Configuration

## `config.yml`

`config.yml` has two key sections:

1. `defaults` - Global fallback flag states.
2. `worlds` - Per-world overrides.

Example:

```yaml
defaults:
  pvp: DENY
  block-break: ALLOW

worlds:
  world:
    pvp: ALLOW
  world_nether:
    explosions: DENY
```

In this example:

- Most worlds inherit `pvp: DENY`.
- `world` overrides PvP to `ALLOW`.
- `world_nether` disables explosions.

## Autosave

- `autosave-interval-ticks` controls periodic save interval.
- Default is `6000` ticks.

## `messages.yml`

All player-facing/admin messages are configurable, including:

- no-permission
- invalid world/flag/value
- action feedback messages
- denied-action warnings (e.g. item dropping, PvP)

After editing config files, run:

```text
/worldflags reload
```
