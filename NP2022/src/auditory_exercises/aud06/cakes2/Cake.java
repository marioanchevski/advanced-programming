package auditory_exercises.aud06.cakes2;

public class Cake extends Item {
    public Cake(String name) {
        super(name);
    }

    @Override
    public Type getType() {
        return Type.CAKE;
    }
}
