package laboratory_exercises.lab03;

import java.util.Arrays;
import java.util.Scanner;

class InvalidExtraTypeException extends Exception {
}

class InvalidPizzaTypeException extends Exception {
}

class ItemOutOfStockException extends Exception {
    public ItemOutOfStockException(int count) {
    }
}

class ArrayIndexOutOfBоundsException extends ArrayIndexOutOfBoundsException {
    public ArrayIndexOutOfBоundsException(int idx) {
    }
}

class EmptyOrder extends Exception {
}

class OrderLockedException extends Exception {
}

interface Item {
    int getPrice();

    String getType();
}

class ExtraItem implements Item {
    private String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (type.equals("Coke") || type.equals("Ketchup"))
            this.type = type;
        else
            throw new InvalidExtraTypeException();
    }

    public String getType() {
        return type;
    }

    @Override
    public int getPrice() {
        if (type.equals("Ketchup"))
            return 3;
        else
            return 5;
    }
}

class PizzaItem implements Item {
    private String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (type.equals("Standard") || type.equals("Pepperoni") || type.equals("Vegetarian"))
            this.type = type;
        else
            throw new InvalidPizzaTypeException();
    }

    public String getType() {
        return type;
    }

    @Override
    public int getPrice() {
        if (type.equals("Standard"))
            return 10;
        if (type.equals("Pepperoni"))
            return 12;
        else
            return 8;
    }
}

class Order {
    private Item[] orders;
    private int[] quantities;
    private int numberOfOrders;
    private int currentMaxOrders;
    private boolean locked;


    public Order() {
        locked = false;
        numberOfOrders = 0;
        currentMaxOrders = 10;
        orders = new Item[currentMaxOrders];
        quantities = new int[currentMaxOrders];
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if (locked)
            throw new OrderLockedException();

        if (count > 10)
            throw new ItemOutOfStockException(count);

        Item temp = null;
        for (Item i : orders)
            if (i != null)
                if (i.getType().equals(item.getType()))
                    temp = i;

        if (temp == null) {
            if (numberOfOrders == currentMaxOrders) {
                orders = Arrays.copyOf(orders, currentMaxOrders * 2);
                quantities = Arrays.copyOf(quantities, currentMaxOrders * 2);
                currentMaxOrders *= 2;
            }
            orders[numberOfOrders] = item;
            quantities[numberOfOrders] = count;
            numberOfOrders++;
        } else {
            int index = 0;
            for (int i = 0; i < orders.length; i++) {
                if (orders[i].getType().equals(item.getType())) {
                    index = i;
                    break;
                }
            }
            quantities[index] = count;
        }

    }

    public int getPrice() {
        int sum = 0;
        for (int i = 0; i < numberOfOrders; i++) {
            sum += orders[i].getPrice() * quantities[i];
        }
        return sum;
    }

    public void displayOrder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfOrders; i++) {
            stringBuilder.append(String.format("%3d.%-15sx%2d%5d$\n", i + 1, orders[i].getType(),
                    quantities[i], orders[i].getPrice() * quantities[i]));
        }
        stringBuilder.append(String.format("%-22s%5d$", "Total:", getPrice()));
        System.out.println(stringBuilder.toString());
    }

    public void removeItem(int idx) throws OrderLockedException {
        if (locked)
            throw new OrderLockedException();
        if (idx > numberOfOrders)
            throw new ArrayIndexOutOfBоundsException(idx);

        //shift elements
        for (int i = idx; i < numberOfOrders; i++) {
            orders[i] = orders[i + 1];
            quantities[i] = quantities[i + 1];
        }
        orders[numberOfOrders] = null;
        quantities[numberOfOrders] = 0;
        numberOfOrders--;
    }

    public void lock() throws EmptyOrder {
        if (numberOfOrders == 0)
            throw new EmptyOrder();
        locked = true;
    }

}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}