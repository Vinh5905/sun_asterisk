// Requirement 3: Create a Circle subclass that inherits from Shape.
public class Circle extends Shape {
    public Circle(double diameter) {
        super(diameter, diameter);
    }

    public double getRadius() {
        return getWidth() / 2;
    }

    // Calculate the area of the circle: π * radius².
    public double getArea() {
        double radius = getRadius();
        return Math.PI * radius * radius;
    }

    // Calculate the circumference of the circle: diameter * 3.14.
    public double getCircumference() {
        return getWidth() * 3.14;
    }

    // Display the circle information.
    @Override
    public void displayInfo() {
        System.out.println("Circle diameter: " + getWidth());
        System.out.println("Circle radius: " + getRadius());
        System.out.println("Circle area: " + getArea());
        System.out.println("Circle circumference: " + getCircumference());
    }
}
