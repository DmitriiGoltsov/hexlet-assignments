package exercise;

// BEGIN
public class MinThread extends Thread {

    private int[] numbers;
    private int min;

    public MinThread(int[] numbers) {
        this.numbers = numbers;
    }

    public int getMin() {
        return min;
    }

    @Override
    public void run() {
        min = numbers[0];
        for (int number : numbers) {
            if (number < min) {
                min = number;
            }
        }
    }
}
// END
