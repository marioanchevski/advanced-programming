package laboratory_exercises.lab02;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.IntStream;


class InsufficientElementsException extends Exception {
    public InsufficientElementsException() {
        super("Insufficient number of elements");
    }
}

class InvalidRowNumberException extends Exception {
    public InvalidRowNumberException() {
        super("Invalid row number");
    }
}

class InvalidColumnNumberException extends Exception {
    public InvalidColumnNumberException() {
        super("Invalid column number");
    }
}

class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner scanner = new Scanner(input);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        double[] doubleArray = new double[1000];
        int i = 0;
        while (scanner.hasNextDouble())
            doubleArray[i++] = scanner.nextDouble();

        doubleArray = Arrays.copyOf(doubleArray, i);
        return new DoubleMatrix(doubleArray, m, n);
    }
}

class DoubleMatrix {
    private double[][] doubleMatrix;

    public DoubleMatrix(double[] f, int m, int n) throws InsufficientElementsException {
        if (f.length < m * n)
            throw new InsufficientElementsException();

        doubleMatrix = new double[m][n];
        int index = f.length - m * n;
        double[] copy = Arrays.copyOfRange(f, index, f.length);
        IntStream.range(0, doubleMatrix.length).forEach(i -> {
            doubleMatrix[i] = Arrays.copyOfRange(copy, i * n, i * n + n);
        });
/*        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                doubleMatrix[i][j] = f[index + j];
            }
            index += n;
        }*/
    }

    public String getDimensions() {
        return String.format("[%d x %d]", rows(), columns());
    }

    public int rows() {
        return doubleMatrix.length;
    }

    public int columns() {
        return doubleMatrix[0].length;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if (row <= 0 || row > rows())
            throw new InvalidRowNumberException();

        double max = doubleMatrix[row - 1][0];
        for (int i = 1; i < columns(); i++) {
            if (max < doubleMatrix[row - 1][i])
                max = doubleMatrix[row - 1][i];
        }
        return max;
    }

    public double maxElementAtColumn(int col) throws InvalidColumnNumberException {
        if (col <= 0 || col > columns())
            throw new InvalidColumnNumberException();

        double max = doubleMatrix[0][col - 1];
        for (int i = 1; i < rows(); i++) {
            if (max < doubleMatrix[i][col - 1])
                max = doubleMatrix[i][col - 1];
        }
        return max;
    }

    public double sum() {
        return Arrays.stream(doubleMatrix).flatMapToDouble(Arrays::stream).sum();
    }

    public double[] toSortedArray() {
        return Arrays.stream(doubleMatrix).flatMapToDouble(array -> Arrays.stream(array))
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToDouble(i -> i.doubleValue()).toArray();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns() - 1; j++) {
                s.append(String.format("%.2f\t", doubleMatrix[i][j]));
            }
            s.append(String.format("%.2f\n", doubleMatrix[i][columns() - 1]));
        }
        s = new StringBuilder(s.substring(0, s.length() - 1));
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleMatrix that = (DoubleMatrix) o;
        return Arrays.deepEquals(doubleMatrix, that.doubleMatrix);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(doubleMatrix);
    }
}


public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}
