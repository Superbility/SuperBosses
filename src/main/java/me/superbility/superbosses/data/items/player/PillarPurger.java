package me.superbility.superbosses.data.items.player;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.utils.ColorLore;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class PillarPurger {
    private static Main plugin = Main.getPlugin(Main.class);

    public static ItemStack pillarPurger() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.getConfig().getString("Items.PillarPurger.Material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Items.PillarPurger.Name")));
        meta.setLore(ColorLore.getLore(plugin.getConfig().getStringList("Items.PillarPurger.Lore")));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("sbsItem", "PILLAR_PURGER");
        nbtItem.setString("uuid", String.valueOf(UUID.randomUUID()));

        return nbtItem.getItem();
    }
}
