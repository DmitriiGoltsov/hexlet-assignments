package exercise;

import java.util.Map;

// BEGIN
class FileKV implements KeyValueStorage {

    private String filePath;
    private Map<String, String> dictionary;

    public FileKV(String filePath, Map<String, String> dictionary) {
        this.filePath = filePath;
        this.dictionary = dictionary;
    }

    @Override
    public void set(String key, String value) {



    }

    @Override
    public void unset(String key) {

    }

    @Override
    public String get(String key, String defaultValue) {
        if (!this.dictionary.containsKey(key)) {
            return "The FileKV does not contain the key: " + key;
        } else {
            return this.dictionary.get(key);
        }
    }

    @Override
    public Map<String, String> toMap() {
        return null;
    }
}
// END
