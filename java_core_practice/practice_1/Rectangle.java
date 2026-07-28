// Requirement 2: Create a Rectangle subclass that inherits from Shape.
public class Rectangle extends Shape {
    public Rectangle(double width, double height) {
        super(width, height);
    }

    // Calculate the area of the rectangle: width * height.
    public double getArea() {
        return getWidth() * getHeight();
    }

    // Calculate the perimeter of the rectangle: 2 * (width + height).
    public double getPerimeter() {
        return 2 * (getWidth() + getHeight());
    }

    // Display the rectangle information.
    @Override
    public void displayInfo() {
        System.out.println("Rectangle width: " + getWidth());
        System.out.println("Rectangle height: " + getHeight());
        System.out.println("Rectangle area: " + getArea());
        System.out.println("Rectangle perimeter: " + getPerimeter());
    }
}
