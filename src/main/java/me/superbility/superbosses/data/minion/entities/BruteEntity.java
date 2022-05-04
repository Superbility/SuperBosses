package me.superbility.superbosses.data.minion.entities;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.minion.MinionType;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BruteEntity {
    private static Main plugin = Main.getPlugin(Main.class);

    private static MinionType type = MinionType.BRUTE;

    public static LivingEntity spawnBrute(Location location) {
        int health = type.getMaxHealth();
        String name = type.getNameTag().replace("{health}", String.valueOf(health));

        MagmaCube entity = (MagmaCube) location.getWorld().spawnEntity(location, EntityType.MAGMA_CUBE);

        entity.setCanPickupItems(false);
        entity.setRemoveWhenFarAway(false);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000000, 2, true));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000000, 4, true));
        entity.setSize(2);
        entity.setMetadata("minion", new FixedMetadataValue(plugin, type));
        entity.setMaxHealth(health);
        entity.setHealth(health);
        entity.setCustomName(name);

        return entity;
    }
    public static void spawnBrute(Location loc, String s) {
        MagmaCube magmacube = loc.getWorld().spawn(loc, MagmaCube.class);
        magmacube.throwEgg();

    }
}
