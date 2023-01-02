package exercise;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

// BEGIN
class App {
    public static List<Map<String, String>> findWhere(List<Map<String, String>> books,
                                                      Map<String, String> searchedBooks) {

        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> book : books) {
            boolean test = false;
            for (Map.Entry<String, String> searchedBook : searchedBooks.entrySet()) {
                if (book.containsKey(searchedBook.getKey())) {
                    if (book.containsValue(searchedBook.getValue())) {
                        test = true;
                    } else {
                        test = false;
                        break;
                    }
                }
            }
            if(test) {
                result.add(book);
            }
        }
        return result;
    }
}
//END
