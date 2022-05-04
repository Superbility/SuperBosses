package me.superbility.superbosses.commands;

import me.superbility.superbosses.data.items.admin.BossSpawnTool;
import me.superbility.superbosses.data.items.admin.PillarTool;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveToolCommand {
    public void give(String[] args, CommandSender sender) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length > 1) {
                String arg = args[1];

                if(arg.equalsIgnoreCase("pillarTool")) {
                    player.setItemInHand(PillarTool.pillarTool());
                    return;
                }

                if(arg.equalsIgnoreCase("bossSpawnTool")) {
                    player.setItemInHand(BossSpawnTool.bossSpawnTool());
                    return;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /sbs tool [type]");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        }
    }
}
