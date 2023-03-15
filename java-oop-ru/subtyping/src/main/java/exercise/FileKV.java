package exercise;

import java.util.Map;

import static exercise.Utils.readFile;
import static exercise.Utils.writeFile;
import static exercise.Utils.serialize;
import static exercise.Utils.unserialize;


// BEGIN
class FileKV implements KeyValueStorage {

    private String filePath;

    public FileKV(String filePath, Map<String, String> dictionary) {
        this.filePath = filePath;
        dictionary.entrySet().stream()
                .forEach(entry -> set(entry.getKey(), entry.getValue()));
    }

    @Override
    public void set(String key, String value) {
        String content = readFile(this.filePath);
        Map<String, String> data = unserialize(content);
        data.put(key, value);
        writeFile(filePath, serialize(data));
    }

    @Override
    public void unset(String key) {
        String content = readFile(this.filePath);
        Map<String, String> data = unserialize(content);
        data.remove(key);
        writeFile(filePath, serialize(data));
    }

    @Override
    public String get(String key, String defaultValue) {
        String content = readFile(this.filePath);
        Map<String, String> data = unserialize(content);
        return data.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        String content = readFile(this.filePath);
        Map<String, String> data = unserialize(content);
        return data;
    }
}
// END
