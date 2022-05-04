package me.superbility.superbosses.data.minion.entities.nms;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.MagmaCube;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

public class NMSBruteEntity extends EntityMagmaCube {
    public NMSBruteEntity(org.bukkit.World world) {
        super(((CraftWorld) world).getHandle());

        try {
            Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);

            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalLeapAtTarget(this, 1.0F));

        this.targetSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 1.0F));

        this.targetSelector.a(1, new PathfinderGoalTargetNearestPlayer(this));
    }
    public static MagmaCube spawn(Location loc){
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final NMSBruteEntity customEnt = new NMSBruteEntity(loc.getWorld());
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false); //Do we want to remove it when the NPC is far away? I won
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (MagmaCube) customEnt.getBukkitEntity();
    }
}
