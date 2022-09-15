package auditory_exercises.aud03.calculator;

public class Division implements Strategy {
    @Override
    public double calculate(double num1, double num2) {
        if (num2 != 0)
            return num1 / num2;
        return num1;
    }
}
