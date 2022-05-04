package me.superbility.superbosses.data.items.player;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.utils.ColorLore;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BossBaton {
    private static Main plugin = Main.getPlugin(Main.class);

    public static ItemStack bossBaton() {
        ItemStack item = new ItemStack(Material.valueOf(plugin.getConfig().getString("Items.BossBaton.Material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Items.BossBaton.Name")));
        meta.setLore(ColorLore.getLore(plugin.getConfig().getStringList("Items.BossBaton.Lore")));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("sbsItem", "BOSS_BATON");

        return nbtItem.getItem();
    }
}
