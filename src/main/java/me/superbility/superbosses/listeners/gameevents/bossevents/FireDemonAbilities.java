package me.superbility.superbosses.listeners.gameevents.bossevents;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.events.bosshitevent.BossHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class FireDemonAbilities implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    //HOTBAR ABILITY
    @EventHandler
    private void hotbarAbility(BossHitEvent e) {
        int random = ThreadLocalRandom.current().nextInt(1, 20);
        if(random <= 1) {
            Player player = e.getPlayer();
            player.getInventory().setHeldItemSlot(ThreadLocalRandom.current().nextInt(0, 8));
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }
    }

    //LAUNCH ABILITY
    @EventHandler
    private void launchPower(BossHitEvent e) {
        int random = ThreadLocalRandom.current().nextInt(1, 50);
        if (random <= 1) {
            Location location = e.getBoss().getEntity().getLocation();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getWorld().equals(location.getWorld())) {
                    if (player.getLocation().distance(location) < 40) {
                        player.setVelocity(new Vector(0, 1.5, 0));
                        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 20, 2);
                    }
                }
            }
        }
    }

    //LIGHTNING ABILITY
    @EventHandler
    private void lightning(BossHitEvent e) {
        int random = ThreadLocalRandom.current().nextInt(1, 100);
        if(random <= 1) {
            Location location = e.getBoss().getEntity().getLocation();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getWorld().equals(location.getWorld())) {
                    if (player.getLocation().distance(location) < 40) {
                        location.getWorld().strikeLightning(player.getLocation());
                    }
                }
            }
        }
    }

    //TNT FOUNTAIN ABILITY
    @EventHandler
    private void tntFountain(BossHitEvent e) {
            int random = ThreadLocalRandom.current().nextInt(1, 80);
            if (random <= 1) {
                //BossUtils.bossFly(e.getBoss(), 7, 110);

                new BukkitRunnable() {
                    int counter = 50;

                    @Override
                    public void run() {
                        TNTPrimed tnt = (TNTPrimed) e.getBoss().getEntity().getWorld().spawnEntity(e.getBoss().getEntity().getLocation().add(0, 1, 0), EntityType.PRIMED_TNT);
                        tnt.setFuseTicks(30);
                        tnt.setCustomName("TNT Fountain");
                        tnt.setVelocity(new Vector(ThreadLocalRandom.current().nextDouble(-0.3, 0.3), 0.1, ThreadLocalRandom.current().nextDouble(-0.3, 0.3)));

                        counter--;
                        if (counter <= 1) {
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 2, 2);
            }
    }
    @EventHandler
    private void disableExplosion(EntityExplodeEvent e) {
        if(e.getEntity().getCustomName() != null) {
            if (e.getEntity().getCustomName().equals("TNT Fountain") || e.getEntity().getCustomName().equals("Fireball Rain")) {
                e.setCancelled(true);
                e.getLocation().getWorld().createExplosion(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(), 3, false, false);
            }
        }
    }
}
