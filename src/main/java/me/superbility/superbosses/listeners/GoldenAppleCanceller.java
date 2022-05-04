package me.superbility.superbosses.listeners;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.utils.LocationSerialiser;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class GoldenAppleCanceller implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    private static ItemStack eGoldApple = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);

    @EventHandler
    private void onEat(PlayerItemConsumeEvent e) {
        if(e.getItem().isSimilar(eGoldApple)) {
            Location bossSpawn = LocationSerialiser.getLiteLocationFromString(plugin.getConfig().getString("BossFight.Locations.BossSpawn"));
            if(e.getPlayer().getWorld() == bossSpawn.getWorld()) {
                e.getPlayer().sendMessage(ChatColor.RED + "You can't use that here!");
                e.setCancelled(true);
            }
        }
    }
}
