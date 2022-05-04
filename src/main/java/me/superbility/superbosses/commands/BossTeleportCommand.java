package me.superbility.superbosses.commands;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BossTeleportCommand {
    private Main plugin = Main.getPlugin(Main.class);

    public void teleport(String[] args, CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Game currentGame = plugin.currentGame;
            if(currentGame != null) {
                player.teleport(currentGame.getBoss().getEntity());
                player.sendMessage(ChatColor.GREEN + "Successfully teleported!");
            } else {
                sender.sendMessage(ChatColor.RED + "There is no current boss fight!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to do this!");
        }
    }
}
