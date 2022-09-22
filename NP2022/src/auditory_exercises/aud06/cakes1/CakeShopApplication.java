package auditory_exercises.aud06.cakes1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CakeShopApplication {
    private List<Order> orders;

    public CakeShopApplication() {
        orders = new ArrayList<>();
    }

    public int readCakeOrders(InputStream in) {
        orders = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(Order::createOrder)
                .collect(Collectors.toList());

        return orders.stream().mapToInt(Order::getNumberOfItems).sum();
    }

    public void printLongestOrder(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        Order longestOrder = orders.stream().max(Comparator.naturalOrder()).orElseGet(Order::new);
        printWriter.println(longestOrder);
        printWriter.close();
    }
}
