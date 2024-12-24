import java.util.List;

public class ShipmentSort {
    public static void mergeSort(List<Shipment> shipments) {
        if (shipments.size() <= 1) {
            return;
        }

        int mid = shipments.size() / 2;
        List<Shipment> left = shipments.subList(0, mid);
        List<Shipment> right = shipments.subList(mid, shipments.size());

        mergeSort(left);
        mergeSort(right);
        merge(shipments, left, right);
    }

    private static void merge(List<Shipment> shipments, List<Shipment> left, List<Shipment> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getDeliveryTime() <= right.get(j).getDeliveryTime()) {
                shipments.set(k++, left.get(i++));
            } else {
                shipments.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            shipments.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            shipments.set(k++, right.get(j++));
        }
    }
}
