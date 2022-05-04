package me.superbility.superbosses.utils;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.boss.Boss;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BossUtils { //MAKES BOSS FLY
    private static Main plugin = Main.getPlugin(Main.class);
    private static SetAI setAI = new SetAI();

    public static void bossFly(Boss boss, int height, int time) { //TODO REWRITE METHOD
        LivingEntity entity = boss.getEntity();
        Location location = entity.getLocation().add(0, height, 0);

        for(int i = 0; i < height + 2; ++i) {
            if(entity.getLocation().clone().add(0, i, 0).getBlock().getType() != Material.AIR) {
                entity.teleport(boss.getBossSpawnLocation().add(0, height, 0));
                location = entity.getLocation();
                break;
            }
        }

        Location finalLocation = location;
        new BukkitRunnable() {
            int counter = 0;
            @Override
            public void run() {
                finalLocation.getWorld().getBlockAt(finalLocation).setType(Material.STAINED_GLASS_PANE);
                finalLocation.subtract(0,1,0);
                counter++;
                if(counter == height) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 1, 5);

        Bat bat = (Bat) finalLocation.getWorld().spawnEntity(finalLocation, EntityType.BAT);
        setAI.setAI(bat, false);
        bat.setMaxHealth(1000);
        bat.setHealth(1000);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 200, true));

        bat.setPassenger(entity);

        finalLocation.subtract(0, 1, 0);

        new BukkitRunnable() {
            @Override
            public void run() {
                bat.remove();

                new BukkitRunnable() {
                    int counter = 0;
                    @Override
                    public void run() {
                        if(finalLocation.getWorld().getBlockAt(finalLocation.clone().add(0,1,0)).getType().equals(Material.STAINED_GLASS_PANE)) {
                            finalLocation.getWorld().getBlockAt(finalLocation.add(0, 1, 0)).setType(Material.AIR);
                            counter++;
                            if (counter == height) {
                                cancel();
                            }
                        }
                    }
                }.runTaskTimer(plugin, 1, 5);

            }
        }.runTaskLater(plugin, time);
    }
    //MAKES BOSS FLY

}
