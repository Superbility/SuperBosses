package me.superbility.superbosses.data.boss;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.utils.LocationSerialiser;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class Boss {
    private Main plugin = Main.getPlugin(Main.class);

    private Location bossSpawnLocation = LocationSerialiser.getLiteLocationFromString(plugin.getConfig().getString("BossFight.Locations.BossSpawn"));

    private LivingEntity entity;
    private BossType type;
    private int maxHealth;
    private int health;
    private Entity slime;
    private Entity healthBar;
    private long lastHitTime;

    public Boss(BossType type) {
        this.type = type;
        this.entity = type.getEntity(bossSpawnLocation);
        this.slime = this.entity.getPassenger();
        this.healthBar = this.entity.getPassenger().getPassenger();

        this.maxHealth = type.getMaxHealth();
        this.health = type.getMaxHealth();

        this.lastHitTime = 0;
    }

    public Location getBossSpawnLocation() {
        return bossSpawnLocation;
    }
    public void setBossSpawnLocation(Location bossSpawnLocation) {
        this.bossSpawnLocation = bossSpawnLocation;
    }

    public LivingEntity getEntity() {
        return entity;
    }
    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public BossType getType() {
        return type;
    }
    public void setType(BossType type) {
        this.type = type;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void damage(int amount) {
        this.health = this.health - amount;
    }

    public Entity getHealthBar() {
        return healthBar;
    }
    public void setHealthBar(Entity healthBar) {
        this.healthBar = healthBar;
    }

    public Entity getSlime() {
        return this.slime;
    }

    public long getLastHitTime() {
        return this.lastHitTime;
    }
    public void setLastHitTime() {
        this.lastHitTime = System.currentTimeMillis();
    }

    public void remove() {
        if(entity != null && !entity.isDead()) {
            healthBar.remove();
            slime.remove();
            entity.remove();
        }
    }
}
