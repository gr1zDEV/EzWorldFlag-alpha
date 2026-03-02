# Using the GUI

EzWorldFlags includes a chest-based GUI for easy world and flag management.

## Open the world selector

Run:

- `/worldflags`

This opens a list of worlds.

## Open a specific world directly

Run:

- `/worldflags <world>`

## Flag editor layout

Inside a world editor:

- Each flag appears as an item with icon, name, and current status.
- Bottom controls:
  - **Back to Worlds**
  - **Reset All** (sets all world flags back to `DEFAULT`)
  - **Save & Close**

## Click behavior

- **Normal click:** toggles between `ALLOW` and `DENY`.
- **Shift+Click:** sets the flag to `DEFAULT`.

## Visual meaning

- Green name: `ALLOW`
- Red name: `DENY`
- Yellow name: `DEFAULT` (inherits resolved server default)

When a flag is `DEFAULT`, lore shows the inherited result (`ALLOW` or `DENY`).
