
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tester {


    public static void main(String[] args) {
        String[] arr = {"Hello", "World"};

        List<Integer> list= new ArrayList<>();
        int[] b =  new int[2];

        int[] ar = {1,2,3};
        b = Arrays.stream(ar).filter(i->i>=2).toArray();

        for(int i : b)
            System.out.println(i);
        System.out.println(b.getClass());


    }
    public static int[] getCopy(int[] a){
        return Arrays.copyOf(a, a.length);
    }
}



