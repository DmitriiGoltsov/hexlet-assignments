package exercise;

class SafetyList {
    // BEGIN
    private static final int INITIAL_CAPACITY = 15;

    private int length;
    private int[] numbers;
    private int size;

    public SafetyList(int capacity) {
        this.numbers = new int[capacity];
        this.length = capacity;
        this.size = 0;
    }

    public SafetyList() {
        this.numbers = new int[INITIAL_CAPACITY];
        this.length = INITIAL_CAPACITY;
        this.size = 0;
    }

    public synchronized boolean add(int num) {
        if ((size + 1) >= length) {
            length = (length * 3) / 2 + 1;
            int[] newNumbers = new int[length];
            System.arraycopy(numbers, 0, newNumbers, 0, size);
            this.numbers = newNumbers;
        }
        numbers[++size] = num;
        return true;
    }

    public int get(int index) {
        return numbers[index];
    }

    public int getSize() {
        return this.size;
    }
    // END
}
