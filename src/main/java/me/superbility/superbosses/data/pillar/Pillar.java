package me.superbility.superbosses.data.pillar;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.boss.BossType;
import me.superbility.superbosses.data.minion.MinionType;
import me.superbility.superbosses.utils.ProgressBar;
import me.superbility.superbosses.utils.Schematic;
import me.superbility.superbosses.utils.SetAI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Pillar {
    private Main plugin = Main.getPlugin(Main.class);
    private SetAI setAI = new SetAI();

    private LivingEntity entity;
    private double maxHealth;
    private double health;
    private Hologram hologram;
    private boolean destroyed;
    private List<LivingEntity> minions = new ArrayList<>();
    private Location location;

    public Pillar(BossType type, Location loc) {
        Schematic.paste(type.getPillarSchematic(), loc);
        spawnPillarEntity(loc);
        this.maxHealth = this.entity.getMaxHealth();
        this.health = this.entity.getHealth();
        this.destroyed = false;
        this.location = loc;
    }

    public LivingEntity getEntity() {
        return entity;
    }
    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public double getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public Hologram getHologram() {
        return hologram;
    }
    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public boolean isDestroyed() { return destroyed; }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        this.hologram.insertTextLine(1, ChatColor.translateAlternateColorCodes('&',"&4&lDESTROYED"));
        this.hologram.removeLine(2);
        plugin.currentGame.removePillar();
    }

    public Location getLocation() {
        return location;
    }

    public LivingEntity spawnMinion() {
        LivingEntity minion = MinionType.getRandomType().spawnMinion(location.clone().add(0, 5, 0));
        double x = ThreadLocalRandom.current().nextDouble(-2, 2);
        double z = ThreadLocalRandom.current().nextDouble(-2, 2);
        Vector vector = new Vector(x, 1, z); //TODO MAKE DIRECTION OF BOSS
        minion.setVelocity(vector);
        return minion;
    }
    private void spawnPillarEntity(Location location) {
        Slime slime = (Slime) location.getWorld().spawnEntity(location.clone().add(0.5, 1.5, 0.5), EntityType.SLIME);

        slime.setSize(8);
        slime.setMaxHealth(2000);
        slime.setHealth(2000);
        slime.setFireTicks(-1000000000);
        setAI.setAI(slime, false);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, 100, true));
        slime.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000000, 100, true));
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 100, true));
        slime.setMetadata("pillar", new FixedMetadataValue(plugin, true));
        slime.setCustomName("Pillar");
        slime.setCustomNameVisible(false);

        Hologram holo = HologramsAPI.createHologram(plugin, location.clone().add(0.5, 6,0.5));
        holo.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&c&lPILLAR"));
        holo.appendTextLine("N/A");

        this.entity = slime;
        this.hologram = holo;
        updateHolo();
    }
    public void updateHolo() {
        int totalBars = 30;
        char character = '|';

        this.hologram.insertTextLine(1, ChatColor.translateAlternateColorCodes('&',"&cHealth: &8[" +
                ProgressBar.getProgressBar((int) this.health, (int) this.maxHealth, totalBars, character, ChatColor.GREEN, ChatColor.GRAY) + "&8]"));
        this.hologram.removeLine(2);
    }
    public void remove() {
        destroyed = true;
        if(entity != null) {
            entity.remove();
        }
        hologram.delete();
    }
}
