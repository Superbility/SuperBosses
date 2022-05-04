package me.superbility.superbosses.listeners.gameevents.bossevents;

import me.superbility.superbosses.data.boss.Boss;
import me.superbility.superbosses.events.bosshitevent.BossHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UniversalBossAbilities implements Listener {
    @EventHandler //TODO FIX THIS
    private void onHit(BossHitEvent e) {
        Boss boss = e.getBoss();
        if(System.currentTimeMillis() - boss.getLastHitTime() >= 4000) {
            boss.getEntity().teleport(e.getPlayer());
        }
        boss.setLastHitTime();
    }
}
