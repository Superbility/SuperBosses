package me.superbility.superbosses.listeners.gameevents.pillarevents;

import me.superbility.superbosses.events.pillarhitevent.PillarHitEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class PillarSpikes implements Listener {
    @EventHandler
    private void onHit(PillarHitEvent e) {
        int random = ThreadLocalRandom.current().nextInt(1, 10);
        if(random == 1) {
            Player player = e.getPlayer();
            player.damage(1, e.getPillar().getEntity()); //TODO DEATH MESSAGES

            Vector launchDirection = player.getLocation().getDirection().multiply(-2);
            launchDirection.setY(1);
            player.setVelocity(launchDirection);

            player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
        }
    }
}
