package me.superbility.superbosses.expansions;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.PlayerDataConfig;
import me.superbility.superbosses.data.pillar.Pillar;
import me.superbility.superbosses.utils.ProgressBar;
import me.superbility.superbosses.utils.SortHashmap;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class SuperBossesExpansion extends PlaceholderExpansion {
    private PlayerDataConfig pdc = new PlayerDataConfig();
    private Main plugin;

    public SuperBossesExpansion(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "superbosses";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Superbility";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        String[] args = params.split("_");
        if(args[0].equals("pillar")) {
            if (args.length > 1) {
                if(plugin.currentGame != null) {
                    int pInt = Integer.valueOf(args[1]);
                    Pillar pillar = plugin.currentGame.getPillars().get(pInt - 1);

                    if(pillar.getHealth() <= 0) return "&8[&c████████&8]";

                    int totalBars = 8;
                    char character = '█';

                    String bar = "&8[" + ProgressBar.getProgressBar((int) pillar.getHealth(), (int) pillar.getMaxHealth(), totalBars, character, ChatColor.GREEN, ChatColor.GRAY) + "&8]";
                    return bar;
                } else {
                    return "&cN/A";
                }
            }
        }
        if(args[0].equals("boss")) {
            if (args[1].equals("health")) {
                if (plugin.currentGame != null) {
                    return String.valueOf(plugin.currentGame.getBoss().getHealth());
                } else {
                    return "&cN/A";
                }
            }
        }
        if(args[0].equals("points")) {
            if (plugin.currentGame != null) {
                return String.valueOf(plugin.currentGame.getPlayerPoints().get(player.getUniqueId()));
            } else {
                return "&cN/A";
            }
        }
        if(args[0].equals("leaderboard")) {
            try {
                if (args[1].equals("points")) {
                    int position = Integer.valueOf(args[2]);
                    LinkedHashMap<UUID, Integer> leaderboard = getLeaderboard("TotalPoints");

                    if (args[3].equals("player"))
                        return Bukkit.getOfflinePlayer((UUID) leaderboard.keySet().toArray()[position - 1]).getName();
                    if (args[3].equals("value")) return String.valueOf(leaderboard.values().toArray()[position - 1]);
                }
                if (args[1].equals("destroyedpillars")) {
                    int position = Integer.valueOf(args[2]);
                    LinkedHashMap<UUID, Integer> leaderboard = getLeaderboard("PillarsDestroyed");

                    if (args[3].equals("player"))
                        return Bukkit.getOfflinePlayer((UUID) leaderboard.keySet().toArray()[position - 1]).getName();
                    if (args[3].equals("value")) return String.valueOf(leaderboard.values().toArray()[position - 1]);
                }
                if (args[1].equals("bosskills")) {
                    int position = Integer.valueOf(args[2]);
                    LinkedHashMap<UUID, Integer> leaderboard = getLeaderboard("BossesKilled");

                    if (args[3].equals("player"))
                        return Bukkit.getOfflinePlayer((UUID) leaderboard.keySet().toArray()[position - 1]).getName();
                    if (args[3].equals("value")) return String.valueOf(leaderboard.values().toArray()[position - 1]);
                }
                if (args[1].equals("minionkills")) {
                    int position = Integer.valueOf(args[2]);
                    LinkedHashMap<UUID, Integer> leaderboard = getLeaderboard("MinionKills");

                    if (args[3].equals("player"))
                        return Bukkit.getOfflinePlayer((UUID) leaderboard.keySet().toArray()[position - 1]).getName();
                    if (args[3].equals("value")) return String.valueOf(leaderboard.values().toArray()[position - 1]);
                }
            } catch (Exception o) {
                return "N/A";
            }
        }
        return null;
    }
    public LinkedHashMap<UUID, Integer> getLeaderboard(String type) {
        HashMap<UUID, Integer> unsortedLeaderboard = new HashMap<>();

        for(String s : pdc.getConfig().getConfigurationSection("").getKeys(false)) {
            UUID uuid = UUID.fromString(s);
            int value = pdc.getConfig().getInt(uuid + "." + type);
            unsortedLeaderboard.put(uuid, value);
        }

        LinkedHashMap<UUID, Integer> sortedLeaderboard = SortHashmap.sortByValue(unsortedLeaderboard);

        return sortedLeaderboard;
    }
}
