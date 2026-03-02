package com.ezinnovations.ezworldflags.flag;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory world flag state store.
 */
public final class FlagManager {
    private final Map<String, Map<WorldFlag, FlagState>> worldFlags = new ConcurrentHashMap<>();
    private final Map<WorldFlag, FlagState> defaults = new ConcurrentHashMap<>();

    public FlagManager() {
        for (WorldFlag flag : WorldFlag.values()) {
            defaults.put(flag, FlagState.ALLOW);
        }
    }

    public void setDefault(final @NotNull WorldFlag flag, final @NotNull FlagState state) {
        defaults.put(flag, state == FlagState.DEFAULT ? FlagState.ALLOW : state);
    }

    public @NotNull FlagState getDefault(final @NotNull WorldFlag flag) {
        return defaults.getOrDefault(flag, FlagState.ALLOW);
    }

    public @NotNull FlagState getRawFlag(final @NotNull String worldName, final @NotNull WorldFlag flag) {
        return worldFlags.getOrDefault(worldName, Map.of()).getOrDefault(flag, FlagState.DEFAULT);
    }

    public @NotNull FlagState getFlag(final @NotNull World world, final @NotNull WorldFlag flag) {
        final FlagState raw = getRawFlag(world.getName(), flag);
        return raw == FlagState.DEFAULT ? getDefault(flag) : raw;
    }

    public boolean isDenied(final @NotNull World world, final @NotNull WorldFlag flag) {
        return getFlag(world, flag) == FlagState.DENY;
    }

    public void setFlag(final @NotNull String worldName, final @NotNull WorldFlag flag, final @NotNull FlagState state) {
        worldFlags.computeIfAbsent(worldName, ignored -> new ConcurrentHashMap<>()).put(flag, state);
    }

    public @NotNull Map<WorldFlag, FlagState> getFlags(final @NotNull String worldName) {
        final Map<WorldFlag, FlagState> copy = new EnumMap<>(WorldFlag.class);
        copy.putAll(worldFlags.getOrDefault(worldName, Map.of()));
        return copy;
    }

    public void setWorldFlags(final @NotNull String worldName, final @NotNull Map<WorldFlag, FlagState> states) {
        worldFlags.put(worldName, new ConcurrentHashMap<>(states));
    }

    public void resetWorld(final @NotNull String worldName) {
        worldFlags.remove(worldName);
    }
}
