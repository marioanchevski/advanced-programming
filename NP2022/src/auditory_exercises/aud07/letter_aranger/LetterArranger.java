package auditory_exercises.aud07.letter_aranger;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LetterArranger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();

        System.out.println(LetterArranger.arrangeSentence(sentence));
    }

    public static String arrangeSentence(String sentence) {

        return Arrays.stream(sentence.split("\\s+"))
                .map(line -> arrangeWord(line))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    private static String arrangeWord(String word) {
        return word.chars()
                .sorted()
                .mapToObj(ch -> String.valueOf((char) ch))
                .collect(Collectors.joining());
    }
}
