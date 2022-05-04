package me.superbility.superbosses.listeners.gameevents.pillarevents;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.PlayerDataConfig;
import me.superbility.superbosses.data.pillar.Pillar;
import me.superbility.superbosses.events.pillarhitevent.PillarHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class PillarBreakEvent implements Listener {
    private Main plugin = Main.getPlugin(Main.class);
    private PlayerDataConfig pdc = new PlayerDataConfig();

    private List<String> pillarBreakMessage = plugin.getConfig().getStringList("Messages.PillarBreak");

    @EventHandler
    private void onHit(PillarHitEvent e) {
        Pillar pillar = e.getPillar();
        if(pillar.getHealth() - e.getDamage() <= 0) {
            breakPillar(pillar, e.getPlayer());
        }
    }
    public void breakPillar(Pillar pillar, Player player) {
        pillar.setDestroyed(true);
        Entity pillarEntity = pillar.getEntity();
        List<Entity> entities = pillarEntity.getNearbyEntities(10, 10, 10);

        pillarEntity.getWorld().createExplosion(pillarEntity.getLocation(), 1, false);
        pillar.getLocation().getBlock().setType(Material.AIR);

        pillar.setHealth(0);

        for(Entity entity : entities) {
            if(entity instanceof LivingEntity) {
                entity.setVelocity(new Vector(0, 1.5, 0));
            }
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            for(String s : pillarBreakMessage) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', s.replace("{player}", player.getDisplayName())));
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
            }
        }

        int currentAmount = pdc.getConfig().getInt(player.getUniqueId() + ".PillarsDestroyed");
        pdc.getConfig().set(player.getUniqueId() + ".PillarsDestroyed", currentAmount + 1);
        pdc.saveFile();

        pillar.getEntity().remove();

        player.getInventory().addItem(new ItemStack(Material.BEACON));
    }
}
