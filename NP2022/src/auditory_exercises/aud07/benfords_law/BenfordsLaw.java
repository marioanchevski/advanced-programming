package auditory_exercises.aud07.benfords_law;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BenfordsLaw {
    private List<Integer> numbers;
    private Counter counter;

    public BenfordsLaw() {
        numbers = new ArrayList<>();
        counter = new Counter();
    }

    public void readData(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        numbers = bufferedReader.lines()
                .filter(line -> line.length() > 0)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int getFirstDigit(int number) {
        while (number >= 10)
            number /= 10;

        return number;
    }

    public void conduct() {
        numbers.stream()
                .map(this::getFirstDigit)
                .forEach(firstDigit -> counter.countDigit(firstDigit));
    }

    @Override
    public String toString() {
        return counter.toString();
    }
}
