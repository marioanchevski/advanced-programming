package auditory_exercises.aud08.eratosthenes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EratosthenesTest {

    private static boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++)
            if (number % i == 0)
                return false;
        return true;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = IntStream.range(2, 101).boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < list.size() - 1; i++) {
            if (isPrime(list.get(i))) {
                for (int j = i + 1; j < list.size() - 1; i++) {
                    if (list.get(j) % list.get(i) == 0) {
                        list.remove(j);
                        j--;
                    }
                }
            }
        }
        System.out.println(list);
    }
}
