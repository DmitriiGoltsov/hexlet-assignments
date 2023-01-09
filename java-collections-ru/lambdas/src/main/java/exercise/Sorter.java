package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class Sorter {
    public static List<String> takeOldestMans(List<Map<String, String>> users) {
        return users.stream()
                .filter(user -> user.get("gender").equals("male"))
                .sorted((user1, user2) -> user1.get("birthday").compareTo(user2.get("birthday")))
                .map(user -> user.get("name"))
                .collect(Collectors.toList());
    }
}
// END
