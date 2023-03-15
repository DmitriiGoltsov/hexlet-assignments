package exercise;

import java.util.Map;
import java.util.HashMap;

// BEGIN
class InMemoryKV implements KeyValueStorage {

    private Map<String, String> dictionary;

    public InMemoryKV(Map<String, String> dictionary) {
        this.dictionary = new HashMap<>(dictionary);
    }

    @Override
    public void set(String key, String value) {
        this.dictionary.put(key, value);
    }

    @Override
    public void unset(String key) {
        this.dictionary.remove(key);
    }

    @Override
    public String get(String key, String defaultValue) {

        if (!this.dictionary.containsKey(key)) {
            return defaultValue;
        }

        return this.dictionary.get(key);
    }

    @Override
    public Map<String, String> toMap() {
        return new HashMap<>(dictionary);
    }
}
// END
