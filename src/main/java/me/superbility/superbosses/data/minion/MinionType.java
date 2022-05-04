package me.superbility.superbosses.data.minion;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.boss.BossType;
import me.superbility.superbosses.data.minion.entities.BruteEntity;
import me.superbility.superbosses.data.minion.entities.SentryEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.ThreadLocalRandom;

public enum MinionType {
    BRUTE, SENTRY;

    private static Main plugin = Main.getPlugin(Main.class);

    public int getMaxHealth() {
        switch (this) {
            case BRUTE: return 75;
            case SENTRY: return 25;
        }
        return 0;
    }
    public String getNameTag() {
        switch (this) {
            case BRUTE: return ChatColor.translateAlternateColorCodes('&', "&6Brute &8- &c{health} health");
            case SENTRY: return ChatColor.translateAlternateColorCodes('&', "&6Sentry &8- &c{health} health");
        }
        return "";
    }
    public LivingEntity spawnMinion(Location location) {
        switch (this) {
            case BRUTE: return BruteEntity.spawnBrute(location);
            case SENTRY: return SentryEntity.spawnBrute(location);
        }
        return null;
    }
    public static MinionType getRandomType() {
        try {
            BossType type = plugin.currentGame.getBoss().getType();
            switch (type) {
                case FIREDEMON:
                    int random = ThreadLocalRandom.current().nextInt(1, 4);
                    if (random <= 1) return MinionType.SENTRY;
                    else return MinionType.BRUTE;
            }
        } catch (NullPointerException e) {}
        return null;
    }
}
