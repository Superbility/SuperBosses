package me.superbility.superbosses.data.boss;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BossArmor {
    public enum BossArmorType {
        HELMET, CHESTPLATE, LEGGINGS, BOOTS;
    }
    public static ItemStack getHelmet(BossType type) {
        switch(type) {
            case FIREDEMON: return new ItemStack(Material.NETHER_BRICK);
        }
        return null;
    }
    public static ItemStack setArmour(BossArmorType armourType, BossType type) {
        ItemStack item = new ItemStack(getArmourType(armourType));
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1000, true);
        meta.setColor(getArmorColor(type));
        item.setItemMeta(meta);
        return item;
    }
    public static Material getArmourType(BossArmorType armorType) {
        if(armorType.equals(BossArmorType.BOOTS)) {
            return Material.LEATHER_BOOTS;
        } else if (armorType.equals(BossArmorType.LEGGINGS)) {
            return Material.LEATHER_LEGGINGS;
        } else if (armorType.equals(BossArmorType.CHESTPLATE)) {
            return Material.LEATHER_CHESTPLATE;
        }
        return null;
    }
    public static Color getArmorColor(BossType type) {
        switch(type) {
            case FIREDEMON: return Color.ORANGE;
        }
        return null;
    }
    public NBTItem getNbtTag(BossType type, NBTItem item) {
        switch(type) {
            case FIREDEMON: item.setString("type", "FIREDEMON");
        }
        return null;
    }
}
