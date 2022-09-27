package laboratory_exercises.lab05;


import java.util.*;

class ResizableArray<T> {
    private T[] elements;
    private int numberOfElements;
    private int currentMaxNumberOfElements;

    public ResizableArray() {
        currentMaxNumberOfElements = 10;
        elements = (T[]) new Object[currentMaxNumberOfElements];
        numberOfElements = 0;
    }

    public ResizableArray(Object[] elements) {
        this.elements = Arrays.copyOf((T[]) elements, elements.length);
        numberOfElements = elements.length;
        currentMaxNumberOfElements = numberOfElements * 2;
    }

    public void addElement(T element) {
        if (numberOfElements == currentMaxNumberOfElements) {
            elements = Arrays.copyOf(elements, currentMaxNumberOfElements * 2);
            currentMaxNumberOfElements *= 2;
        }
        elements[numberOfElements++] = element;
    }

    public boolean removeElement(T element) {
        for (int i = 0; i < numberOfElements; i++) {
            if (elements[i].equals(element)) {
                for (int j = i; j < numberOfElements; j++) {
                    elements[j] = elements[j + 1];
                }
                numberOfElements--;
                return true;
            }
        }
        return false;
    }

    public boolean contains(T element) {
        return Arrays.asList(elements).contains(element);
    }

    public Object[] toArray() {
        return Arrays.stream(elements).toArray();
    }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public int count() {
        return numberOfElements;
    }

    public T elementAt(int idx) {
        return elements[idx];
    }

    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {
        T[] copy = Arrays.copyOf((T[]) src.toArray(), src.elements.length);
        for (T element : copy)
            if (element != null)
                dest.addElement(element);
    }
}

class IntegerArray extends ResizableArray<Integer> {

    public IntegerArray() {
    }

    public IntegerArray(Object[] elements) {
        super(elements);
    }

    public double sum() {
        return Arrays.stream(super.toArray()).filter(Objects::nonNull).mapToDouble(i -> (Integer) i).sum();
    }

    public double mean() {
        return Arrays.stream(super.toArray()).filter(Objects::nonNull).mapToDouble(i -> (Integer) i).sum() / count();
    }

    public int countNonZero() {
        return (int) Arrays.stream(super.toArray()).filter(Objects::nonNull).mapToInt(i -> (Integer) i).filter(i -> i != 0).count();
    }

    public IntegerArray distinct() {
        Object[] copy = Arrays.copyOf(toArray(), toArray().length);
        return new IntegerArray(Arrays.stream(copy).distinct().toArray());
    }

    public IntegerArray increment(int offset) {
        Object[] copy = Arrays.copyOf(toArray(), toArray().length);
        return new IntegerArray(Arrays.stream(copy).filter(Objects::nonNull)
                .mapToInt(i -> (Integer) i + offset).boxed().toArray());
    }
}

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if (test == 0) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while (jin.hasNextInt()) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if (test == 1) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for (int i = 0; i < 4; ++i) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if (test == 2) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while (jin.hasNextInt()) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if (a.sum() > 100)
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if (test == 3) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for (int w = 0; w < 500; ++w) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k = 2000;
                int t = 1000;
                for (int i = 0; i < k; ++i) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for (int i = 0; i < t; ++i) {
                    a.removeElement(k - i - 1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
