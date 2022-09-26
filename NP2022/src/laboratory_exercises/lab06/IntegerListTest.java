package laboratory_exercises.lab06;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class IntegerList {
    private List<Integer> integerList;

    public IntegerList() {
        integerList = new ArrayList<>();
    }

    public IntegerList(Integer... numbers) {
        integerList = new ArrayList<>(Arrays.asList(numbers));
    }

    public void add(int el, int idx) {
        if (idx > integerList.size())
            IntStream.range(size(), idx).forEach(i -> integerList.add(0));

        integerList.add(idx, el);
    }

    public int remove(int idx) {
        return integerList.remove(idx);
    }

    public void set(int el, int idx) {
        integerList.set(idx, el);
    }

    public int get(int idx) {
        return integerList.get(idx);
    }

    public int size() {
        return integerList.size();
    }

    public int count(int el) {
        return (int) integerList.stream().filter(i -> i.equals(el)).count();
    }

    public void removeDuplicates() {

/*        for (int i = 0; i < list.size(); i++) {
            int element = list.get(i);
            int lastIndexOfelement = list.lastIndexOf(element);
            if (lastIndexOfelement == i)
                continue;
            for (int j = i; j < lastIndexOfelement; j++) {
                if (list.get(j) == element)
                    list.set(j, -1);
            }
        }

        list.removeIf(i -> i == -1);*/

        Collections.reverse(integerList);
        integerList = integerList.stream().distinct().collect(Collectors.toList());
        Collections.reverse(integerList);
    }

    public int sumFirst(int k) {
        if (k > size()) k = size();
/*        int sum = 0;
        for (int i = 0; i < k; i++)
            sum += list.get(i);
        return sum;*/
        //return integerList.subList(0, k).stream().reduce(Integer::sum).orElse(0);
        return integerList.subList(0, k).stream().mapToInt(i -> i.intValue()).sum();
    }

    public int sumLast(int k) {
/*        int sum = 0;
        for (int i = list.size() - k; i < list.size(); i++)
            sum += list.get(i);
        return sum;*/
        if (k > size()) k = 0;
        return integerList.subList(size() - k, size()).stream().reduce(Integer::sum).orElse(0);
    }

    public void shiftRight(int idx, int k) {
        int newIndex = (idx + k) % size();
        Integer element = integerList.remove(idx);
        integerList.add(newIndex, element);

    }

    public void shiftLeft(int idx, int k) {
        int newIndex = idx - (k % size());
        if (newIndex < 0)
            newIndex += size();
        Integer el = integerList.remove(idx);
        integerList.add(newIndex, el);
    }

    public IntegerList addValue(int value) {
        return new IntegerList(integerList.stream().mapToInt(i -> i + value).boxed().toArray(Integer[]::new));
    }
}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if (il.size() == 0) System.out.print("EMPTY");
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
