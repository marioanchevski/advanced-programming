package auditory_exercises.aud08.book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookCollection {
    private List<Book> books;

    final Comparator<Book> titleAndPriceComparator =
            Comparator.comparing(Book::getTitle)
                    .thenComparing(Book::getPrice);

    final Comparator<Book> priceComparator =
            Comparator.comparing(Book::getPrice)
                    .thenComparing(Book::getTitle);


    public BookCollection() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
        books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .sorted(titleAndPriceComparator)
                .forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(priceComparator)
                .limit(n)
                .collect(Collectors.toList());
    }
}
