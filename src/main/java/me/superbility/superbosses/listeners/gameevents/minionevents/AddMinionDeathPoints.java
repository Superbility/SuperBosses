package me.superbility.superbosses.listeners.gameevents.minionevents;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.PlayerDataConfig;
import me.superbility.superbosses.events.minionhitevent.MinionHitEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AddMinionDeathPoints implements Listener {
    private Main plugin = Main.getPlugin(Main.class);
    private PlayerDataConfig pdc = new PlayerDataConfig();

    @EventHandler
    private void onMinionDeath(MinionHitEvent e) {
        if(!e.getEntity().isDead()) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            if(entity.getHealth() - e.getDamage() <= 0) {
                plugin.currentGame.addPlayerPoints(e.getPlayer().getUniqueId(), 3, "Minion Kill");
                plugin.currentGame.removeMinion(entity);

                int currentAmount = pdc.getConfig().getInt(e.getPlayer().getUniqueId() + ".MinionKills");
                pdc.getConfig().set(e.getPlayer().getUniqueId() + ".MinionKills", currentAmount + 1);
                pdc.saveFile();
            }
        }
    }
}
