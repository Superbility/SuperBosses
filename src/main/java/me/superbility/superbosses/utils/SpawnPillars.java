package me.superbility.superbosses.utils;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.Game;
import me.superbility.superbosses.data.boss.BossType;
import me.superbility.superbosses.data.pillar.Pillar;
import org.bukkit.Location;

public class SpawnPillars {
    private Main plugin = Main.getPlugin(Main.class);

    public void spawnPillars(BossType type) {
        for(String s : plugin.getConfig().getStringList("BossFight.Locations.PillarSpawns")) {
            Game fight = plugin.currentGame;
            Location loc = LocationSerialiser.getLiteLocationFromString(s);
            fight.addPillar(new Pillar(type, loc));
        }
    }
}
