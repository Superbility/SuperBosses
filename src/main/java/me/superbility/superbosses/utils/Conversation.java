package me.superbility.superbosses.utils;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.boss.BossType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Conversation {
    private static Main plugin = Main.getPlugin(Main.class);

    public static void startConversation(List<String> conversation, Player player, Integer delay, Sound sound) {
        new BukkitRunnable() {
            int line = 0;
            @Override
            public void run() {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', conversation.get(line)));
                player.playSound(player.getLocation(), sound, 20, 5);
                if(line + 1 < conversation.size()) {
                    line++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, delay, delay);
    }
    public static void startConversation(List<String> conversation, Player player, Integer delay, Sound sound, BossType type) {
        new BukkitRunnable() {
            int line = 0;
            @Override
            public void run() {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', conversation.get(line).replace("{boss}", type.getPrefix())));
                player.playSound(player.getLocation(), sound, 20, 5);
                if(line + 1 < conversation.size()) {
                    line++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, delay, delay);
    }
}
