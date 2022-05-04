package me.superbility.superbosses.commands;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.Game;
import me.superbility.superbosses.data.boss.BossType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class StartFightCommand {
    private Main plugin = Main.getPlugin(Main.class);

    public void startFight(String[] args, CommandSender sender) {
        Game game = null;

        if(args.length > 1) {
            try {
                game = new Game(BossType.valueOf(args[1].toUpperCase(Locale.ROOT)));
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "No boss found!");
            }
        } else {
            game = new Game(BossType.FIREDEMON); //TODO RANDOM
        }

        plugin.currentGame = game;
    }
}
