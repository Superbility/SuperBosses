package me.superbility.superbosses.data.items.admin;

import me.superbility.superbosses.utils.ColorLore;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BossSpawnTool {
    public static ItemStack bossSpawnTool() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Boss Spawn Location Tool");
        meta.setLore(ColorLore.getLore(Arrays.asList("&e&lABILITY - RIGHT-CLICK",
                "&7Right click a block to set the location",
                "&7where the boss spawns!")));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("sbsTool", "BOSS_SPAWN_SETTER");

        return nbtItem.getItem();
    }
}
