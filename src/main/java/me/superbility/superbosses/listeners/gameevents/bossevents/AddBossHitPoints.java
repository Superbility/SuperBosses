package me.superbility.superbosses.listeners.gameevents.bossevents;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.events.bosshitevent.BossHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AddBossHitPoints implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void onBossHit(BossHitEvent e) {
        if (plugin.currentGame != null) {
            plugin.currentGame.addPlayerPoints(e.getPlayer().getUniqueId(), 3, "Boss Hit");
        }
    }
}
