package com.ezinnovations.ezworldflags.config;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.FlagManager;
import com.ezinnovations.ezworldflags.flag.FlagState;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import com.ezinnovations.ezworldflags.scheduler.SchedulerUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Handles config and messages loading/saving.
 */
public final class ConfigManager {
    private final EzWorldFlags plugin;
    private File messagesFile;
    private FileConfiguration messages;

    public ConfigManager(final @NotNull EzWorldFlags plugin) {
        this.plugin = plugin;
    }

    public void load(final @NotNull FlagManager flagManager) {
        plugin.saveDefaultConfig();
        loadMessages();
        plugin.reloadConfig();

        for (WorldFlag flag : WorldFlag.values()) {
            final String path = "defaults." + flag.id();
            final FlagState state = FlagState.fromString(plugin.getConfig().getString(path, "ALLOW"));
            flagManager.setDefault(flag, state == null ? FlagState.ALLOW : state);
            if (!plugin.getConfig().contains(path)) {
                plugin.getConfig().set(path, FlagState.ALLOW.name());
            }
        }

        final ConfigurationSection worlds = plugin.getConfig().getConfigurationSection("worlds");
        if (worlds != null) {
            for (String worldName : worlds.getKeys(false)) {
                final ConfigurationSection worldSec = worlds.getConfigurationSection(worldName);
                if (worldSec == null) {
                    continue;
                }
                final Map<WorldFlag, FlagState> states = new EnumMap<>(WorldFlag.class);
                for (WorldFlag flag : WorldFlag.values()) {
                    final FlagState state = FlagState.fromString(worldSec.getString(flag.id(), "DEFAULT"));
                    states.put(flag, state == null ? FlagState.DEFAULT : state);
                }
                flagManager.setWorldFlags(worldName, states);
            }
        }
        plugin.saveConfig();
    }

    public void scheduleAutoSave() {
        final long interval = plugin.getConfig().getLong("autosave-interval-ticks", 6000L);
        BukkitRepeater.start(plugin, interval, () -> SchedulerUtil.runTaskAsync(plugin, plugin::saveConfig));
    }

    public void saveNow() {
        plugin.saveConfig();
    }

    public @NotNull String message(final @NotNull String key) {
        final String prefix = messages.getString("prefix", "§8[§bEzWorldFlags§8] §7");
        return prefix + messages.getString(key, key);
    }

    private void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reload(final @NotNull FlagManager flagManager) {
        try {
            load(flagManager);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "[EzWorldFlags] Failed to reload config", ex);
        }
    }

    private static final class BukkitRepeater {
        private BukkitRepeater() {
        }

        private static void start(final EzWorldFlags plugin, final long interval, final Runnable task) {
            if (SchedulerUtil.isFoliaServer()) {
                org.bukkit.Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, ignored -> task.run(), interval, interval);
                return;
            }
            org.bukkit.Bukkit.getScheduler().runTaskTimer(plugin, task, interval, interval);
        }
    }
}
