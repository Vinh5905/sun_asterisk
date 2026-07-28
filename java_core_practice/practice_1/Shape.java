// Requirement 1: Create a Shape class with width and height properties.
public class Shape {
    private double width;
    private double height;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void displayInfo() {
        System.out.println("Shape width: " + getWidth());
        System.out.println("Shape height: " + getHeight());
    }
}
