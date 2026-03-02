package com.ezinnovations.ezworldflags.listener;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles PvP flag.
 */
public final class CombatListener implements Listener {
    private final EzWorldFlags plugin;
    private final Map<UUID, Long> cooldown = new ConcurrentHashMap<>();

    public CombatListener(final @NotNull EzWorldFlags plugin) { this.plugin = plugin; }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(final EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }
        final Player attacker = resolvePlayer(event.getDamager());
        if (attacker == null) {
            return;
        }
        if (plugin.getFlagManager().isDenied(victim.getWorld(), WorldFlag.PVP)) {
            event.setCancelled(true);
            final long now = System.currentTimeMillis();
            final long last = cooldown.getOrDefault(attacker.getUniqueId(), 0L);
            if (now - last >= 3000) {
                attacker.sendMessage(plugin.getConfigManager().message("pvp-denied"));
                cooldown.put(attacker.getUniqueId(), now);
            }
        }
    }

    private Player resolvePlayer(final Entity entity) {
        if (entity instanceof Player player) {
            return player;
        }
        if (entity instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            return player;
        }
        return null;
    }
}
