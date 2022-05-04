package me.superbility.superbosses.data.items.admin;

import me.superbility.superbosses.utils.ColorLore;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PillarTool {
    public static ItemStack pillarTool() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Pillar Setter");
        meta.setLore(ColorLore.getLore(Arrays.asList("&e&lABILITY - LEFT-CLICK",
                "&7Left click a block to delete a",
                "&7pillar spawn!",
                "",
                "&e&lABILITY - RIGHT-CLICK",
                "&7Right click a block to create a",
                "&7pillar spawn!")));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("sbsTool", "PILLAR_SETTER");

        return nbtItem.getItem();
    }
}
