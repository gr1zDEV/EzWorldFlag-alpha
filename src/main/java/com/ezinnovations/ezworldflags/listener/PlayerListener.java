package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles player-centric world flags.
 */
public final class PlayerListener implements Listener {
    private final EzWorldFlags plugin;

    public PlayerListener(final @NotNull EzWorldFlags plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFood(final FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player && plugin.getFlagManager().isDenied(player.getWorld(), WorldFlag.HUNGER_DRAIN)
            && event.getFoodLevel() < player.getFoodLevel()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && event.getCause() == EntityDamageEvent.DamageCause.FALL
            && plugin.getFlagManager().isDenied(player.getWorld(), WorldFlag.FALL_DAMAGE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(final PlayerDropItemEvent event) {
        if (plugin.getFlagManager().isDenied(event.getPlayer().getWorld(), WorldFlag.ITEM_DROP)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getConfigManager().message("item-drop-denied"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickup(final EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player && plugin.getFlagManager().isDenied(player.getWorld(), WorldFlag.ITEM_PICKUP)) {
            event.setCancelled(true);
            player.sendMessage(plugin.getConfigManager().message("item-pickup-denied"));
        }
    }
}
