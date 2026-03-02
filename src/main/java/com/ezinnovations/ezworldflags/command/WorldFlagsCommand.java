package com.ezinnovations.ezworldflags.command;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.FlagState;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import com.ezinnovations.ezworldflags.gui.FlagGUI;
import com.ezinnovations.ezworldflags.gui.WorldSelectorGUI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Handles /worldflags command.
 */
public final class WorldFlagsCommand implements CommandExecutor, TabCompleter {
    private final EzWorldFlags plugin;
    private final WorldSelectorGUI selectorGUI = new WorldSelectorGUI();
    private final FlagGUI flagGUI;

    public WorldFlagsCommand(final @NotNull EzWorldFlags plugin) {
        this.plugin = plugin;
        this.flagGUI = new FlagGUI(plugin);
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Players only.");
                return true;
            }
            if (!sender.hasPermission("ezworldflags.gui")) return deny(sender);
            selectorGUI.open(player);
            return true;
        }

        if (args.length == 1 && !isSubCommand(args[0])) {
            if (!(sender instanceof Player player)) {
                return true;
            }
            if (!sender.hasPermission("ezworldflags.gui")) return deny(sender);
            final World world = Bukkit.getWorld(args[0]);
            if (world == null) {
                sender.sendMessage(plugin.getConfigManager().message("invalid-world").replace("{world}", args[0]));
                return true;
            }
            flagGUI.open(player, world);
            return true;
        }

        return switch (args[0].toLowerCase(Locale.ROOT)) {
            case "set" -> handleSet(sender, args);
            case "get" -> handleGet(sender, args);
            case "list" -> handleList(sender, args);
            case "reload" -> handleReload(sender);
            case "reset" -> handleReset(sender, args);
            default -> false;
        };
    }

    private boolean handleSet(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ezworldflags.set")) return deny(sender);
        if (args.length < 4) return false;
        World world = Bukkit.getWorld(args[1]);
        WorldFlag flag = WorldFlag.fromId(args[2]);
        FlagState state = FlagState.fromString(args[3]);
        if (world == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-world").replace("{world}", args[1])); return true; }
        if (flag == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-flag").replace("{flag}", args[2])); return true; }
        if (state == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-state")); return true; }
        plugin.getFlagManager().setFlag(world.getName(), flag, state);
        plugin.getConfig().set("worlds." + world.getName() + "." + flag.id(), state.name());
        plugin.saveConfig();
        sender.sendMessage(plugin.getConfigManager().message("flag-set").replace("{flag}", flag.id()).replace("{state}", state.name()).replace("{world}", world.getName()));
        return true;
    }

    private boolean handleGet(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ezworldflags.get")) return deny(sender);
        if (args.length < 3) return false;
        World world = Bukkit.getWorld(args[1]);
        WorldFlag flag = WorldFlag.fromId(args[2]);
        if (world == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-world").replace("{world}", args[1])); return true; }
        if (flag == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-flag").replace("{flag}", args[2])); return true; }
        sender.sendMessage(plugin.getConfigManager().message("flag-get").replace("{flag}", flag.id()).replace("{state}", plugin.getFlagManager().getRawFlag(world.getName(), flag).name()).replace("{world}", world.getName()));
        return true;
    }

    private boolean handleList(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ezworldflags.list")) return deny(sender);
        if (args.length < 2) return false;
        World world = Bukkit.getWorld(args[1]);
        if (world == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-world").replace("{world}", args[1])); return true; }
        sender.sendMessage("§eFlags for world §f" + world.getName());
        for (WorldFlag flag : WorldFlag.values()) {
            sender.sendMessage("§7- §f" + flag.id() + "§7: §e" + plugin.getFlagManager().getRawFlag(world.getName(), flag));
        }
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("ezworldflags.reload")) return deny(sender);
        plugin.getConfigManager().reload(plugin.getFlagManager());
        sender.sendMessage(plugin.getConfigManager().message("config-reloaded"));
        return true;
    }

    private boolean handleReset(CommandSender sender, String[] args) {
        if (!sender.hasPermission("ezworldflags.reset")) return deny(sender);
        if (args.length < 2) return false;
        World world = Bukkit.getWorld(args[1]);
        if (world == null) { sender.sendMessage(plugin.getConfigManager().message("invalid-world").replace("{world}", args[1])); return true; }
        plugin.getFlagManager().resetWorld(world.getName());
        plugin.getConfig().set("worlds." + world.getName(), null);
        plugin.saveConfig();
        sender.sendMessage(plugin.getConfigManager().message("world-reset").replace("{world}", world.getName()));
        return true;
    }

    private boolean deny(CommandSender sender) {
        sender.sendMessage(plugin.getConfigManager().message("no-permission"));
        return true;
    }

    private boolean isSubCommand(String s) {
        return Set.of("set", "get", "list", "reload", "reset").contains(s.toLowerCase(Locale.ROOT));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return filter(List.of("set", "get", "list", "reload", "reset"), args[0]);
        }
        if (args.length == 2) {
            return filter(Bukkit.getWorlds().stream().map(World::getName).toList(), args[1]);
        }
        if (args.length == 3 && (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("get"))) {
            return filter(Arrays.stream(WorldFlag.values()).map(WorldFlag::id).toList(), args[2]);
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("set")) {
            return filter(List.of("ALLOW", "DENY", "DEFAULT"), args[3]);
        }
        return List.of();
    }

    private List<String> filter(List<String> source, String token) {
        return source.stream().filter(s -> s.toLowerCase(Locale.ROOT).startsWith(token.toLowerCase(Locale.ROOT))).toList();
    }
}
