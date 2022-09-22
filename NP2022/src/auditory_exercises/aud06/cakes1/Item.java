package auditory_exercises.aud06.cakes1;

public class Item {
    private String name;
    private int price;

    public Item(String name) {
        this.name = name;
        price = 0;
    }

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
