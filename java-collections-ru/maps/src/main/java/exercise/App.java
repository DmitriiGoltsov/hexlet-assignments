package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
class App {
    public static Map<String, Integer> getWordCount(String sentence) {
        Map<String, Integer> words = new HashMap<>();
        if (sentence.equals("")) {
            return words;
        }
        String[] wordsIfSentence = sentence.split(" ");
        for (var i = 0; i < wordsIfSentence.length; i++) {
            int numbersCount = 0;
            for (var j = 0; j < wordsIfSentence.length; j++) {
                if (wordsIfSentence[i].equals(wordsIfSentence[j])) {
                    numbersCount += 1;
                }
            }
            words.put(wordsIfSentence[i], numbersCount);
        }
        return words;
    }
    public static String toString(Map<String, Integer> words) {
        var keys = words.keySet();
        if (words.size() == 0) {
            return "{}";
        }
        var result = new StringBuilder();
        result.append("{");
        System.out.println(keys);
        for (var key : keys) {
            result.append("\n  " + key + ": " + words.get(key));
        }
        result.append("\n}");
        System.out.println(result);
        return result.toString();
    }
}
//END
