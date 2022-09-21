package auditory_exercises.aud05.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * V - value
 * E - element
 * T - type
 */
public class Box<E extends Drawable> {

    private List<E> elements;
    public static Random random = new Random();

    public Box() {
        elements = new ArrayList<>();
    }

    public void add(E element) {
        elements.add(element);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public E draw() {
        if (isEmpty()) return null;
        return elements.remove(random.nextInt(elements.size()));
    }
}
