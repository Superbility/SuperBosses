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

import java.util.List;

public class PillarTool implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void pillarManager(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("sbs.admin")) {
            ItemStack item = player.getItemInHand();
            if (item.getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(item);
                if (nbtItem.hasKey("sbsTool")) {
                    String toolType = nbtItem.getString("sbsTool");
                    if (toolType.equals("PILLAR_SETTER")) {
                        e.setCancelled(true);
                        Action action = e.getAction();

                        Location location = e.getClickedBlock().getLocation();
                        String sLoc = LocationSerialiser.getLiteStringFromLocation(location);
                        List<String> currentLocations = plugin.getConfig().getStringList("BossFight.Locations.PillarSpawns");

                        if (action == Action.RIGHT_CLICK_BLOCK) {
                            if(!currentLocations.contains(sLoc)) {
                                currentLocations.add(sLoc);
                                plugin.getConfig().set("BossFight.Locations.PillarSpawns", currentLocations);
                                player.sendMessage(ChatColor.GREEN + "Location added successfully!");
                            } else {
                                player.sendMessage(ChatColor.RED + "That is already a location!");
                            }
                        } else if(action == Action.LEFT_CLICK_BLOCK) {
                            if(currentLocations.contains(sLoc)) {
                                currentLocations.remove(sLoc);
                                plugin.getConfig().set("BossFight.Locations.PillarSpawns", currentLocations);
                                player.sendMessage(ChatColor.GREEN + "Location remove successfully!");
                            } else {
                                player.sendMessage(ChatColor.RED + "This is not a location!");
                            }
                        } else return;

                        plugin.saveConfig();
                    }
                }
            }
        }
    }
}
