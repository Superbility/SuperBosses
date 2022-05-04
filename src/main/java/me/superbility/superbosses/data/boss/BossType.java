package me.superbility.superbosses.data.boss;

import me.superbility.superbosses.data.bossentities.FireDemon;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public enum BossType {
    FIREDEMON;

    public int getMaxHealth() {
        switch(this) {
            case FIREDEMON: return 1000;
        }
        return 0;
    }
    public String getPrefix() {
        switch(this) {
            case FIREDEMON: return ChatColor.translateAlternateColorCodes('&', "&c&lFire Demon &8- ");
        }
        return null;
    }
    public LivingEntity getEntity(Location location) {
        switch(this) {
            case FIREDEMON: return FireDemon.spawn(location);
        }
        return null;
    }
    public String getPillarSchematic() {
        switch(this) {
            case FIREDEMON: return "firedemonPillar";
        }
        return null;
    }
    public String getCageSchematic() {
        switch(this) {
            case FIREDEMON: return "fireCage";
        }
        return null;
    }
}
