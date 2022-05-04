package me.superbility.superbosses.events.pillarhitevent;

import me.superbility.superbosses.data.Game;
import me.superbility.superbosses.data.pillar.Pillar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PillarHitEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Pillar pillar;
    private final Player pillarHitter;
    private final Game game;
    private double damage;
    private EntityDamageByEntityEvent event;

    public PillarHitEvent(Pillar pillar, Player hitter, Game game, double damage, EntityDamageByEntityEvent event) {
        this.pillarHitter = hitter;
        this.pillar = pillar;
        this.game = game;
        this.damage = damage;
        this.event = event;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    public Pillar getPillar() {
        return this.pillar;
    }
    public Player getPlayer() {
        return this.pillarHitter;
    }
    public Game getGame() {
        return this.game;
    }
    public double getDamage() {
        return this.damage;
    }
    public EntityDamageByEntityEvent getEvent() {
        return this.event;
    }
}
