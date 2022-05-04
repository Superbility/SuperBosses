package me.superbility.superbosses.commands;

import me.superbility.superbosses.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndBossFightCommand {
    private Main plugin = Main.getPlugin(Main.class);

    public void end(String[] args, CommandSender sender) {
        if(plugin.currentGame != null) {
            if(sender instanceof Player) {
                plugin.currentGame.setFinishingPlayer((Player) sender);
                plugin.currentGame.addPlayerPoints(((Player) sender).getUniqueId(), 100, "Final Blow");
                plugin.currentGame.endBossFight();
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player!");
                return;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "There is no current game!");
            return;
        }
    }
}
