package exercise;

// BEGIN
public class MaxThread extends Thread {

    private int[] numbers;
    private int max;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        max = numbers[0];
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
    }

    public int getMax() {
        return max;
    }
}
// END
