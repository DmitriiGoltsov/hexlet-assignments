package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {

    List<Integer> numbers1;
    List<Integer> numbers2;

    @BeforeEach
    void initIntegerList() {
        this.numbers1 = new ArrayList<>();
        this.numbers1.add(0);
        this.numbers1.add(2);
        this.numbers1.add(4);
        this.numbers1.add(5);
        this.numbers1.add(7);
        this.numbers1.add(9);
        this.numbers1.add(99);
        this.numbers1.add(121);
    }
    @BeforeEach
    void initIntegerEmptyList() {
        this.numbers2 = new ArrayList<>();
    }

    @Test
    void testTake1() {
        // BEGIN
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        expected.add(4);

        List<Integer> actual = App.take(numbers1, 3);
        Assertions.assertEquals(actual, expected);
        // END
    }

    @Test
    void testTake2() {
        List<Integer> expected2 = new ArrayList<>();

        List<Integer> actual2 = App.take(expected2, 5);
        Assertions.assertEquals(actual2, expected2);
    }

    @Test
    void testTake3() {
        List<Integer> expected3 = new ArrayList<>();
        expected3.addAll(0, numbers1);

        List<Integer> actual3 = App.take(expected3, 10);
        Assertions.assertEquals(expected3, actual3);
    }
}
