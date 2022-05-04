package me.superbility.superbosses.listeners.gameevents.bossevents;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.events.bosshitevent.BossHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class UpdateBossHealth implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler(priority = EventPriority.LOWEST)
    private void onBossHit(BossHitEvent e) {
        e.getBoss().damage(1);
        String name = e.getBoss().getType().getPrefix() + e.getBoss().getHealth();
        e.getBoss().getHealthBar().setCustomName(name);
    }
}