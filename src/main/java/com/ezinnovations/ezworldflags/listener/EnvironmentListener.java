package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles environment flags.
 */
public final class EnvironmentListener implements Listener {
    private final EzWorldFlags plugin;

    public EnvironmentListener(final @NotNull EzWorldFlags plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpread(final BlockSpreadEvent event) {
        if (event.getSource().getType() == Material.FIRE && plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.FIRE_SPREAD)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onIgnite(final BlockIgniteEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.FIRE_SPREAD)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDecay(final LeavesDecayEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.LEAF_DECAY)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWeather(final WeatherChangeEvent event) {
        if (plugin.getFlagManager().isDenied(event.getWorld(), WorldFlag.WEATHER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onThunder(final ThunderChangeEvent event) {
        if (plugin.getFlagManager().isDenied(event.getWorld(), WorldFlag.WEATHER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onForm(final BlockFormEvent event) {
        final Material type = event.getNewState().getType();
        if ((type == Material.ICE || type == Material.FROSTED_ICE) && plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.ICE_FORM)) {
            event.setCancelled(true);
        }
        if ((type == Material.SNOW || type == Material.SNOW_BLOCK) && plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.SNOW_FORM)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGrow(final BlockGrowEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.CROP_GROWTH)) {
            event.setCancelled(true);
        }
    }
}
