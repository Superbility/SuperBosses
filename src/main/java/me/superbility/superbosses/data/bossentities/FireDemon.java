package me.superbility.superbosses.data.bossentities;

import me.superbility.superbosses.Main;
import me.superbility.superbosses.data.boss.BossArmor;
import me.superbility.superbosses.data.boss.BossType;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class FireDemon extends EntityGiantZombie {
    private static Main plugin = Main.getPlugin(Main.class);

    public FireDemon(World world) {
        super(world);

        List goalB = (List)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalRandomStroll(this, 3));
        this.goalSelector.a(4, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(5, new PathfinderGoalRandomLookaround(this));
        //this.goalSelector.a(1, new PathfinderGoalAvoidTarget(this, EntityHuman.class, 8.0F, 0.6D, 0.6D));
        this.targetSelector.a(2, new PathfinderGoalTargetNearestPlayer(this));

        addToMaps(FireDemon.class, "FireDemon", 53);
    }

    public static Giant spawn(Location loc){
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final FireDemon customEnt = new FireDemon(mcWorld);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false); //Do we want to remove it when the NPC is far away? I won
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);

        BossType type = BossType.FIREDEMON;
        
        Giant giant = (Giant) customEnt.getBukkitEntity();
        giant.setCustomName("FireDemon");
        giant.setCustomNameVisible(false);
        giant.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000000, 2, true));
        //giant.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000, 3, true));
        giant.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000, 100, true));
        giant.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000000, 100, true));
        giant.setMaxHealth(1000);
        giant.setHealth(1000);

        giant.getEquipment().setHelmet(BossArmor.getHelmet(type));
        giant.getEquipment().setChestplate(BossArmor.setArmour(BossArmor.BossArmorType.CHESTPLATE, type));
        giant.getEquipment().setLeggings(BossArmor.setArmour(BossArmor.BossArmorType.LEGGINGS, type));
        giant.getEquipment().setBoots(BossArmor.setArmour(BossArmor.BossArmorType.BOOTS, type));

        giant.setMetadata("boss",new FixedMetadataValue(plugin, true));

        Slime slime = (Slime) giant.getWorld().spawnEntity(giant.getLocation(), EntityType.SLIME);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 10, false, false), true);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 10000, false, false), true);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999999, 1000, false,  false), true);
        slime.setMaxHealth(1000);
        slime.setHealth(1000);
        slime.setSize(-1);
        slime.setMetadata("invincible", new FixedMetadataValue(plugin, true));
        slime.setCustomName("FireDemon.Slime");
        slime.setCustomNameVisible(false);

        ArmorStand stand = (ArmorStand) giant.getLocation().getWorld().spawnEntity(giant.getLocation(), EntityType.ARMOR_STAND);
        stand.setCustomName(type.getPrefix() + type.getMaxHealth());
        stand.setVisible(false);
        stand.setSmall(true);
        stand.setCustomNameVisible(false);

        slime.setPassenger(stand);
        giant.setPassenger(slime);
        
        return (Giant) customEnt.getBukkitEntity();
    }
    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }
    private static void addToMaps(Class clazz, String name, int id) {
        //getPrivateField is the method from above.
        //Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
        ((Map) getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map) getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        //((Map)getPrivateField("e", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
        ((Map) getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
        //((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }
}
