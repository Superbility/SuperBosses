package me.superbility.superbosses.listeners.tools;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.utils.LocationSerialiser;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BossSpawnTool implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void setBossSpawn(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("sbs.admin")) {
            ItemStack item = player.getItemInHand();
            if (item.getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(item);
                if (nbtItem.hasKey("sbsTool")) {
                    String toolType = nbtItem.getString("sbsTool");
                    if (toolType.equals("BOSS_SPAWN_SETTER")) {
                        e.setCancelled(true);
                        Action action = e.getAction();

                        Location location = e.getClickedBlock().getLocation().clone().add(0.5, 0, 0.5);
                        String sLoc = LocationSerialiser.getLiteStringFromLocation(location);

                        if (action == Action.RIGHT_CLICK_BLOCK) {
                            plugin.getConfig().set("BossFight.Locations.BossSpawn", sLoc);
                            plugin.saveConfig();
                            player.sendMessage(ChatColor.GREEN + "Location set successfully!");
                        }
                    }
                }
            }
        }
    }
}
