package me.superbility.superbosses.listeners.gameevents.minionevents;

import me.superbility.superbosses.data.minion.MinionType;
import me.superbility.superbosses.events.minionhitevent.MinionHitEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UpdateMinionHealth implements Listener {
    @EventHandler
    private void onHit(MinionHitEvent e) {
        if(!e.getEntity().isDead()) {
            MinionType type = e.getType();
            LivingEntity entity = (LivingEntity) e.getEntity();
            int health = (int) entity.getHealth();
            String name = type.getNameTag().replace("{health}", String.valueOf(health));
            entity.setCustomName(name);
        }
    }
}
