package exercise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// BEGIN
class App {
    public static Map<String, String> genDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Map<String, String> result = new LinkedHashMap<>();
        Set<String> keySet = new TreeSet<>(data1.keySet());
        keySet.addAll(data2.keySet());

        if (data1 == null && data2 == null) {
            return result;
        } else if (!data1.isEmpty() && data1.isEmpty()) {
            for (String element : keySet) {
                result.put(element, "deleted");
                return result;
            }
        } else if (data1.isEmpty() && !data2.isEmpty()) {
            for (String element : keySet) {
                result.put(element, "added");
                return result;
            }
        }
        for (String key : keySet) {
            if (data1.containsKey(key) && data2.containsKey(key) && data1.get(key).equals(data2.get(key))) {
                result.put(key, "unchanged");
            } else if (data1.containsKey(key) && data2.containsKey(key) && !(data1.get(key).equals(data2.get(key)))) {
                result.put(key, "changed");
            } else if (data1.containsKey(key) && !(data2.containsKey(key))) {
                result.put(key, "deleted");
            } else if (!(data1.containsKey(key)) && data2.containsKey(key)) {
                result.put(key, "added");
            }
        }
        return result;
    }
}
//END
