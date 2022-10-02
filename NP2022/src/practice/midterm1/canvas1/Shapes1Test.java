package practice.midterm1.canvas1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Square {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public int perimeter() {
        return side * 4;
    }
}


class Canvas implements Comparable<Canvas> {
    private String canvasId;
    private List<Square> squares;

    public Canvas(String canvasId, List<Square> squares) {
        this.canvasId = canvasId;
        this.squares = squares;
    }

    public static Canvas create(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Square> squares = new ArrayList<>();
        squares = Arrays.stream(parts).skip(1)
                .map(side -> new Square(Integer.parseInt(side)))
                .collect(Collectors.toList());

        return new Canvas(id, squares);
    }

    public int getSquareListLength() {
        return squares.size();
    }

    public int totalSquaresPerimeter() {
        return squares.stream()
                .mapToInt(Square::perimeter).sum();
    }

    public String getCanvasId() {
        return canvasId;
    }

    @Override
    public int compareTo(Canvas other) {
        return Integer.compare(this.totalSquaresPerimeter(), other.totalSquaresPerimeter());
    }
}

class ShapesApplication {
    private List<Canvas> canvasList;

    public ShapesApplication() {
        canvasList = new ArrayList<>();
    }

    public int readCanvases(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        canvasList = bufferedReader.lines()
                .map(line -> Canvas.create(line))
                .collect(Collectors.toList());
        return canvasList.stream().mapToInt(s -> s.getSquareListLength()).sum();
    }

    void printLargestCanvasTo(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        Canvas result = canvasList.stream()
                .max(Comparator.naturalOrder()).orElse(null);
        printWriter.println(String.format("%s %d %d", result.getCanvasId(), result.getSquareListLength(), result.totalSquaresPerimeter()));
        printWriter.flush();
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}