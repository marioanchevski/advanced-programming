
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Tester {


    public static void main(String[] args) throws IOException {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Mace"));
        list.add(new Person("sase"));
        list.add(new Person("jovaa"));

        Person n = null;
        if (!list.stream().filter(i ->i.getName().equals("M1ace")).collect(Collectors.toList()).isEmpty())
            n = list.stream().filter(i ->i.getName().equals("M1ace")).collect(Collectors.toList()).get(0);
        System.out.println(n);
    }

    public static void test(int... mace){
        for (int i : mace){
            System.out.println(i);
        }
    }
}
class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}



