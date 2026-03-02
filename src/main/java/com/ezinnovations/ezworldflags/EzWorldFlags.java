package com.ezinnovations.ezworldflags;

import com.ezinnovations.ezworldflags.command.WorldFlagsCommand;
import com.ezinnovations.ezworldflags.config.ConfigManager;
import com.ezinnovations.ezworldflags.flag.FlagManager;
import com.ezinnovations.ezworldflags.listener.FlagListenerManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin bootstrap class.
 */
public final class EzWorldFlags extends JavaPlugin {
    private FlagManager flagManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.flagManager = new FlagManager();
        this.configManager = new ConfigManager(this);
        configManager.load(flagManager);
        configManager.scheduleAutoSave();

        final WorldFlagsCommand command = new WorldFlagsCommand(this);
        final PluginCommand pluginCommand = getCommand("worldflags");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        }

        new FlagListenerManager(this).register();
        getLogger().info("[EzWorldFlags] Enabled successfully.");
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("[EzWorldFlags] Disabled.");
    }

    public @NotNull FlagManager getFlagManager() {
        return flagManager;
    }

    public @NotNull ConfigManager getConfigManager() {
        return configManager;
    }
}
