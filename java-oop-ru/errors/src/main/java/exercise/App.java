package exercise;

// BEGIN
class App {

    public static void printSquare(Circle circle) throws NegativeRadiusException {

        try {
            int result = (int) Math.round(circle.getSquare());
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Не удалось посчитать площадь");
        } finally {
            System.out.println("Вычисление окончено");
        }
    }
}
// END
