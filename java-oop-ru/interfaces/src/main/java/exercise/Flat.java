package exercise;

// BEGIN
public class Flat implements Home {

    private final double area;
    private final double balconyArea;
    private final double floor;

    public Flat(double area, double balconyArea, double floor) {
        this.area = area;
        this.balconyArea = balconyArea;
        this.floor = floor;
    }

    @Override
    public double getArea() {
        return this.area + this.balconyArea;
    }

    @Override
    public int compareTo(Home another) {
        return Double.compare(this.getArea(), another.getArea());
    }

    @Override
    public String toString() {
        return "Квартира площадью " + this.getArea() + " метров на "
                + (int) this.getFloor() + " этаже";
    }

    public double getFloor() {
        return floor;
    }
}
// END
