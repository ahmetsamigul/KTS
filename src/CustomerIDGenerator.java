public class CustomerIDGenerator {
    private static int customerCounter = 1;

    public static String generateCustomerID() {
        return "C" + String.format("%03d", customerCounter++);
    }
}
