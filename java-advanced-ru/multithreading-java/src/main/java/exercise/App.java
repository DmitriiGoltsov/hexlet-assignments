package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {
        Map<String, Integer> result = new HashMap<>();

        MinThread minThread = new MinThread(numbers);
        MaxThread maxThread = new MaxThread(numbers);
        minThread.setName("MinThread");
        maxThread.setName("MaxThread");

        minThread.start();
        LOGGER.log(Level.ALL, "Thread " + minThread.getName() + " has started");

        maxThread.start();
        LOGGER.log(Level.ALL, "Thread " + maxThread.getName() + " has started");


        try {
            minThread.join();
            maxThread.join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Thread was interrupted!");
            throw new RuntimeException(e);
        }

        Integer min = minThread.getMin();
        Integer max = maxThread.getMax();

        result.put("min", min);
        result.put("max", max);

        return result;
    }
    // END
}
