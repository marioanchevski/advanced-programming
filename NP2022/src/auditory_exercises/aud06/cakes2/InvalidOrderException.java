package auditory_exercises.aud06.cakes2;

public class InvalidOrderException extends Exception{
    public InvalidOrderException(int orderId) {
        super(String.format("The order with id %d has less items than the minimum allowed.", orderId));
    }
}
