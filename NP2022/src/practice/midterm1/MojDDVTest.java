package practice.midterm1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

enum TaxType {
    A,
    B,
    V
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(int amount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
    }
}

class Item {
    private int price;
    private TaxType type;

    public Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public double getTaxReturn() {
        if (type.equals(TaxType.A))
            return price * 0.18;
        else if (type.equals(TaxType.B))
            return price * 0.05;
        else
            return 0;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

}

class Receipt {
    private long id;
    private List<Item> items;

    public Receipt(long id, List<Item> items) {
        this.items = items;
        this.id = id;
    }

    public static int getAmount(List<Item> items) {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public double getTaxReturnTotal() {
        return items.stream().mapToDouble(Item::getTaxReturn).sum();
    }

    public static Receipt create(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        long receiptId = Long.parseLong(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(i -> {
                    if (Character.isDigit(i.charAt(0))) {
                        items.add(new Item(Integer.parseInt(i)));
                    } else {
                        items.get(items.size() - 1).setType(TaxType.valueOf(i));
                    }
                });
        if (getAmount(items) > 30000)
            throw new AmountNotAllowedException(getAmount(items));
        return new Receipt(receiptId, items);
    }

    @Override
    public String toString() {
        //ID SUM_OF_AMOUNTS TAX_RETURN
        return String.format("%10d\t%10d\t%10.5f", id, getAmount(items), getTaxReturnTotal() * MojDDV.TAX_RETURN);
    }
}

class MojDDV {
    private List<Receipt> items;
    public static final double TAX_RETURN = 0.15;

    public MojDDV() {
        items = new ArrayList<>();
    }

    public void readRecords(InputStream in) {
        items = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(line -> {
                    try {
                        return Receipt.create(line);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void printTaxReturns(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        items.stream().forEach(i -> printWriter.println(i));
        printWriter.flush();
    }

    public void printStatistics(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        DoubleSummaryStatistics doubleSummaryStatistics = items.stream()
                .mapToDouble(i -> i.getTaxReturnTotal() * TAX_RETURN)
                .summaryStatistics();

        printWriter.println(String.format("min:\t%5.3f\nmax:\t%5.3f\nsum:\t%5.3f\ncount:\t%-5d\navg:\t%5.3f",
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getSum(),
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getAverage()));
        printWriter.flush();
    }
}


public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}