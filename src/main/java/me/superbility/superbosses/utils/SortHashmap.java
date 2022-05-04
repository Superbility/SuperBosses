package me.superbility.superbosses.utils;

import java.util.*;
import java.util.stream.Collectors;

public class SortHashmap {
    public static LinkedHashMap<UUID, Integer> sortByValue(HashMap<UUID, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<UUID, Integer> > list =
                new LinkedList<Map.Entry<UUID, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<UUID, Integer> >() {
            public int compare(Map.Entry<UUID, Integer> o1,
                               Map.Entry<UUID, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue()); //(o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        LinkedHashMap<UUID, Integer> temp = new LinkedHashMap<UUID, Integer>();
        for (Map.Entry<UUID, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    public static LinkedHashMap<UUID, Integer> sortByValue(Map<UUID, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<UUID, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
}
