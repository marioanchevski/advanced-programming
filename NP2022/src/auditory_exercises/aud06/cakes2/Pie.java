package auditory_exercises.aud06.cakes2;

public class Pie extends Item {
    public Pie(String name) {
        super(name);
    }

    @Override
    public Type getType() {
        return Type.PIE;
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 50;
    }
}
