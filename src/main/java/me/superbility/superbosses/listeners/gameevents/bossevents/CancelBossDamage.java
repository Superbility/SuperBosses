package me.superbility.superbosses.listeners.gameevents.bossevents;

import me.superbility.superbosses.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class CancelBossDamage implements Listener {
    private Main plugin = Main.getPlugin(Main.class);
    private int count = 0;

    @EventHandler
    public void onHit(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("boss")) {
            e.setCancelled(true);
            if(e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                count++;
            }
            if(count >= 10) {
                plugin.currentGame.teleportBoss(plugin.currentGame.getBoss().getBossSpawnLocation());
                count = 0;
            }
        }
    }
}
