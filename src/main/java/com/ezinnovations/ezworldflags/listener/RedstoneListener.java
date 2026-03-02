package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles redstone flag.
 */
public final class RedstoneListener implements Listener {
    private final EzWorldFlags plugin;

    public RedstoneListener(final @NotNull EzWorldFlags plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onRedstone(final BlockRedstoneEvent event) {
        if (plugin.getFlagManager().isDenied(event.getBlock().getWorld(), WorldFlag.REDSTONE)) {
            event.setNewCurrent(0);
        }
    }
}
