package auditory_exercises.aud05.queue;

public class PriorityQueueTest {
    public static void main(String[] args) {

        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        priorityQueue.add("middle1", 49);
        priorityQueue.add("middle2", 50);
        priorityQueue.add("middle3", 51);
        priorityQueue.add("top", 100);
        priorityQueue.add("bottom", 10);

        String line;
        while ((line = priorityQueue.remove()) != null)
            System.out.println(line);
    }
}
