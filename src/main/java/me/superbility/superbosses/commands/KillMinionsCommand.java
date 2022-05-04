package me.superbility.superbosses.commands;

import me.superbility.superbosses.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;

public class KillMinionsCommand {
    private Main plugin = Main.getPlugin(Main.class);

    public void kill(String[] args, CommandSender sender) {
        if(plugin.currentGame != null) {
            for(LivingEntity e : plugin.currentGame.getMinions()) {
                e.remove();
            }
            plugin.currentGame.removeAllMinions();
        }
    }
}
