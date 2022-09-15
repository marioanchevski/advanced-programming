package auditory_exercises.aud08.book;

import java.util.Objects;

public class Book {
    private String title;
    private String category;
    private float price;


    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Float.compare(book.price, price) == 0 && title.equals(book.title) && category.equals(book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category, price);
    }
}
