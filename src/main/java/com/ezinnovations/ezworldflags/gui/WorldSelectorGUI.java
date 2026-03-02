package com.ezinnovations.ezworldflags.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Builds world selector inventory.
 */
public final class WorldSelectorGUI {
    public static final String TITLE = "EzWorldFlags — Select World";

    public void open(final @NotNull Player player) {
        final int size = 27;
        final Inventory inventory = Bukkit.createInventory(null, size, Component.text(TITLE));
        int slot = 0;
        for (World world : Bukkit.getWorlds()) {
            if (slot >= size) {
                break;
            }
            final Material icon = switch (world.getEnvironment()) {
                case NETHER -> Material.NETHERRACK;
                case THE_END -> Material.END_STONE;
                default -> Material.GRASS_BLOCK;
            };
            final ItemStack stack = new ItemStack(icon);
            final ItemMeta meta = stack.getItemMeta();
            meta.displayName(Component.text("§a" + world.getName()));
            meta.lore(List.of(Component.text("§eClick to edit flags")));
            stack.setItemMeta(meta);
            inventory.setItem(slot++, stack);
        }
        player.openInventory(inventory);
    }
}
