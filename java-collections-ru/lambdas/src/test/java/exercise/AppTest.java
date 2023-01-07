package exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

// BEGIN
class AppTest {
    String[][] image1 = {
            {"*", "*", "*", "*"},
            {"*", " ", " ", "*"},
            {"*", " ", " ", "*"},
            {"*", "*", "*", "*"},
    };

    String[][] emptyImage = {
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
            {"", "", "", ""},
    };

    String[][] differentSymbols = {
            {"*", "/", ".", ","},
            {"/", "*", ",", "."},
            {",", ".", "*", "/"},
            {".", ",", "/", "*"},
    };

    @Test
    void testEnlargeArrayImage1() {
        String[][] actual = App.enlargeArrayImage(image1);
        String[][] expected = {
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
        };
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void testEnlargeArrayImage2() {
        String[][] actual2 = App.enlargeArrayImage(emptyImage);
        String[][] expected2 = {
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""},
        };
        Assertions.assertEquals(actual2, expected2);
    }

    @Test
    void testEnlargeArrayImage3() {
        String[][] actual3 = App.enlargeArrayImage(differentSymbols);
        String[][] expected3 = {
                {"*", "*", "/", "/", ".", ".", ",", ","},
                {"*", "*", "/", "/", ".", ".", ",", ","},
                {"/", "/", "*", "*", ",", ",", ".", "."},
                {"/", "/", "*", "*", ",", ",", ".", "."},
                {",", ",", ".", ".", "*", "*", "/", "/"},
                {",", ",", ".", ".", "*", "*", "/", "/"},
                {".", ".", ",", ",", "/", "/", "*", "*"},
                {".", ".", ",", ",", "/", "/", "*", "*"},
        };
        Assertions.assertEquals(actual3, expected3);
    }
}
// END
