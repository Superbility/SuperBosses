package me.superbility.superbosses.data;

import me.superbility.superbosses.Main;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public enum RewardType {
    ESSENCE, COINS, XP;

    private Main plugin = Main.getPlugin(Main.class);

    private String essenceCommand = plugin.getConfig().getString("Rewards.Commands.Essence");
    private String coinsCommand = plugin.getConfig().getString("Rewards.Commands.Coins");
    private String xpCommand = plugin.getConfig().getString("Rewards.Commands.XP");

    public String getRawCommand() {
        switch (this) {
            case ESSENCE: return essenceCommand;
            case COINS: return coinsCommand;
            case XP: return xpCommand;
        }
        return null;
    }
    public static RewardType getRandomReward() {
        int random = ThreadLocalRandom.current().nextInt(0, 4);
        if(random == 1) return ESSENCE;
        if(random == 2) return COINS;
        if(random == 3) return XP;
        return null;
    }
    public String getCommandFormatted(OfflinePlayer player, int placement) {
        switch (this) {
            case ESSENCE: return ESSENCE.getRawCommand()
                    .replace("{player}", player.getName())
                    .replace("{amount}", String.valueOf(ESSENCE.getAmount(placement)));
            case COINS: return COINS.getRawCommand()
                    .replace("{player}", player.getName())
                    .replace("{amount}", String.valueOf(COINS.getAmount(placement)));
            case XP: return XP.getRawCommand()
                    .replace("{player}", player.getName())
                    .replace("{amount}", String.valueOf(XP.getAmount(placement)));
        }
        return null;
    }
    public String getName() {
        switch (this) {
            case ESSENCE: return ChatColor.AQUA + "Essence";
            case COINS: return ChatColor.GOLD + "Coins";
            case XP: return ChatColor.GREEN + "Experience";
        }
        return null;
    }
    public int getAmount(int placement) {
        switch(this) {
            case ESSENCE:
                if(plugin.getConfig().contains("Rewards.Placement." + placement + ".Essence")) return plugin.getConfig().getInt("Rewards.Placement." + placement + ".Essence");
                else {
                    Set<String> configSection = plugin.getConfig().getConfigurationSection("Rewards.Placement").getKeys(false);
                    return plugin.getConfig().getInt("Rewards.Placement." + configSection.toArray()[configSection.size() - 1] + ".Essence");
                }
            case COINS:
                if(plugin.getConfig().contains("Rewards.Placement." + placement + ".Coins")) return plugin.getConfig().getInt("Rewards.Placement." + placement + ".Coins");
                else {
                    Set<String> configSection = plugin.getConfig().getConfigurationSection("Rewards.Placement").getKeys(false);
                    return plugin.getConfig().getInt("Rewards.Placement." + configSection.toArray()[configSection.size() - 1] + ".Coins");
                }
            case XP:
                if(plugin.getConfig().contains("Rewards.Placement." + placement + ".XP")) return plugin.getConfig().getInt("Rewards.Placement." + placement + ".XP");
                else {
                    Set<String> configSection = plugin.getConfig().getConfigurationSection("Rewards.Placement").getKeys(false);
                    return plugin.getConfig().getInt("Rewards.Placement." + configSection.toArray()[configSection.size() - 1] + ".XP");
                }
        }
        return 0;
    }
}
