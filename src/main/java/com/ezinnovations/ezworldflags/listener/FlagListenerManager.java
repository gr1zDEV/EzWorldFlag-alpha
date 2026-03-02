package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.gui.GUIListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * Registers listeners.
 */
public final class FlagListenerManager {
    private final EzWorldFlags plugin;

    public FlagListenerManager(final @NotNull EzWorldFlags plugin) {
        this.plugin = plugin;
    }

    public void register() {
        register(new GUIListener(plugin));
        register(new MobSpawningListener(plugin));
        register(new CombatListener(plugin));
        register(new BlockListener(plugin));
        register(new EnvironmentListener(plugin));
        register(new PlayerListener(plugin));
        register(new RedstoneListener(plugin));
    }

    private void register(final Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
