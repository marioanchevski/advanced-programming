package auditory_exercises.aud07.benfords_law;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Counter {

    private int[] numbersArray;
    private int digitsCount;

    Counter() {
        numbersArray = new int[10];
        digitsCount = 0;
    }

    public void countDigit(int digit) {
        numbersArray[digit]++;
        digitsCount++;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 10; i++)
            stringBuilder.append(String.format("%d --> %.2f %%\n", i, numbersArray[i] * 100.0 / digitsCount));
        //return stringBuilder.toString();

        return IntStream.range(1, 10)
                .mapToObj(i -> String.format("%d --> %.2f %%", i, numbersArray[i] * 100.0 / digitsCount))
                .collect(Collectors.joining("\n"));
    }
}
