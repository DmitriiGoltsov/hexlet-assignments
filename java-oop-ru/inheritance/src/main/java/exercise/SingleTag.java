package exercise;

import java.util.Map;

// BEGIN
class SingleTag extends Tag {

    public SingleTag(String tagName, Map<String, String> tagAttribute) {
        super(tagName, tagAttribute);
    }

    @Override
    public String toString() {
        return String.format("<%s%s>", getTagName(), makeAttributesAsString());
    }
}
// END
