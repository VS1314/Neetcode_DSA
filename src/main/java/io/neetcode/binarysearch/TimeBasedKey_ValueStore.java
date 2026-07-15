package io.neetcode.binarysearch;

import java.util.*;

public class TimeBasedKey_ValueStore {

    class TimeMap {

        private Map<String, List<TimeData>> map;

        class TimeData {

            int timestamp;
            String value;

            public TimeData(int timestamp, String value) {
                this.timestamp = timestamp;
                ;
                this.value = value;
            }
        }

        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            TimeData data = new TimeData(timestamp, value);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(data);
        }

        public String get(String key, int timestamp) {
            if (!map.containsKey(key))
                return "";
            List<TimeData> data = map.get(key);
            int l = 0, r = data.size() - 1;
            String ans = "";
            while (l <= r) {
                int m = l + (r - l) / 2;
                TimeData curr = data.get(m);
                if (curr.timestamp <= timestamp) {
                    ans = curr.value;
                    l = m + 1;
                } else
                    r = m - 1;
            }
            return ans;

        }
    }

    /**
     * Your TimeMap object will be instantiated and called as such:
     * TimeMap obj = new TimeMap();
     * obj.set(key,value,timestamp);
     * String param_2 = obj.get(key,timestamp);
     */

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

        public void set(String key, String value, int timestamp) {// O(1)
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(new Pair(timestamp, value));
        }

        public String get(String key, int timestamp) {// O(log n)
            if (!map.containsKey(key))
                return "";
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

        public void set(String key, String value, int timestamp) {// O(log n)
            map.putIfAbsent(key, new TreeMap<>());
            map.get(key).put(timestamp, value);
        }

        public String get(String key, int timestamp) {// O(log n)
            if (!map.containsKey(key))
                return "";
            TreeMap<Integer, String> tmap = map.get(key);
            Integer t = tmap.floorKey(timestamp);
            if (t == null)
                return "";
            return tmap.get(t);
        }
    }

}
