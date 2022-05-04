package me.superbility.superbosses.events.minionhitevent;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.minion.MinionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MinionHitEventListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    //CREATE CUSTOM EVENT
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity().hasMetadata("minion")) {
                Entity entity = e.getEntity();
                MinionType type = MinionType.valueOf(e.getEntity().getMetadata("minion").get(0).value().toString());
                Player player = (Player) e.getDamager();
                double damage = e.getDamage();

                MinionHitEvent minionHitEvent = new
                        MinionHitEvent(entity, player, type, damage); // Initialize your Event
                Bukkit.getPluginManager().callEvent(minionHitEvent); // This fires the event and allows any listener to listen to the event
            }
        }
    }
    //CREATE CUSTOM EVENT
}
