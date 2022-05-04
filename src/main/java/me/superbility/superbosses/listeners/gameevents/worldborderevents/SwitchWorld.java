package me.superbility.superbosses.listeners.gameevents.worldborderevents;

import me.superbility.superbosses.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class SwitchWorld implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void onTeleportFrom(PlayerChangedWorldEvent e) {
        if(plugin.currentGame != null) {
            if (e.getFrom().equals(plugin.currentGame.getBoss().getEntity().getWorld())) {
                plugin.currentGame.removeBorder(e.getPlayer());
            }
        }
    }
    @EventHandler
    private void onTeleportTo(PlayerChangedWorldEvent e) {
        if(plugin.currentGame != null) {
            if (e.getPlayer().getWorld().equals(plugin.currentGame.getBoss().getEntity().getWorld())) {
                plugin.currentGame.createBorder(e.getPlayer());
            }
        }
    }
}
