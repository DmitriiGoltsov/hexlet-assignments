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

    List<Integer> numbers;

    @BeforeEach
    void initIntegerList() {
        this.numbers = new ArrayList<>();
        this.numbers.add(0);
        this.numbers.add(2);
        this.numbers.add(4);
        this.numbers.add(5);
        this.numbers.add(7);
        this.numbers.add(9);
        this.numbers.add(99);
        this.numbers.add(121);
    }

    @Test
    void testTake() {
        // BEGIN
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(2);
        expected.add(4);

        List<Integer> actual = App.take(numbers, 3);
        Assertions.assertEquals(actual, expected);
        // END
    }
}
