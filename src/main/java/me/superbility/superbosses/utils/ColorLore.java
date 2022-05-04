package me.superbility.superbosses.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorLore {
    public static List<String> getLore(List<String> oldLore) {
        List<String> newLore = new ArrayList<>();
        for(String s : oldLore) {
            newLore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return newLore;
    }
}
