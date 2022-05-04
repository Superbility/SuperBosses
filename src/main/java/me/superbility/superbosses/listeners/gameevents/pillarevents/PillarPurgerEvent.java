package me.superbility.superbosses.listeners.gameevents.pillarevents;

import me.superbility.superbosses.events.pillarhitevent.PillarHitEvent;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PillarPurgerEvent implements Listener {
    private PillarBreakEvent pillarBreakEvent = new PillarBreakEvent();

    @EventHandler
    private void onHit(PillarHitEvent e) {
        if(e.getPlayer().getItemInHand().getType() != Material.AIR) {
            ItemStack item = e.getPlayer().getItemInHand();
            NBTItem nbtItem = new NBTItem(item);
            if(nbtItem.hasKey("sbsItem") && nbtItem.getString("sbsItem").equals("PILLAR_PURGER")) {
                pillarBreakEvent.breakPillar(e.getPillar(), e.getPlayer());
                e.getPlayer().setItemInHand(null);
            }
        }
    }
}
