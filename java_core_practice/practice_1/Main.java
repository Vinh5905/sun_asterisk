public class Main {
    public static void main(String[] args) {
        // Requirement 4: Initialize objects of the three classes and display their information.
        Shape shape = new Shape(8.5, 4.0);
        Rectangle rectangle = new Rectangle(6.0, 3.5);
        Circle circle = new Circle(10.0);

        System.out.println("Shape Information");
        shape.displayInfo();

        System.out.println();
        System.out.println("Rectangle Information");
        rectangle.displayInfo();

        System.out.println();
        System.out.println("Circle Information");
        circle.displayInfo();
    }
}
