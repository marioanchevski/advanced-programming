package auditory_exercises.aud06.cakes1;

public class CakeShopApplicationTest {

    public static void main(String[] args) {
        CakeShopApplication cakeShopApplication = new CakeShopApplication();

        System.out.println("--- READING FROM INPUT STREAM ---");
        System.out.println(cakeShopApplication.readCakeOrders(System.in));

        System.out.println("--- PRINTING LARGEST ORDER TO OUTPUT STREAM ---");
        cakeShopApplication.printLongestOrder(System.out);
    }
}