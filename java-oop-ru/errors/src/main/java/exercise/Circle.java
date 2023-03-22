package exercise;

// BEGIN
class Circle {

    private int radius;
    private Point point;

    public Circle(Point point, int radius) {
        this.point = point;
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public double getSquare() throws NegativeRadiusException {
        if (this.radius < 0) {
            throw new NegativeRadiusException("Error! Radius is less than 0.");
        }

        return radius * radius * Math.PI;
    }
}
// END
