package auditory_exercises.aud06.cakes1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order implements Comparable<Order>{
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

    public static Order createOrder(String line){
        String[] parts = line.split("\\s+");
        int orderId = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts).skip(1)
                .forEach(part -> {
                    if (Character.isAlphabetic(part.charAt(0)))
                        items.add(new Item(part));
                    else
                        items.get(items.size() - 1).setPrice(Integer.parseInt(part));
                });
        return new Order(orderId, items);
    }

    public int getNumberOfItems(){
        return items.size();
    }
    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.getNumberOfItems(), other.getNumberOfItems());
    }

    @Override
    public String toString() {
        return id + " " +getNumberOfItems();
    }
}
