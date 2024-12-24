public class ShipmentLinkedList {
    ShipmentNode head;

    public ShipmentLinkedList() {
        this.head = null;
    }


    public void addShipment(Shipment shipment) {
        ShipmentNode newNode = new ShipmentNode(shipment);
        if (head == null||shipment.getShipmentDate().isBefore(head.shipment.getShipmentDate())) {
            newNode.next=head;
            head = newNode;
        }
        else {
            ShipmentNode temp=head;
            while (temp.next!=null&&temp.next.shipment.getShipmentDate().isBefore(shipment.getShipmentDate())){
                temp=temp.next;
            }
            newNode.next=temp.next;
            temp.next=newNode;
        }
    }
}
