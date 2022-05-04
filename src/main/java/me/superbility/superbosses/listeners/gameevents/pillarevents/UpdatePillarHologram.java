package me.superbility.superbosses.listeners.gameevents.pillarevents;

import me.superbility.superbosses.events.pillarhitevent.PillarHitEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class UpdatePillarHologram implements Listener {
    @EventHandler (priority = EventPriority.LOWEST)
    private void onHit(PillarHitEvent e) {
        e.getGame().addPlayerPoints(e.getPlayer().getUniqueId(), 1, "Pillar hit");
        LivingEntity entity = e.getPillar().getEntity();
        e.getPillar().setHealth((int) entity.getHealth());
        e.getPillar().updateHolo();
    }
}
