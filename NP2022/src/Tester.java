
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tester {


    public static void main(String[] args) {
        int[] array = {1,2,3,4,5};
        test(1,2,3);
    }

    public static void test(int... mace){
        for (int i : mace){
            System.out.println(i);
        }
    }
}



