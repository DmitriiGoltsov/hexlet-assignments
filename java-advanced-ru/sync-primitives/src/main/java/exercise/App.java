package exercise;

class App {

    public static void main(String[] args) {
        // BEGIN
        SafetyList list = new SafetyList();

        ListThread thread1 = new ListThread(list);
        ListThread thread2 = new ListThread(list);

        thread1.setName("First thread");
        thread2.setName("Second thread");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The number of added elements in the list is " + list.getSize());
        // END
    }
}

