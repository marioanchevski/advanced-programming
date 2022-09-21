package auditory_exercises.aud05.queue;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T> {

    private List<PriorityQueueElement<T>> elements;

    public PriorityQueue() {
        elements = new ArrayList<>();
    }

    public void add(T data, int priority) {
        PriorityQueueElement<T> newElement = new PriorityQueueElement<>(data, priority);

        int i;
        for (i = 0; i < elements.size(); i++)
            if (newElement.compareTo(elements.get(i)) <= 0) break;

        elements.add(i, newElement);
    }

    public T remove() {
        if (elements.isEmpty()) return null;
        return elements.remove(elements.size() - 1).getElement();
    }
}
