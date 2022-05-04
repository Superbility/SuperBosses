package me.superbility.superbosses;

import me.superbility.superbosses.commands.MainCommand;
import me.superbility.superbosses.data.Game;
import me.superbility.superbosses.data.bossentities.FireDemon;
import me.superbility.superbosses.data.minion.entities.nms.NMSBruteEntity;
import me.superbility.superbosses.events.bosshitevent.BossHitEventListener;
import me.superbility.superbosses.events.minionhitevent.MinionHitEventListener;
import me.superbility.superbosses.events.pillarhitevent.PillarHitEventListener;
import me.superbility.superbosses.expansions.SuperBossesExpansion;
import me.superbility.superbosses.listeners.GoldenAppleCanceller;
import me.superbility.superbosses.listeners.dataevents.PlayerJoinListener;
import com.superdevelopment.superbosses.listeners.gameevents.bossevents.*;
import me.superbility.superbosses.listeners.gameevents.minionevents.AddMinionDeathPoints;
import me.superbility.superbosses.listeners.gameevents.minionevents.UpdateMinionHealth;
import me.superbility.superbosses.listeners.gameevents.pillarevents.PillarBreakEvent;
import me.superbility.superbosses.listeners.gameevents.pillarevents.PillarPurgerEvent;
import me.superbility.superbosses.listeners.gameevents.pillarevents.PillarSpikes;
import me.superbility.superbosses.listeners.gameevents.pillarevents.UpdatePillarHologram;
import me.superbility.superbosses.listeners.gameevents.worldborderevents.SwitchWorld;
import me.superbility.superbosses.listeners.tools.BossSpawnTool;
import me.superbility.superbosses.listeners.tools.PillarTool;
import me.superbility.superbosses.data.minion.entities.nms.RegisterNMSEntity;
import io.netty.util.internal.ThreadLocalRandom;
import me.superbility.superbosses.listeners.gameevents.bossevents.*;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import net.minecraft.server.v1_8_R3.EntityMagmaCube;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin {
    public Game currentGame;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new PlayerDataConfig().setup();

        registerEntities();

        getServer().getPluginManager().registerEvents(new BossHitEventListener(), this);
        getServer().getPluginManager().registerEvents(new PillarHitEventListener(), this);
        getServer().getPluginManager().registerEvents(new MinionHitEventListener(), this);
        getServer().getPluginManager().registerEvents(new BossDeathListener(), this);

        getServer().getPluginManager().registerEvents(new UpdateBossHealth(), this);
        getServer().getPluginManager().registerEvents(new UpdatePillarHologram(), this);
        getServer().getPluginManager().registerEvents(new UpdateMinionHealth(), this);

        getServer().getPluginManager().registerEvents(new AddMinionDeathPoints(), this);
        getServer().getPluginManager().registerEvents(new AddBossHitPoints(), this);

        getServer().getPluginManager().registerEvents(new PillarBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new PillarPurgerEvent(), this);

        getServer().getPluginManager().registerEvents(new PillarTool(), this);
        getServer().getPluginManager().registerEvents(new BossSpawnTool(),  this);

        getServer().getPluginManager().registerEvents(new FireDemonAbilities(), this);
        getServer().getPluginManager().registerEvents(new UniversalBossAbilities(), this);
        getServer().getPluginManager().registerEvents(new PillarSpikes(), this);

        getServer().getPluginManager().registerEvents(new CancelBossDamage(), this);

        getServer().getPluginManager().registerEvents(new SwitchWorld(), this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new GoldenAppleCanceller(), this);

        getCommand("superbosses").setExecutor(new MainCommand());

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SuperBossesExpansion(this).register();
        }

        startCountdown();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEntities() {
        RegisterNMSEntity registerNMSEntity = new RegisterNMSEntity();
        registerNMSEntity.registerEntity("Brute", 62, EntityMagmaCube.class, NMSBruteEntity.class);
        registerNMSEntity.registerEntity("FireDemon", 53, EntityGiantZombie.class, FireDemon.class);
    }

    private void startCountdown() {
        new BukkitRunnable() {
            int counter = ThreadLocalRandom.current().nextInt(1440, 2880);
            @Override
            public void run() {
                counter--;
                if (counter == 30) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.ENDERMAN_STARE, 1, 1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou feel the ground shake... Prepare for battle"));
                    }
                }
                if (counter <= 1) {
                    counter = ThreadLocalRandom.current().nextInt(1440, 2880);
                    if (currentGame == null) {
                        if (Bukkit.getOnlinePlayers().size() >= 10) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sbs spawn");
                        } else {
                            Bukkit.broadcastMessage(ChatColor.RED + "There was not enough players online for the ZoneX event!");
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 200, 200);
    }
}
