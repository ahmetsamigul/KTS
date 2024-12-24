import java.util.List;

public class ShipmentSearch {
    public static Shipment binarySearch(List<Shipment> shipments, String shipmentID) {
        int low = 0;
        int high = shipments.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Shipment midShipment = shipments.get(mid);
            int compareResult = midShipment.getShipmentID().compareTo(shipmentID);

            if (compareResult == 0) {
                return midShipment;
            } else if (compareResult < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
}
