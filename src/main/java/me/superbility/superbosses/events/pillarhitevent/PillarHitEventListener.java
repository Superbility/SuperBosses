package me.superbility.superbosses.events.pillarhitevent;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.pillar.Pillar;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PillarHitEventListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler (priority = EventPriority.HIGHEST)
    private void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType().isAlive()) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            if (entity.getType() == EntityType.SLIME) {
                if (entity.hasMetadata("pillar")) {
                    if (e.getDamager() instanceof Player) {
                        Player player = (Player) e.getDamager();
                        Pillar pillar = null;
                        for (Pillar p : plugin.currentGame.getPillars()) {
                            if (p.getEntity().getUniqueId().equals(entity.getUniqueId())) {
                                pillar = p;
                            }
                        }
                        if (pillar != null) {
                            double damage = e.getDamage();
                            PillarHitEvent hitEvent = new
                                    PillarHitEvent(pillar, player, plugin.currentGame, damage, e); // Initialize your Event
                            Bukkit.getPluginManager().callEvent(hitEvent); // This fires the event and allows any listener to listen to the event
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    private void onHit(EntityDamageEvent e) {
        if (e.getEntity().getType().isAlive()) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            if (entity.getType() == EntityType.SLIME) {
                if (entity.hasMetadata("pillar")) {
                    if(!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}