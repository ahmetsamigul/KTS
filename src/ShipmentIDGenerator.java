public class ShipmentIDGenerator {
    private static int shipmentCounter = 1;

    public static String generateShipmentID() {
        return "S" + String.format("%03d", shipmentCounter++);
    }
}
