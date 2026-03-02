package com.ezinnovations.ezworldflags.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Scheduler abstraction for Folia and regular Paper.
 */
public final class SchedulerUtil {
    private static final boolean FOLIA = isFolia();

    private SchedulerUtil() {
    }

    public static boolean isFoliaServer() {
        return FOLIA;
    }

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static void runTask(final @NotNull Plugin plugin, final @NotNull Runnable task) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().run(plugin, ignored -> task.run());
            return;
        }
        Bukkit.getScheduler().runTask(plugin, task);
    }

    public static void runTaskLater(final @NotNull Plugin plugin, final @NotNull Runnable task, final long delayTicks) {
        if (FOLIA) {
            Bukkit.getGlobalRegionScheduler().runDelayed(plugin, ignored -> task.run(), delayTicks);
            return;
        }
        Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    public static void runTaskAsync(final @NotNull Plugin plugin, final @NotNull Runnable task) {
        if (FOLIA) {
            Bukkit.getAsyncScheduler().runNow(plugin, ignored -> task.run());
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    public static void runEntityTask(final @NotNull Plugin plugin, final @NotNull Entity entity, final @NotNull Runnable task) {
        if (FOLIA) {
            entity.getScheduler().run(plugin, ignored -> task.run(), null);
            return;
        }
        Bukkit.getScheduler().runTask(plugin, task);
    }

    public static void runRegionTask(final @NotNull Plugin plugin, final @NotNull Location location, final @NotNull Runnable task) {
        if (FOLIA) {
            Bukkit.getRegionScheduler().run(plugin, location, ignored -> task.run());
            return;
        }
        Bukkit.getScheduler().runTask(plugin, task);
    }
}
