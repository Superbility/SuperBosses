package me.superbility.superbosses.events.bosshitevent;

import me.superbility.superbosses.data.boss.Boss;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BossHitEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Boss boss;
    private final Player player;
    private boolean isCancelled;

    public BossHitEvent(Boss boss, Player player) {
        this.boss = boss;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    public Boss getBoss() {return boss;}
    public Player getPlayer() {return player;}
}
