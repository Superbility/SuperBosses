package me.superbility.superbosses.events.bosshitevent;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.boss.Boss;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BossHitEventListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    //CREATE CUSTOM EVENT
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity().hasMetadata("boss")) {
                Player player = (Player) e.getDamager();
                ItemStack item = player.getItemInHand();
                if (item.getType() != Material.AIR) {
                    NBTItem nbtItem = new NBTItem(item);
                    if (nbtItem.hasKey("sbsItem") && nbtItem.getString("sbsItem").equals("BOSS_BATON")) {
                        if (plugin.currentGame.getUnbrokenPillars() <= 0) {
                            Boss boss = plugin.currentGame.getBoss();
                            e.setDamage(0);
                            BossHitEvent bossHitEvent = new
                                    BossHitEvent(boss, player); // Initialize your Event
                            Bukkit.getPluginManager().callEvent(bossHitEvent); // This fires the event and allows any listener to listen to the event
                        } else {
                            player.sendMessage(ChatColor.RED + "Nice try");
                        }
                    } else {
                        List<String> needBaton = plugin.getConfig().getStringList("Messages.NeedBaton");
                        for (String s : needBaton) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                        }
                    }
                }
            }
        }
    }
    //CREATE CUSTOM EVENT
}
