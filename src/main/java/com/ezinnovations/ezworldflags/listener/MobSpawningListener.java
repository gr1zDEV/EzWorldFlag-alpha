package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Handles mob spawning flags.
 */
public final class MobSpawningListener implements Listener {
    private static final Set<CreatureSpawnEvent.SpawnReason> NATURAL = Set.of(
        CreatureSpawnEvent.SpawnReason.NATURAL,
        CreatureSpawnEvent.SpawnReason.VILLAGE_DEFENSE,
        CreatureSpawnEvent.SpawnReason.PATROL,
        CreatureSpawnEvent.SpawnReason.REINFORCEMENTS,
        CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION
    );

    private final EzWorldFlags plugin;

    public MobSpawningListener(final @NotNull EzWorldFlags plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        if (!NATURAL.contains(event.getSpawnReason())) {
            return;
        }
        if (plugin.getFlagManager().isDenied(event.getLocation().getWorld(), WorldFlag.MOB_SPAWNING)) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Monster && plugin.getFlagManager().isDenied(event.getLocation().getWorld(), WorldFlag.MONSTER_SPAWNING)) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Animals && plugin.getFlagManager().isDenied(event.getLocation().getWorld(), WorldFlag.ANIMAL_SPAWNING)) {
            event.setCancelled(true);
        }
    }
}
