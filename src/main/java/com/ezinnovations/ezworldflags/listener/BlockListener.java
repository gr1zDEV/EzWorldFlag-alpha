package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles block interaction and explosion flags.
 */
public final class BlockListener implements Listener {
    private final EzWorldFlags plugin;

    public BlockListener(final @NotNull EzWorldFlags plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.BLOCK_BREAK)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getConfigManager().message("block-break-denied"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlace(final BlockPlaceEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.BLOCK_PLACE)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getConfigManager().message("block-place-denied"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onExplode(final EntityExplodeEvent event) {
        if (plugin.getFlagManager().isDenied(event.getLocation().getWorld(), WorldFlag.EXPLOSIONS)) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Creeper && plugin.getFlagManager().isDenied(event.getLocation().getWorld(), WorldFlag.CREEPER_BLOCK_DAMAGE)) {
            event.blockList().clear();
        }
        if (event.getEntity() instanceof Wither && plugin.getFlagManager().isDenied(event.getLocation().getWorld(), WorldFlag.WITHER_BLOCK_DAMAGE)) {
            event.blockList().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockExplode(final BlockExplodeEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.EXPLOSIONS)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChange(final EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof Enderman && plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.ENDERMAN_GRIEF)) {
            event.setCancelled(true);
        }
        if (event.getEntity() instanceof Wither && plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.WITHER_BLOCK_DAMAGE)) {
            event.setCancelled(true);
        }
    }
}
