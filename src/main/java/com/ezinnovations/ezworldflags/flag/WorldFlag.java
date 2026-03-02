package com.ezinnovations.ezworldflags.flag;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

/**
 * All available world flags and metadata.
 */
public enum WorldFlag {
    MOB_SPAWNING("mob-spawning", "Mob Spawning", "Whether mobs can naturally spawn", Material.ZOMBIE_HEAD),
    MONSTER_SPAWNING("monster-spawning", "Monster Spawning", "Whether hostile mobs can spawn", Material.SKELETON_SKULL),
    ANIMAL_SPAWNING("animal-spawning", "Animal Spawning", "Whether passive mobs can spawn", Material.EGG),
    PVP("pvp", "PvP", "Whether players can damage each other", Material.DIAMOND_SWORD),
    BLOCK_BREAK("block-break", "Block Breaking", "Whether blocks can be mined", Material.IRON_PICKAXE),
    BLOCK_PLACE("block-place", "Block Placing", "Whether blocks can be placed", Material.GRASS_BLOCK),
    EXPLOSIONS("explosions", "Explosions", "Whether explosions can occur", Material.TNT),
    FIRE_SPREAD("fire-spread", "Fire Spread", "Whether fire can spread", Material.FLINT_AND_STEEL),
    LEAF_DECAY("leaf-decay", "Leaf Decay", "Whether leaves decay naturally", Material.OAK_LEAVES),
    HUNGER_DRAIN("hunger-drain", "Hunger Drain", "Whether hunger depletes", Material.COOKED_BEEF),
    FALL_DAMAGE("fall-damage", "Fall Damage", "Whether fall damage is applied", Material.FEATHER),
    WEATHER("weather", "Weather Cycle", "Whether weather changes naturally", Material.SUNFLOWER),
    ICE_FORM("ice-form", "Ice Formation", "Whether ice forms naturally", Material.ICE),
    SNOW_FORM("snow-form", "Snow Formation", "Whether snow layers form", Material.SNOW_BLOCK),
    REDSTONE("redstone", "Redstone", "Whether redstone mechanics function", Material.REDSTONE),
    ITEM_DROP("item-drop", "Item Drops", "Whether players can drop items", Material.HOPPER),
    ITEM_PICKUP("item-pickup", "Item Pickup", "Whether players can pick up items", Material.CHEST),
    CROP_GROWTH("crop-growth", "Crop Growth", "Whether crops grow naturally", Material.WHEAT),
    ENDERMAN_GRIEF("enderman-grief", "Enderman Griefing", "Whether endermen can pick up blocks", Material.ENDER_PEARL),
    CREEPER_BLOCK_DAMAGE("creeper-block-damage", "Creeper Block Damage", "Whether creeper explosions break blocks", Material.CREEPER_HEAD),
    WITHER_BLOCK_DAMAGE("wither-block-damage", "Wither Block Damage", "Whether wither explosions break blocks", Material.WITHER_SKELETON_SKULL);

    private final String id;
    private final String displayName;
    private final String description;
    private final Material material;

    WorldFlag(final String id, final String displayName, final String description, final Material material) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.material = material;
    }

    public @NotNull String id() { return id; }
    public @NotNull String displayName() { return displayName; }
    public @NotNull String description() { return description; }
    public @NotNull Material material() { return material; }

    public static @Nullable WorldFlag fromId(final String id) {
        if (id == null) {
            return null;
        }
        final String target = id.toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.id.equals(target)).findFirst().orElse(null);
    }
}
