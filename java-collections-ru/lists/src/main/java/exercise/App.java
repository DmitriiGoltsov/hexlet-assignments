package exercise;

import java.util.ArrayList;
import java.util.List;

// BEGIN
class App {
    public static boolean scrabble(String packOfLetters, String word) {

        String loweredLetters = packOfLetters.toLowerCase();
        String loweredWord = word.toLowerCase();

        List<String> listOfLetters = new ArrayList<>();
        for (var i = 0; i <= packOfLetters.length() - 1; i++) {
            listOfLetters.add(String.valueOf(loweredLetters.charAt(i)));
        }

        List<String> listOfTheWordLetters = new ArrayList<>();
        for (var i2 = 0; i2 <= word.length() - 1; i2++) {
            listOfTheWordLetters.add(String.valueOf(loweredWord.charAt(i2)));
        }

        if (listOfLetters.size() < listOfTheWordLetters.size()) {
            return false;
        }

        for (var i3 = 0; i3 <= listOfLetters.size() - 1; i3++) {
            for (var i4 = 0; i4 <= listOfTheWordLetters.size() - 1; i4++) {
                if (listOfLetters.get(i3).equals(listOfTheWordLetters.get(i4))) {
                    listOfTheWordLetters.remove(i4);
                    if (listOfTheWordLetters.size() == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

// END
