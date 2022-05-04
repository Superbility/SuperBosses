package me.superbility.superbosses.commands;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.items.player.BossBaton;
import me.superbility.superbosses.data.items.player.PillarPurger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand {
    private Main plugin = Main.getPlugin(Main.class);

    public void give(String[] args, CommandSender sender) {
        if(args.length > 1) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if(player.isOnline() && player != null) {
                if(args.length > 2) {
                    if(args[2].equalsIgnoreCase("pillarPurger")) {
                        player.getInventory().addItem(PillarPurger.pillarPurger());
                    }
                    if(args[2].equalsIgnoreCase("bossBaton")) {
                        player.getInventory().addItem(BossBaton.bossBaton());
                    }
                } else sender.sendMessage(ChatColor.RED + "Usage: /sbs give [player] [item]");
            } else {
                sender.sendMessage(ChatColor.RED + "Player not found!");
            }
        } else sender.sendMessage(ChatColor.RED + "Usage: /sbs give [player] [item]");
    }
}
