package me.superbility.superbosses.events.minionhitevent;

import me.superbility.superbosses.data.minion.MinionType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinionHitEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Entity entity;
    private final Player player;
    private final MinionType type;
    private boolean isCancelled;
    private double damage;

    public MinionHitEvent(Entity entity, Player player, MinionType type, double damage) {
        this.entity = entity;
        this.player = player;
        this.type = type;
        this.damage = damage;
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
    public Entity getEntity() {return entity;}
    public Player getPlayer() {return player;}
    public MinionType getType() {
        return type;
    }
    public double getDamage() {
        return damage;
    }
}
