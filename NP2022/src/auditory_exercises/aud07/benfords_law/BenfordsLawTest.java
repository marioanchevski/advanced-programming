package auditory_exercises.aud07.benfords_law;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BenfordsLawTest {

    public static void main(String[] args) {
        BenfordsLaw benfordLaw = new BenfordsLaw();

        try {

            benfordLaw.readData(new FileInputStream("D:\\skladiste\\NP_projects\\NP2022\\src\\auditory_exercises\\aud07\\benfords_law\\data.txt"));
            benfordLaw.conduct();
            System.out.println(benfordLaw);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
