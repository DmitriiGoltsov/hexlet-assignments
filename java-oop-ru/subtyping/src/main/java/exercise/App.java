package exercise;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// BEGIN
class App {

    public static void swapKeyValue(KeyValueStorage dictionary) {

        Iterator<Map.Entry<String, String>> it = dictionary.toMap().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            dictionary.set(pair.getValue(), pair.getKey());
            dictionary.unset(pair.getKey());
        }
    }
}
// END
