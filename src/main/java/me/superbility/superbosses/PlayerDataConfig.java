package me.superbility.superbosses;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerDataConfig {
    private Main plugin = Main.getPlugin(Main.class);

    public static File file;
    public static FileConfiguration config;

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                //addDefaults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }
    }

    private void addDefaults() {
        config.createSection("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats");
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.MaxHealth", 10000);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.Defense", 50000000);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.Intelligence", 10000);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.Force", 10000);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.CritDamage", 100);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.LuckyChance", 100);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.Speed", 200);
        config.set("426cb606-0d3f-3fb0-b654-2d1439b52704.Stats.Agility", 100);
        saveFile();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
        }
    }

    public void reloadFile() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
