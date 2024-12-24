import java.util.Scanner;

public class Customer {
    private String customerId;
    private String firstname;
    private String lastname;
    private ShipmentLinkedList shipmentHistory;
    private Stack recentShipments;

    public Customer(String customerId, String firstname, String lastname) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.shipmentHistory = new ShipmentLinkedList();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ShipmentLinkedList getShipmentHistory() {
        return shipmentHistory;
    }

    public Stack getRecentShipments() {
        return recentShipments;
    }

    public void setRecentShipments(Stack recentShipments) {
        this.recentShipments = recentShipments;
    }

    public void setShipmentHistory(ShipmentLinkedList shipmentHistory) {
        this.shipmentHistory = shipmentHistory;
    }

    public void addShipmentToCustomerHistory(Shipment shipment) {
        shipmentHistory.addShipment(shipment);
    }
}
