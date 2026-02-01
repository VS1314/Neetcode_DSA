package io.neetcode.binarysearch;

import java.util.*;

public class TimeBasedKey_ValueStore {

    class TimeMap {

        private Map<String, List<Pair>> map;

        private static class Pair {
            int timestamp;
            String value;

            Pair(int timestamp, String value) {
                this.timestamp = timestamp;
                this.value = value;
            }
        }

        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {//O(1)
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(new Pair(timestamp, value));
        }

        public String get(String key, int timestamp) {//O(log n)
            if (!map.containsKey(key)) return "";
            List<Pair> pair = map.get(key);
            int l = 0, r = pair.size() - 1;
            String ans = "";
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (pair.get(mid).timestamp <= timestamp) {
                    ans = pair.get(mid).value;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return ans;
        }
    }


    class TimeMap {

        private Map<String, TreeMap<Integer, String>> map;

        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {//O(log n)
            map.putIfAbsent(key, new TreeMap<>());
            map.get(key).put(timestamp, value);
        }

        public String get(String key, int timestamp) {//O(log n)
            if (!map.containsKey(key)) return "";
            TreeMap<Integer, String> tmap = map.get(key);
            Integer t = tmap.floorKey(timestamp);
            if (t == null) return "";
            return tmap.get(t);
        }
    }

}
