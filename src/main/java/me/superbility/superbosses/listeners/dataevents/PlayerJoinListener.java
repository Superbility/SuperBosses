package me.superbility.superbosses.listeners.dataevents;

import me.superbility.superbosses.PlayerDataConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private PlayerDataConfig pdc = new PlayerDataConfig();

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();
        if(!pdc.getConfig().contains(uuid)) {
            pdc.getConfig().set(uuid + ".MinionKills", 0);
            pdc.getConfig().set(uuid + ".TotalPoints", 0);
            pdc.getConfig().set(uuid + ".PillarsDestroyed", 0);
            pdc.getConfig().set(uuid + ".BossesKilled", 0);
            pdc.saveFile();
        }
    }
}
