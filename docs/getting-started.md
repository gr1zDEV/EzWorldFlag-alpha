---
title: Getting Started
nav_order: 2
---

# Getting Started

## Requirements

- **Paper 1.21.11+** (plugin targets API `1.21`).
- Folia is supported.

## Installation

1. Download the latest release jar from GitHub Releases.
2. Place it in your server's `plugins/` directory.
3. Start (or restart) your server.
4. Confirm plugin load in console.

## First-Time Setup

After first boot, EzWorldFlags creates:

- `plugins/EzWorldFlags/config.yml` (defaults + per-world values)
- `plugins/EzWorldFlags/messages.yml` (all player/admin messages)

### Recommended first checks

- Ensure your admin role has `ezworldflags.gui` (or `ezworldflags.*`).
- Open `/worldflags` and review each world's defaults.
- Decide whether you want to enforce strict behavior in specific worlds (e.g. no PvP in hub worlds).

## How inheritance works

Every flag in each world has a **raw state**:

- `ALLOW`
- `DENY`
- `DEFAULT`

When a world flag is `DEFAULT`, EzWorldFlags resolves it from `config.yml > defaults`.
This lets you set global behavior once, then only override worlds that differ.

---

[⬅ Previous: Home](index.md){: .btn }
[Next: Using the GUI ➡](gui.md){: .btn }
