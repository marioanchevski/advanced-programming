package auditory_exercises.aud04.oldest;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPersonTest {

    public static List<Person> readFile(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return bufferedReader.lines()
                .map(line -> new Person(line))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        File file = new File("D:\\skladiste\\NP_projects\\NP2022\\src" +
                "\\auditory_exercises\\aud04\\files\\people.txt");

        try {
            List<Person> list = readFile(new FileInputStream(file));
            Collections.sort(list);
            System.out.println(list.get(list.size() - 1));

            if (list.stream().max(Comparator.naturalOrder()).isPresent())
                System.out.println(list.stream().max(Comparator.naturalOrder()).get());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
