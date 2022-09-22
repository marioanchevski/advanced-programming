package auditory_exercises.aud06.cakes2;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CakeShopApplication {
    public static int MIN_ORDERS;
    private List<Order> orders;

    public CakeShopApplication(int minOrderItems) {
        MIN_ORDERS = minOrderItems;
        orders = new ArrayList<>();
    }

    public void readCakeOrders(InputStream inputStream) {
        orders = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .map(line -> {
                    try {
                        return Order.createOrder(line);
                    } catch (InvalidOrderException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void printAllOrders(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        orders.stream().sorted(Comparator.reverseOrder()).forEach(order -> printWriter.println(order));
        printWriter.close();
    }

}
