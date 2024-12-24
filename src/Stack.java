public class Stack {
    private ShipmentNode top;
    private int size;
    private final int MAX = 5;

    public Stack() {
        this.top = null;
        this.size = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size >= MAX;
    }

    public void push(Shipment shipment) {
        if (isFull()) {

            ShipmentNode temp = top;
            while (temp.next != null && temp.next.next != null) {
                temp = temp.next;
            }

            if (temp.next != null) {
                temp.next = null;
                size--;
            }
        }


        ShipmentNode newNode = new ShipmentNode(shipment);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Shipment pop() {
        if (isEmpty()) {
            System.out.println("boş");
            return null;
        }
        Shipment removedShipment = top.shipment;
        top = top.next;
        size--;
        return removedShipment;
    }
    public void display() {
        if (isEmpty()) {
            System.out.println("Gönderi geçmişi boş.");
            return;
        }
        System.out.println("Son 5 Gönderi:");
        ShipmentNode current = top;
        while (current != null) {
            System.out.println("Gönderi ID: " + current.shipment.getShipmentID());
            System.out.println("Tarih: " + current.shipment.getShipmentDate());
            System.out.println("Durum: " + current.shipment.getDeliveryStatusAsString());
            System.out.println("---------------------------------");
            current = current.next;
        }
    }

    public int size() {
        return size;
    }

}

