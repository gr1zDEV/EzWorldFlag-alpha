package com.ezinnovations.ezworldflags.gui;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.FlagState;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Handles plugin GUI click interactions.
 */
public final class GUIListener implements Listener {
    private final EzWorldFlags plugin;
    private final WorldSelectorGUI selectorGUI;
    private final FlagGUI flagGUI;

    public GUIListener(final @NotNull EzWorldFlags plugin) {
        this.plugin = plugin;
        this.selectorGUI = new WorldSelectorGUI();
        this.flagGUI = new FlagGUI(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(final InventoryClickEvent event) {
        final InventoryView view = event.getView();
        final String title = PlainTextComponentSerializer.plainText().serialize(view.title());
        if (!title.startsWith("EzWorldFlags")) {
            return;
        }
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player) || event.getCurrentItem() == null) {
            return;
        }

        if (title.equals(WorldSelectorGUI.TITLE)) {
            final String worldName = extractItemName(event.getCurrentItem()).replace("§a", "");
            final World world = Bukkit.getWorld(worldName);
            if (world != null) {
                flagGUI.open(player, world);
            }
            return;
        }

        final String worldName = title.replace("EzWorldFlags — ", "");
        final World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.closeInventory();
            return;
        }

        if (event.getRawSlot() == 45) {
            selectorGUI.open(player);
            return;
        }
        if (event.getRawSlot() == 49) {
            plugin.getFlagManager().resetWorld(world.getName());
            plugin.getConfig().set("worlds." + world.getName(), null);
            plugin.saveConfig();
            flagGUI.open(player, world);
            return;
        }
        if (event.getRawSlot() == 53) {
            player.closeInventory();
            return;
        }
        if (event.getRawSlot() < 0 || event.getRawSlot() >= WorldFlag.values().length) {
            return;
        }

        final WorldFlag flag = WorldFlag.values()[event.getRawSlot()];
        final FlagState current = plugin.getFlagManager().getRawFlag(world.getName(), flag);
        final FlagState next = event.isShiftClick() ? FlagState.DEFAULT : (current == FlagState.DENY ? FlagState.ALLOW : FlagState.DENY);
        plugin.getFlagManager().setFlag(world.getName(), flag, next);
        plugin.getConfig().set("worlds." + world.getName() + "." + flag.id(), next.name());
        plugin.saveConfig();
        view.setItem(event.getRawSlot(), flagGUI.buildFlagItem(world, flag));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
    }

    private String extractItemName(final ItemStack stack) {
        if (stack.getItemMeta() != null && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().displayName() != null) {
            return PlainTextComponentSerializer.plainText().serialize(stack.getItemMeta().displayName());
        }
        return "";
    }
}
