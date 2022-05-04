package me.superbility.superbosses.data;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.PlayerDataConfig;
import me.superbility.superbosses.data.boss.Boss;
import me.superbility.superbosses.data.boss.BossType;
import me.superbility.superbosses.data.pillar.Pillar;
import com.superdevelopment.superbosses.utils.*;
import me.superbility.superbosses.utils.*;
import me.superbility.superbosses.utils.centeredtext.CenteredText;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Game {
    private Main plugin = Main.getPlugin(Main.class);
    private PlayerDataConfig pdc = new PlayerDataConfig();

    private Boss boss;
    private List<Pillar> pillars;
    private HashMap<UUID, Integer> playerPoints;
    private List<LivingEntity> spawnedMinions;
    private int unbrokenPillars;
    private Player finishingPlayer;
    private RewardType rewardType;

    public Game(BossType type) {
        this.boss = new Boss(type);
        this.pillars = new ArrayList<>();
        this.playerPoints = new HashMap<>();
        this.spawnedMinions = new ArrayList<>();
        setupCage();
        createDefaultBorder();
        spawnPillars(type);
        spawnMinions();
        this.unbrokenPillars = this.pillars.size();

        startGameTimer();

        List<String> start = plugin.getConfig().getStringList("Messages.GameStart");
        for(String s : start) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
    }

    public Boss getBoss() {
        return boss;
    }
    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public List<Pillar> getPillars() {
        return pillars;
    }
    public void addPillar(Pillar pillar) {
        this.pillars.add(pillar);
    }
    public void removePillar(Pillar pillar) {
        this.pillars.remove(pillar);
    }

    public HashMap<UUID, Integer> getPlayerPoints() {
        return playerPoints;
    }
    public void setPlayerPoints(HashMap<UUID, Integer> playerPoints) {
        this.playerPoints = playerPoints;
    }
    public void addPlayerPoints(UUID uuid, int amount, String reason) {
        if(!this.playerPoints.containsKey(uuid)) this.playerPoints.put(uuid, 0);
        int oldAmount = this.playerPoints.get(uuid);
        int newAmount = oldAmount + amount;
        this.playerPoints.replace(uuid, oldAmount, newAmount);

        Player player = Bukkit.getPlayer(uuid);
        SendActionbar.sendActionText(player, ChatColor.translateAlternateColorCodes('&',
                "&b+&3" + amount + " &bpoints &3(&b" + reason + "&3)      &bTotal - &3" + newAmount));

        int currentAmount = pdc.getConfig().getInt(uuid + ".TotalPoints");
        pdc.getConfig().set(uuid + ".TotalPoints", currentAmount + amount);
        pdc.saveFile();
    }

    public void setFinishingPlayer(Player player) {
        this.finishingPlayer = player;
    }

    public List<LivingEntity> getMinions() {
        return this.spawnedMinions;
    }
    public void removeMinion(LivingEntity ent) {
        this.spawnedMinions.remove(ent);
    }
    public void removeAllMinions() {
        this.spawnedMinions.clear();
    }

    public void removePillar() {
        this.unbrokenPillars--;
        if(this.unbrokenPillars <= 0) startBossFight();
    }
    public int getUnbrokenPillars() {
        return this.unbrokenPillars;
    }

    private void startGameTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(plugin.currentGame.equals(this)) {
                    endBossFight();
                    List<String> timeout = plugin.getConfig().getStringList("Messages.GameTimeout");
                    for(String s : timeout) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', s));
                    }
                }
            }
        }.runTaskLater(plugin, plugin.getConfig().getInt("BossFight.Timeout") * 20);
    }

    private void setupCage() {
        String schematic = boss.getType().getCageSchematic();
        Location location = boss.getBossSpawnLocation();
        Schematic.paste(schematic, location);
        for(int i = 0; i < 30; ++i) {
            Block block = location.clone().add(0, i, 0).getBlock();
            if(block.getType() == Material.SPONGE) {
                block.setType(Material.AIR);
                teleportBoss(block.getLocation().clone().add(0.5, 0, 0));
            }
        }
    }
    public void teleportBoss(Location location) {
        LivingEntity bossEntity = boss.getEntity();
        Entity slime = boss.getSlime();
        Entity healthBar = boss.getHealthBar();

        slime.eject();
        bossEntity.eject();

        bossEntity.teleport(location);

        slime.setPassenger(healthBar);
        bossEntity.setPassenger(slime);
    }
    private void createDefaultBorder() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getWorld().equals(boss.getEntity().getWorld())) {
                createBorder(p);
            }
        }
    }
    public void createBorder(Player player) {
        WorldBorder wb = new WorldBorder();
        wb.world = ((CraftWorld) player.getWorld()).getHandle();
        wb.setSize(9);
        Location location = boss.getBossSpawnLocation();
        wb.setCenter(location.getX(), location.getZ());
        wb.setDamageAmount(0);

        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(wb, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }
    public void removeBorder(Player player) {
        WorldBorder wb = new WorldBorder();
        wb.world = ((CraftWorld) player.getWorld()).getHandle();
        wb.setSize(300000000);
        Location location = boss.getBossSpawnLocation();
        wb.setCenter(location.getX(), location.getZ());
        wb.setDamageAmount(0);

        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(wb, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }
    private void spawnPillars(BossType type) {
        for(String s : plugin.getConfig().getStringList("BossFight.Locations.PillarSpawns")) {
            Location loc = LocationSerialiser.getLiteLocationFromString(s);
            Pillar pillar = new Pillar(type, loc);
            addPillar(pillar);
        }
    }
    private void spawnMinions() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Pillar p : pillars) {
                    if(unbrokenPillars <= 0) {
                        cancel();
                    }
                    if(!p.isDestroyed()) {
                        if(spawnedMinions.size() < 175) {
                            LivingEntity minion = p.spawnMinion();
                            spawnedMinions.add(minion);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 5, 5);
    }
    private void localisedDamage() {
        new BukkitRunnable() {
            @Override
            public void run() {
                LivingEntity bossEntity = boss.getEntity();
                if(bossEntity == null || bossEntity.isDead()) cancel();
                for(Entity e : boss.getEntity().getNearbyEntities(6, 6, 6)) {
                    if(e instanceof Player) {
                        ((Player) e).damage(4, boss.getEntity());
                    }
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    public void startBossFight() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            Conversation.startConversation(plugin.getConfig().getStringList("Messages.ShieldDestructionConvo"), p, 60, Sound.VILLAGER_HIT, boss.getType());
            removeBorder(p);
        }
        new BukkitRunnable() {
            Location spawnLocation =  boss.getBossSpawnLocation();
            Location topLoc = new Location(spawnLocation.getWorld(),
                    spawnLocation.getX(), spawnLocation.getWorld().getHighestBlockYAt(spawnLocation) + 1, spawnLocation.getZ());
            @Override
            public void run() {
                Schematic.paste("barrier", topLoc);
                topLoc.setY(topLoc.getY() - 1);
                if(topLoc.getY() <= spawnLocation.getY()) {
                    boss.getEntity().removePotionEffect(PotionEffectType.SLOW);
                    cancel();
                }
                if(topLoc.getY() <= spawnLocation.clone().getY() + 1) {
                    boss.getEntity().setVelocity(new Vector(0, 2, 0));
                }

            }
        }.runTaskTimer(plugin, 20, 20);
        localisedDamage();
    }
    public void endBossFight() {
        for(Pillar p : this.pillars) {
            p.remove();
        }
        for(LivingEntity e : this.spawnedMinions) {
            e.remove();
        }
        boss.remove();

        this.rewardType = RewardType.getRandomReward();

        sendLeaderboardMessage();
        giveRewards();

        plugin.currentGame = null;

        int currentAmount = pdc.getConfig().getInt(finishingPlayer.getUniqueId() + ".BossesKilled");
        pdc.getConfig().set(finishingPlayer.getUniqueId() + ".BossesKilled", currentAmount + 1);
        pdc.saveFile();
    }
    private void sendLeaderboardMessage() {
        List<String> leaderboard = plugin.getConfig().getStringList("Messages.Leaderboard");
        LinkedHashMap<UUID,Integer> leaderboardPositions = SortHashmap.sortByValue(playerPoints, false);

        UUID player1Uuid = GetHashmapValue.getValue(leaderboardPositions, 0);
        UUID player2Uuid = GetHashmapValue.getValue(leaderboardPositions, 1);
        UUID player3Uuid = GetHashmapValue.getValue(leaderboardPositions, 2);

        OfflinePlayer player1 = null;
        OfflinePlayer player2 = null;
        OfflinePlayer player3 = null;

        if(player1Uuid != null) player1 = Bukkit.getOfflinePlayer(player1Uuid);
        if(player2Uuid != null) player2 = Bukkit.getOfflinePlayer(player2Uuid);
        if(player3Uuid != null) player3 = Bukkit.getOfflinePlayer(player3Uuid);

        List<String> message = new ArrayList<String>();
        for(String s : leaderboard) {
            if(finishingPlayer != null) s = s.replace("{player_final}", finishingPlayer.getName());

            if(player1 != null) {
                s = s.replace("{player_damager_1}", player1.getName());
                s = s.replace("{player_damage_1}", String.valueOf(leaderboardPositions.get(player1.getUniqueId())));
            } else {
                s = s.replace("{player_damager_1}", "None!");
                s = s.replace("{player_damage_1}", "0");
            }

            if(player2 != null) {
                s = s.replace("{player_damager_2}", player2.getName());
                s = s.replace("{player_damage_2}", String.valueOf(leaderboardPositions.get(player2.getUniqueId())));
            } else {
                s = s.replace("{player_damager_2}", "None!");
                s = s.replace("{player_damage_2}", "0");
            }

            if(player3 != null) {
                s = s.replace("{player_damager_3}", player3.getName());
                s = s.replace("{player_damage_3}", String.valueOf(leaderboardPositions.get(player3.getUniqueId())));
            } else {
                s = s.replace("{player_damager_3}", "None!");
                s = s.replace("{player_damage_3}", "0");
            }

            s = s.replace("{reward_type}", this.rewardType.getName());

            message.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(String.valueOf(leaderboardPositions));
            for(String s : message) {
                if(leaderboardPositions.containsKey(player.getUniqueId())) {
                    CenteredText.sendCenteredMessage(player, s
                            .replace("{target_damage}", String.valueOf(leaderboardPositions.get(player.getUniqueId())))
                            .replace("{target_position}", String.valueOf(GetHashmapValue.getValue(leaderboardPositions, player.getUniqueId()) + 1)
                            ));
                } else {
                    CenteredText.sendCenteredMessage(player, s
                            .replace("{target_damage}", "0")
                            .replace("{target_position}", "N/A")
                            );
                }
            }
        }
    }
    private void giveRewards() {
        HashMap<UUID,Integer> leaderboardPositions = SortHashmap.sortByValue(playerPoints);
        for(UUID uuid : leaderboardPositions.keySet()) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.rewardType.getCommandFormatted(player, leaderboardPositions.get(uuid)));

            for(String s : plugin.getConfig().getStringList("Rewards.Global")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{player}", player.getName()));
            }

            if(player.isOnline()) {
                Player p = (Player) player;
                p.sendMessage(ChatColor.GREEN + "Your rewards have been successfully added to your account!");
            }
        }
    }
}
