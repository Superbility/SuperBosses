package me.superbility.superbosses.listeners.gameevents.bossevents;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.Game;
import me.superbility.superbosses.events.bosshitevent.BossHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BossDeathListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void onDeath(BossHitEvent e) {
        Game game = plugin.currentGame;

        if(e.getBoss().getHealth() <= 0) {
            game.setFinishingPlayer(e.getPlayer());
            game.endBossFight();
        }
    }
}
