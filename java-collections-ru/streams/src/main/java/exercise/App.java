package exercise;

import java.util.List;
// BEGIN
public class App {
    public static long getCountOfFreeEmails(List<String> emails) {
        List<String> freeEmails = List.of("yandex.ru", "gmail.com", "hotmail.com");
        return emails.stream()
                .map(email -> email.split("@")[1])
                .filter(email -> freeEmails.contains(email))
                .count();
    }
}
// END
