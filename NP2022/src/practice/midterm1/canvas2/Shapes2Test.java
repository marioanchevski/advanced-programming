package practice.midterm1.canvas2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class IrregularCanvasException extends Exception {
    public IrregularCanvasException(String canvasId, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", canvasId, maxArea));
    }
}

enum ShapeType {
    CIRCLE,
    SQUARE
}

abstract class Shape {
    abstract void setSize(double size);

    abstract double area();

    abstract ShapeType getType();
}

class Square extends Shape {
    private double side;

    public Square(int side) {
        this.side = side;
    }

    public double area() {
        return side * side;
    }

    @Override
    ShapeType getType() {
        return ShapeType.SQUARE;
    }

    public void setSize(double side) {
        this.side = side;
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public void setSize(double radius) {
        this.radius = radius;
    }

    public double area() {
        return radius * radius * Math.PI;
    }

    @Override
    ShapeType getType() {
        return ShapeType.CIRCLE;
    }
}


class Canvas implements Comparable<Canvas> {
    private String canvasId;
    private List<Shape> shapes;

    public Canvas(String canvasId, List<Shape> squares) {
        this.canvasId = canvasId;
        this.shapes = squares;
    }

    public static Canvas create(String line, double maxArea) throws IrregularCanvasException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Shape> shapes = new ArrayList<>();
        Arrays.stream(parts).skip(1)
                .forEach(i -> {
                    if (i.equals("S")) {
                        shapes.add(new Square(0));
                    } else if (i.equals("C")) {
                        shapes.add(new Circle(0));
                    } else {
                        shapes.get(shapes.size() - 1).setSize(Double.parseDouble(i));
                    }
                });

        if (shapes.stream().mapToDouble(Shape::area).anyMatch(i -> i >= maxArea))
            throw new IrregularCanvasException(id, maxArea);
        return new Canvas(id, shapes);
    }

    public int getShapesListLength() {
        return shapes.size();
    }

    public int getTotalCircles() {
        return (int) shapes.stream()
                .filter(shape -> shape.getType().equals(ShapeType.CIRCLE))
                .count();
    }

    public int getTotalSquares() {
        return (int) shapes.stream()
                .filter(shape -> shape.getType().equals(ShapeType.SQUARE))
                .count();
    }

    public double getSumOfShapeAreas() {
        return shapes.stream()
                .mapToDouble(Shape::area).sum();
    }

    public String getCanvasId() {
        return canvasId;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    @Override
    public int compareTo(Canvas other) {
        return Double.compare(this.getSumOfShapeAreas(), other.getSumOfShapeAreas());
    }
}

class ShapesApplication {
    private double maxArea;
    private List<Canvas> canvasList;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        canvasList = new ArrayList<>();
    }


    public void readCanvases(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        canvasList = bufferedReader.lines()
                .map(line -> {
                    try {
                        return Canvas.create(line, maxArea);
                    } catch (IrregularCanvasException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    void printCanvases(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        canvasList.stream().filter(Objects::nonNull).sorted(Comparator.reverseOrder()).forEach(canvas -> {
            List<Shape> canvasShapes = canvas.getShapes();
            DoubleSummaryStatistics doubleSummaryStatistics = canvasShapes.stream().mapToDouble(Shape::area).summaryStatistics();
            printWriter.println(String.format("%s %d %d %d %.2f %.2f %.2f",
                    canvas.getCanvasId(), canvas.getShapesListLength(), canvas.getTotalCircles(), canvas.getTotalSquares(),
                    doubleSummaryStatistics.getMin(), doubleSummaryStatistics.getMax(), doubleSummaryStatistics.getAverage()));
        });
        printWriter.flush();
    }
}


public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}