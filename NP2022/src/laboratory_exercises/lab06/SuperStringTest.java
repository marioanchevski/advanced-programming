package laboratory_exercises.lab06;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SuperString {
    private LinkedList<String> superString;
    private LinkedList<String> lastAdded;

    public SuperString() {
        superString = new LinkedList<>();
        lastAdded = new LinkedList<>();
    }

    public void append(String s) {
        superString.add(s);
        lastAdded.add(s);
    }

    public void insert(String s) {
        superString.add(0, s);
        lastAdded.add(s);
    }

    public boolean contains(String s) {
        return this.toString().contains(s);
    }

    private String reverseWord(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    public void reverse() {
        Collections.reverse(superString);
        superString = superString.stream().map(this::reverseWord).collect(Collectors.toCollection(LinkedList::new));
        lastAdded = lastAdded.stream().map(this::reverseWord).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public String toString() {
        return String.join("", superString);
    }

    public void removeLast(int k) {
        IntStream.range(0, k).forEach(i -> {
            superString.remove(lastAdded.get(lastAdded.size() - 1));
            lastAdded.remove(lastAdded.size() - 1);
        });
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }

}
