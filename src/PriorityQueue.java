import java.util.ArrayList;

class PriorityQueue {
    private ArrayList<Shipment> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public void ekle(Shipment shipment) {
        heap.add(shipment);
        int current = heap.size() - 1;

        while (current > 0) {
            int parent = (current - 1) / 2;
            if (heap.get(current).getDeliveryTime() < heap.get(parent).getDeliveryTime()) {
                swap(current, parent);
                current = parent;
            } else {
                break;
            }
        }
    }

    public Shipment kargoBul(String shipmentID) {
        for (Shipment shipment : heap) {
            if (shipment.getShipmentID().equals(shipmentID)) {
                return shipment;
            }
        }
        return null;
    }
    public String goster() {
        if (heap.isEmpty()) {
            return null;
        } else {

            ArrayList<Shipment> tempHeap = new ArrayList<>(heap);
            ArrayList<Shipment> sortedShipments = new ArrayList<>();
            StringBuilder result = new StringBuilder();

            while (!tempHeap.isEmpty()) {

                sortedShipments.add(extractMin(tempHeap));
            }

            for (Shipment shipment : sortedShipments) {
                result.append("Gönderi ID: ").append(shipment.getShipmentID())
                        .append(", Teslimat Süresi: ").append(shipment.getDeliveryTime()).append(" gün")
                        .append(", Durum: ").append(shipment.getDeliveryStatusAsString()).append("\n");
            }
            return result.toString();
        }
    }
    private void swap(int i, int j) {
        Shipment temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public Shipment al() {
        if (heap.size() == 0) {
            return null;
        }
        Shipment shipment = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapifyDown(0);
        return shipment;
    }
    private Shipment extractMin(ArrayList<Shipment> tempHeap) {
        Shipment minShipment = tempHeap.get(0);
        tempHeap.set(0, tempHeap.get(tempHeap.size() - 1));
        tempHeap.remove(tempHeap.size() - 1);
        heapifyDown(tempHeap, 0);
        return minShipment;
    }
    private void heapifyDown(ArrayList<Shipment> tempHeap, int current) {
        while (true) {
            int leftChild = 2 * current + 1;
            int rightChild = 2 * current + 2;
            int smallest = current;

            if (leftChild < tempHeap.size() && tempHeap.get(leftChild).getDeliveryTime() < tempHeap.get(smallest).getDeliveryTime()) {
                smallest = leftChild;
            }
            if (rightChild < tempHeap.size() && tempHeap.get(rightChild).getDeliveryTime() < tempHeap.get(smallest).getDeliveryTime()) {
                smallest = rightChild;
            }
            if (smallest != current) {
                Shipment temp = tempHeap.get(current);
                tempHeap.set(current, tempHeap.get(smallest));
                tempHeap.set(smallest, temp);
                current = smallest;
            } else {
                break;
            }
        }
    }
    private void heapifyDown(int current) {
        while (true) {
            int leftChild = 2 * current + 1;
            int rightChild = 2 * current + 2;
            int smallest = current;

            if (leftChild < heap.size() && heap.get(leftChild).getDeliveryTime() < heap.get(smallest).getDeliveryTime()) {
                smallest = leftChild;
            }
            if (rightChild < heap.size() && heap.get(rightChild).getDeliveryTime() < heap.get(smallest).getDeliveryTime()) {
                smallest = rightChild;
            }
            if (smallest != current) {
                swap(current, smallest);
                current = smallest;
            } else {
                break;
            }
        }

    }
    public ArrayList<Shipment> getHeap() {
        return heap;
    }

}