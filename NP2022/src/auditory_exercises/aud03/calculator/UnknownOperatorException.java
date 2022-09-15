package auditory_exercises.aud03.calculator;

public class UnknownOperatorException extends Exception {
    public UnknownOperatorException(char operator) {
        super(String.format("'%c' is not a valid operator", operator));
    }
}
