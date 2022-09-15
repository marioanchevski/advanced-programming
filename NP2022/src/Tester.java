
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tester {
    public static void main(String[] args) {

        double[] ar ={0.12, 0.25, -2.74, 1.88, -3.69, 0.14, -3.36, -8.43, -5.21, -0.06, -4.33 ,1.56, 0.59, -9.72, -7.88, -5.38, -3.95 ,1.77, 2.66};
        double[][]a2 = new double[3][5];
        //{{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}};
        System.out.println(DoubleStream.of(ar).max().getAsDouble());
        StringBuilder stringBuilder = new StringBuilder();
        int start = 1;
        String str = stringBuilder.toString();
        String[][] arStr = {{"123", "123", "123"},{"123", "123", "123"}};

        IntStream.range(0, arStr.length).forEach(i->{
            a2[i]=Arrays.copyOfRange(ar, i*5, i*5+5);
        });
    }
}



