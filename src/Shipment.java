import java.time.LocalDate;


public class Shipment {
    private String shipmentID;
    private LocalDate shipmentDate;
    private boolean deliveryStatus;
    private int deliveryTime;

    public Shipment(City deliveryCity) {
        this.shipmentID = ShipmentIDGenerator.generateShipmentID();
        this.shipmentDate = LocalDate.now();
        this.deliveryStatus = false;
        this.deliveryTime = deliveryCity.getDeliveryTime();
    }


    public String getShipmentID() {
        return shipmentID;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getDeliveryStatusAsString() {
        return deliveryStatus ? "Teslim Edildi" : "Teslim Edilmedi";
    }

    public void setDeliveryStatus(boolean status) {
        this.deliveryStatus = status;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    @Override
    public String toString() {
        return "Kargo{" +
                "ID='" + shipmentID + '\'' +
                ", Teslimat Süresi=" + deliveryTime +
                ", Durum='" + deliveryStatus + '\'' +
                '}';
    }
}
