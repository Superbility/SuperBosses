package me.superbility.superbosses.data.minion.entities;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.minion.MinionType;
import org.bukkit.Location;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SentryEntity {
    private static Main plugin = Main.getPlugin(Main.class);

    private static MinionType type = MinionType.SENTRY;

    public static LivingEntity spawnBrute(Location location) {
        int health = type.getMaxHealth();
        String name = type.getNameTag().replace("{health}", String.valueOf(health));

        Blaze entity = (Blaze) location.getWorld().spawnEntity(location, EntityType.BLAZE);

        entity.setCanPickupItems(false);
        entity.setRemoveWhenFarAway(false);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000000, 2, true));
        entity.setMetadata("minion", new FixedMetadataValue(plugin, type));
        entity.setMaxHealth(health);
        entity.setHealth(health);
        entity.setCustomName(name);

        return entity;
    }
}
