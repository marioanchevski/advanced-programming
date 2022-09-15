package auditory_exercises.aud08.count_occurance;

import java.util.Collection;

public class CountOccurancesTest {

    public static int count(Collection<Collection<String>> c, String str) {
        int count = 0;
        for (Collection<String> collection : c) {
            for (String s : collection) {
                if (s.equals(str))
                    count++;
            }
        }
        return count;
    }

    static long countFunc(Collection<Collection<String>> collection, String str) {
        return (int) collection.stream()
                .flatMap(col -> col.stream())
                .filter(s -> s.equalsIgnoreCase(str))
                .count();
    }

    public static void main(String[] args) {

    }
}
