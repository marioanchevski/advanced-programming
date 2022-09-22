package auditory_exercises.aud06.cakes2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order implements Comparable<Order> {
    private int id;
    private List<Item> items;

    public Order() {
        items = new ArrayList<>();
        id = -1;
    }

    public Order(int id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Order createOrder(String line) throws InvalidOrderException {
        String[] parts = line.split("\\s+");
        int orderId = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts).skip(1)
                .forEach(part -> {
                    if (Character.isAlphabetic(part.charAt(0))) {
                        if (part.charAt(0) == 'P')
                            items.add(new Pie(part));
                        else
                            items.add(new Cake(part));
                    } else
                        items.get(items.size() - 1).setPrice(Integer.parseInt(part));
                });
        if (items.size() < CakeShopApplication.MIN_ORDERS)
            throw new InvalidOrderException(orderId);
        return new Order(orderId, items);
    }

    public int getNumberOfItems() {
        return items.size();
    }

    public int totalPies() {
        return (int) items.stream().filter(i -> i.getType().equals(Type.PIE)).count();
    }

    public int totalCakes() {
        return (int) items.stream().filter(i -> i.getType().equals(Type.CAKE)).count();
    }

    public int getTotalPrice() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.getTotalPrice(), other.getTotalPrice());
    }

    @Override
    public String toString() {
        return id + " " + getNumberOfItems() + " " + totalPies() + " " + totalCakes() + " " + getTotalPrice();
    }
}
