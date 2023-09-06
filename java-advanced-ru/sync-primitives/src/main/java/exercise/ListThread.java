package exercise;

import java.util.Random;

// BEGIN
public class ListThread extends Thread {

    private static final int NUMBER_OF_RANDOM_NUMBERS = 1000;

    private SafetyList safetyList;

    public ListThread(SafetyList list) {
        this.safetyList = list;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_RANDOM_NUMBERS; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
            safetyList.add(random.nextInt(10000));
        }
    }
}
// END
