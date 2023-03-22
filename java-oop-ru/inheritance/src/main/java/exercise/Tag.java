package exercise;

import java.util.stream.Collectors;
import java.util.Map;

// BEGIN
class Tag {
    private String tagName;
    private Map<String, String> tagAttribute;

    public Tag(String tagName, Map<String, String> tagAttribute) {
        this.tagName = tagName;
        this.tagAttribute = tagAttribute;
    }

    public String getTagName() {
        return tagName;
    }

    public Map<String, String> getTagAttribute() {
        return tagAttribute;
    }

    public String makeAttributesAsString() {
        String result = this.tagAttribute.keySet().stream()
                .map(key -> {
                    String value = tagAttribute.get(key);
                    return String.format(" %s=\"%s\"", key, value);
                })
                .collect(Collectors.joining(""));

    return result;
    }
}
// END
