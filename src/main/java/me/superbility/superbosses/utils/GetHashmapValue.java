package me.superbility.superbosses.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GetHashmapValue {
    public static UUID getValue(HashMap<UUID, Integer> map, int index) {
        try {
            return (UUID) map.keySet().toArray()[index];
        } catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    public static int getValue(HashMap<UUID, Integer> map, UUID uuid) {
        int count = 0;
        for(UUID u : map.keySet()) {
            count--;
            if(u.equals(uuid)) {
                return count;
            }
        }
        return count;
    }
}
