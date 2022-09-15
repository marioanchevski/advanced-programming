import org.w3c.dom.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tester {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(2, 10).boxed().collect(Collectors.toList());
        System.out.println(list);

    }
}
class Generic <T extends Animal & Comparable<Animal>>{
    T data;

    public Generic(T data) {
        this.data = data;

    }

    public void print(){
        System.out.println(data);
    }
}

class Animal implements Comparable<Animal>{
    int age;



    @Override
    public int compareTo(Animal o) {
        return 0;
    }
}
class Dog extends Animal{

}



