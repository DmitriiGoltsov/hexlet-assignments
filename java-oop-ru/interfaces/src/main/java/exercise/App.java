package exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
class App {

    public static List<String> buildApartmentsList(List<Home> list, int n) {

        List<String> sortedList = list.stream()
                .sorted(Comparator.comparingDouble(Home::getArea))
                .map(String::valueOf)
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();

        if (n > sortedList.size()) {
            return sortedList;
        }

        for (int i = 0; i < n; i++) {
            result.add(sortedList.get(i));
        }

        return result;
    }

}
// END
