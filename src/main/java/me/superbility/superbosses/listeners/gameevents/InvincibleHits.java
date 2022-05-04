package me.superbility.superbosses.listeners.gameevents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class InvincibleHits implements Listener {
    @EventHandler
    private void onEntityDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if(entity.hasMetadata("invincible") && entity.getMetadata("invincible").get(0).value().equals(true)) {
            e.setCancelled(true);
        }
    }
}
