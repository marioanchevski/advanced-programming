package auditory_exercises.aud07.finalists;

import java.util.Scanner;

public class FinalistPickerTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int finalists = scanner.nextInt();
        int prizes = scanner.nextInt();

        FinalistPicker finalistPicker = new FinalistPicker(finalists);
        try {
            System.out.println(finalistPicker.pick(prizes));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}
