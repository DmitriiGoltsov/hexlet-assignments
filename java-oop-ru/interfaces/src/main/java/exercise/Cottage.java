package exercise;

// BEGIN
public class Cottage implements Home {

    private final double area;
    private final double floorCount;

    public Cottage(double area, double floorCount) {
        this.area = area;
        this.floorCount = floorCount;
    }

    @Override
    public double getArea() {
        return this.area;
    }

    @Override
    public int compareTo(Home another) {
        return Double.compare(this.getArea(), another.getArea());
    }

    public double getFloorCount() {
        return floorCount;
    }

    public String toString() {
        return (int) this.getFloorCount() + " этажный коттедж площадью "
                + this.getArea() + " метров";
    }
}
// END
