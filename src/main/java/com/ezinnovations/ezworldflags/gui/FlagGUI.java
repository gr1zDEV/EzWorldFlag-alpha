package com.ezinnovations.ezworldflags.gui;

import com.ezinnovations.ezworldflags.EzWorldFlags;
import com.ezinnovations.ezworldflags.flag.FlagState;
import com.ezinnovations.ezworldflags.flag.WorldFlag;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds world flag editor GUI.
 */
public final class FlagGUI {
    private final EzWorldFlags plugin;

    public FlagGUI(final @NotNull EzWorldFlags plugin) {
        this.plugin = plugin;
    }

    public void open(final @NotNull org.bukkit.entity.Player player, final @NotNull World world) {
        final Inventory inventory = Bukkit.createInventory(null, 54, Component.text("EzWorldFlags — " + world.getName()));
        int slot = 0;
        for (WorldFlag flag : WorldFlag.values()) {
            inventory.setItem(slot++, buildFlagItem(world, flag));
        }
        for (int i = 36; i < 45; i++) {
            inventory.setItem(i, simple(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
        inventory.setItem(45, simple(Material.BARRIER, "§cBack to Worlds"));
        inventory.setItem(49, simple(Material.TNT, "§cReset All"));
        inventory.setItem(53, simple(Material.EMERALD, "§aSave & Close"));
        player.openInventory(inventory);
    }

    public @NotNull ItemStack buildFlagItem(final @NotNull World world, final @NotNull WorldFlag flag) {
        final FlagState raw = plugin.getFlagManager().getRawFlag(world.getName(), flag);
        final FlagState resolved = plugin.getFlagManager().getFlag(world, flag);
        final ItemStack stack = new ItemStack(flag.material());
        final ItemMeta meta = stack.getItemMeta();
        final String color = raw == FlagState.DENY ? "§c" : raw == FlagState.DEFAULT ? "§e" : "§a";
        meta.displayName(Component.text(color + flag.displayName()));
        final List<Component> lore = new ArrayList<>();
        if (raw == FlagState.DEFAULT) {
            lore.add(Component.text("§7Status: §eDEFAULT §8(inherits: " + (resolved == FlagState.DENY ? "§cDENY" : "§aALLOW") + "§8)"));
        } else {
            lore.add(Component.text("§7Status: " + (raw == FlagState.DENY ? "§cDENY" : "§aALLOW")));
        }
        lore.add(Component.text("§7" + flag.description()));
        lore.add(Component.text("§8"));
        lore.add(Component.text("§eClick to toggle"));
        lore.add(Component.text("§7Shift+Click for DEFAULT"));
        if (raw == FlagState.DENY) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.lore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack simple(final Material material, final String name) {
        final ItemStack stack = new ItemStack(material);
        final ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component.text(name));
        stack.setItemMeta(meta);
        return stack;
    }
}
