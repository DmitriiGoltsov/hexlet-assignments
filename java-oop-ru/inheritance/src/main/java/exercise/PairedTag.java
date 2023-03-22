package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
class PairedTag extends Tag {
    private String tagBody;
    private List<Tag> childrenTag;


    public PairedTag(String tagName, Map<String, String> tagAttribute,
                     String tagBody, List<Tag> childrenTag) {
        super(tagName, tagAttribute);
        this.tagBody = tagBody;
        this.childrenTag = childrenTag;
    }

    public String getTagBody() {
        return tagBody;
    }

    public List<Tag> getChildrenTag() {
        return childrenTag;
    }

    @Override
    public String toString() {

        String attributes = makeAttributesAsString();
        String name = getTagName();
        String value = childrenTag.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));

        return String.format("<%s%s>%s%s</%s>", name, attributes, tagBody, value, name);

    }
}
// END
