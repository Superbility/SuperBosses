package me.superbility.superbosses.commands;

import me.superbility.superbosses.data.minion.MinionType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnMinionCommand {
    public void spawnMinion(String[] args, CommandSender sender) {
        if(sender instanceof Player) {
            if(args.length > 1) {
                MinionType type = null;
                try {
                    type = MinionType.valueOf(args[1]);
                } catch (NullPointerException e) {
                    sender.sendMessage(ChatColor.RED + "No minion found!");
                    return;
                }
                type.spawnMinion(((Player) sender).getLocation());
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /sbs minion [type]");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        }
    }
}
