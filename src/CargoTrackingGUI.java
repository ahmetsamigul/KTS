import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CargoTrackingGUI extends JFrame {
    private DeliveryTree deliveryTree;
    private CustomerLinkedList customerList = new CustomerLinkedList();
    private Stack shipmentStack = new Stack();
    private PriorityQueue kargoKuyrugu = new PriorityQueue();

    public CargoTrackingGUI() {
        setTitle("Online Kargo Takip Sistemi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(10, 1)); // Paneli 10 satır olarak ayarladım


        JLabel titleLabel = new JLabel("Online Kargo Takip Sistemi", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);


        JButton addCustomerButton = new JButton("Müşteri Ekle");
        JButton addShipmentButton = new JButton("Gönderi Ekle");
        JButton viewHistoryButton = new JButton("Gönderi Geçmişini Görüntüle");
        JButton updateStatusButton = new JButton("Gönderi Durumu Güncelle");
        JButton viewShipmentsButton = new JButton("Teslimat Süresine Göre Kargolar");
        JButton viewRecentButton = new JButton("Son 5 Gönderi");
        JButton viewRoutesButton = new JButton("Kargo Rotaları");
        JButton searchShipmentButton = new JButton("Teslim Edilmiş Gönderi Ara");
        JButton sortShipmentsButton = new JButton("Teslim Edilmemiş Gönderileri Sırala");
        JButton exitButton = new JButton("Çıkış");


        mainPanel.add(addCustomerButton);
        mainPanel.add(addShipmentButton);
        mainPanel.add(viewHistoryButton);
        mainPanel.add(updateStatusButton);
        mainPanel.add(viewShipmentsButton);
        mainPanel.add(viewRecentButton);
        mainPanel.add(viewRoutesButton);
        mainPanel.add(searchShipmentButton);
        mainPanel.add(sortShipmentsButton);
        mainPanel.add(exitButton);

        add(mainPanel, BorderLayout.CENTER);


        addCustomerButton.addActionListener(this::handleAddCustomer);
        addShipmentButton.addActionListener(this::handleAddShipment);
        viewShipmentsButton.addActionListener(this::handleViewShipments);
        viewRecentButton.addActionListener(this::handleViewRecentShipments);
        viewRoutesButton.addActionListener(this::handleViewRoutes);
        searchShipmentButton.addActionListener(this::handleSearchShipment);
        sortShipmentsButton.addActionListener(this::handleSortShipments);
        updateStatusButton.addActionListener(this::handleUpdateStatus);
        viewHistoryButton.addActionListener(this::handleViewHistory);


        exitButton.addActionListener(e -> System.exit(0));


        deliveryTree = new DeliveryTree(new City("Merkez", 0));


        City istanbul = new City("Istanbul", 1);
        istanbul.addSubCity(new City("Kadıköy", 101));
        istanbul.addSubCity(new City("Beşiktaş", 102));
        istanbul.addSubCity(new City("İstanbul Merkez", 103));

        City bursa = new City("Bursa", 2);
        bursa.addSubCity(new City("Osmangazi", 201));
        bursa.addSubCity(new City("Nilüfer", 202));
        bursa.addSubCity(new City("Bursa Merkez", 203));

        City izmir = new City("Izmir", 3);
        izmir.addSubCity(new City("Konak", 301));
        izmir.addSubCity(new City("Bornova", 302));
        izmir.addSubCity(new City("İzmir Merkez", 303));

        City ankara = new City("Ankara", 4);
        ankara.addSubCity(new City("Çankaya", 401));
        ankara.addSubCity(new City("Keçiören", 402));
        ankara.addSubCity(new City("Ankara Merkez", 403));

        City antalya = new City("Antalya", 5);
        antalya.addSubCity(new City("Muratpaşa", 501));
        antalya.addSubCity(new City("Konyaaltı", 502));
        antalya.addSubCity(new City("Antalya Merkez", 503));


        deliveryTree.getRoot().addSubCity(istanbul);
        deliveryTree.getRoot().addSubCity(bursa);
        deliveryTree.getRoot().addSubCity(izmir);
        deliveryTree.getRoot().addSubCity(ankara);
        deliveryTree.getRoot().addSubCity(antalya);


        deliveryTree.assignDeliveryTimes();



    }

    private void handleViewHistory(ActionEvent e) {

        String customerId = JOptionPane.showInputDialog("Gönderi geçmişini görüntülemek için müşteri ID'sini giriniz:");

        if (customerId != null && !customerId.isEmpty()) {

            CustomerNode temp = customerList.head;
            boolean customerFound = false;

            while (temp != null) {
                if (temp.customer.getCustomerId().equals(customerId)) {

                    StringBuilder historyList = new StringBuilder();
                    historyList.append("Müşteri: ").append(temp.customer.getFirstname())
                            .append(" ").append(temp.customer.getLastname()).append("\n");

                    ShipmentNode shipmentTemp = temp.customer.getShipmentHistory().head;
                    if (shipmentTemp == null) {
                        historyList.append("  Gönderi geçmişi bulunmamaktadır.\n");
                    } else {

                        while (shipmentTemp != null) {
                            Shipment shipment = shipmentTemp.shipment;
                            historyList.append("  Gönderi ID: ").append(shipment.getShipmentID())
                                    .append(" - Teslimat Süresi: ").append(shipment.getDeliveryTime())
                                    .append(" gün - Durum: ").append(shipment.getDeliveryStatus() ? "Teslim Edildi" : "Teslim Edilmedi")
                                    .append("\n");
                            shipmentTemp = shipmentTemp.next;
                        }
                    }


                    JTextArea textArea = new JTextArea(historyList.toString());
                    textArea.setEditable(false);
                    textArea.setColumns(40);
                    textArea.setRows(10);


                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Gönderi Geçmişi: " + temp.customer.getFirstname() + " " + temp.customer.getLastname(), JOptionPane.INFORMATION_MESSAGE);

                    customerFound = true;
                    break;
                }
                temp = temp.next;
            }

            if (!customerFound) {
                JOptionPane.showMessageDialog(null, "Bu müşteri ID'si ile ilgili geçmiş bulunamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Geçersiz müşteri ID'si.");
        }
    }



    private void handleUpdateStatus(ActionEvent e) {
        String shipmentID = JOptionPane.showInputDialog("Gönderi ID'sini giriniz:");
        if (shipmentID != null && !shipmentID.isEmpty()) {
            boolean found = false;
            CustomerNode temp = customerList.head;
            while (temp != null) {
                ShipmentNode shipmentTemp = temp.customer.getShipmentHistory().head;
                while (shipmentTemp != null) {
                    if (shipmentTemp.shipment.getShipmentID().equals(shipmentID)) {
                        shipmentTemp.shipment.setDeliveryStatus(true);
                        JOptionPane.showMessageDialog(null, "Gönderi teslim durumu başarıyla güncellendi.");
                        found = true;
                        break;
                    }
                    shipmentTemp = shipmentTemp.next;
                }
                if (found) break;
                temp = temp.next;
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "Gönderi bulunamadı.");
            }
        }
    }

    private void handleAddCustomer(ActionEvent e) {
        String firstname = JOptionPane.showInputDialog("Müşteri Adı:");
        String lastname = JOptionPane.showInputDialog("Müşteri Soyadı:");
        if (firstname != null && lastname != null) {
            Customer customer = customerList.addCustomer(firstname, lastname);
            JOptionPane.showMessageDialog(null, "Müşteri başarıyla eklendi.\nMüşteri ID'si: " + customer.getCustomerId());
        }
    }

    private void handleAddShipment(ActionEvent e) {
        City rootCity = deliveryTree.getRoot(); // Root şehir
        if (rootCity == null) {
            JOptionPane.showMessageDialog(null, "Şehir ağacı boş!");
            return;
        }


        ArrayList<String> cityNames = new ArrayList<>();
        for (City city : rootCity.getSubCities()) {
            cityNames.add(city.getCityName());
        }

        String selectedCity = (String) JOptionPane.showInputDialog(
                null, "Şehir Seçiniz:", "Şehir Seçimi",
                JOptionPane.PLAIN_MESSAGE, null, cityNames.toArray(), null
        );

        if (selectedCity == null) {
            JOptionPane.showMessageDialog(null, "Şehir seçilmedi. İşlem iptal edildi.");
            return;
        }


        City selectedCityNode = findCityInTree(rootCity, selectedCity);
        if (selectedCityNode == null || selectedCityNode.getSubCities().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bu şehirde ilçe bulunamadı.");
            return;
        }

        ArrayList<String> districtNames = new ArrayList<>();
        for (City district : selectedCityNode.getSubCities()) {
            districtNames.add(district.getCityName());

        }


        String selectedDistrict = (String) JOptionPane.showInputDialog(
                null, "İlçe Seçiniz:", "İlçe Seçimi",
                JOptionPane.PLAIN_MESSAGE, null, districtNames.toArray(), null
        );

        if (selectedDistrict == null) {
            JOptionPane.showMessageDialog(null, "İlçe seçilmedi. İşlem iptal edildi.");
            return;
        }


        String customerId = JOptionPane.showInputDialog("Müşteri ID'si:");
        if (customerId == null || customerId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Müşteri ID'si girilmedi.");
            return;
        }


        CustomerNode temp = customerList.head;
        boolean customerFound = false;
        while (temp != null) {
            if (temp.customer.getCustomerId().equals(customerId)) {
                Shipment shipment = new Shipment(selectedCityNode);
                temp.customer.getShipmentHistory().addShipment(shipment);
                shipmentStack.push(shipment);
                kargoKuyrugu.ekle(shipment);


                String message = "Gönderi başarıyla eklendi! Gönderi ID'si: " + shipment.getShipmentID() +
                        "\nŞehir: " + selectedCity + ", İlçe: " + selectedDistrict;
                JOptionPane.showMessageDialog(null, message);

                customerFound = true;
                break;
            }
            temp = temp.next;
        }

        if (!customerFound) {
            JOptionPane.showMessageDialog(null, "Müşteri bulunamadı.");
        }
    }



    private void handleSearchShipment(ActionEvent e) {
        String customerId = JOptionPane.showInputDialog("Teslim edilmiş gönderiyi aramak için müşteri ID'sini giriniz:");

        if (customerId != null && !customerId.isEmpty()) {
            List<Shipment> deliveredShipments = new ArrayList<>();
            CustomerNode temp = customerList.head;
            boolean customerFound = false;


            while (temp != null) {
                if (temp.customer.getCustomerId().equals(customerId)) {
                    customerFound = true;
                    ShipmentNode shipmentTemp = temp.customer.getShipmentHistory().head;
                    while (shipmentTemp != null) {
                        if (shipmentTemp.shipment.getDeliveryStatus()) {
                            deliveredShipments.add(shipmentTemp.shipment); // Teslim edilmiş gönderiyi ekle
                        }
                        shipmentTemp = shipmentTemp.next;
                    }
                    break;
                }
                temp = temp.next;
            }

            if (customerFound && !deliveredShipments.isEmpty()) {
                StringBuilder result = new StringBuilder("Teslim Edilmiş Gönderiler:\n");
                for (Shipment shipment : deliveredShipments) {
                    result.append("Gönderi ID: ").append(shipment.getShipmentID())
                            .append(" - Teslimat Süresi: ").append(shipment.getDeliveryTime())
                            .append(" gün\n");
                }
                JOptionPane.showMessageDialog(null, result.toString());
            } else if (!customerFound) {
                JOptionPane.showMessageDialog(null, "Bu müşteri ID'si ile ilgili gönderiler bulunamadı.");
            } else {
                JOptionPane.showMessageDialog(null, "Teslim edilmiş gönderi bulunamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Geçersiz müşteri ID'si.");
        }
    }



    private void handleSortShipments(ActionEvent e) {
        String customerId = JOptionPane.showInputDialog("Teslim edilmemiş gönderileri sıralamak için müşteri ID'sini giriniz:");

        if (customerId != null && !customerId.isEmpty()) {
            List<Shipment> undeliveredShipments = new ArrayList<>();
            CustomerNode temp = customerList.head;
            boolean customerFound = false;


            while (temp != null) {
                if (temp.customer.getCustomerId().equals(customerId)) {

                    customerFound = true;
                    ShipmentNode shipmentTemp = temp.customer.getShipmentHistory().head;
                    while (shipmentTemp != null) {
                        if (!shipmentTemp.shipment.getDeliveryStatus()) {
                            undeliveredShipments.add(shipmentTemp.shipment);
                        }
                        shipmentTemp = shipmentTemp.next;
                    }
                    break;
                }
                temp = temp.next;
            }

            if (customerFound && !undeliveredShipments.isEmpty()) {
                ShipmentSort.mergeSort(undeliveredShipments);
                StringBuilder sortedShipments = new StringBuilder("Teslim Edilmemiş Gönderiler (Sıralı):\n");
                for (Shipment shipment : undeliveredShipments) {
                    sortedShipments.append("Gönderi ID: ").append(shipment.getShipmentID())
                            .append(" - Teslimat Süresi: ").append(shipment.getDeliveryTime())
                            .append(" gün\n");
                }
                JOptionPane.showMessageDialog(null, sortedShipments.toString());
            } else if (!customerFound) {
                JOptionPane.showMessageDialog(null, "Bu müşteri ID'si ile ilgili gönderiler bulunamadı.");
            } else {
                JOptionPane.showMessageDialog(null, "Teslim edilmemiş gönderi bulunamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Geçersiz müşteri ID'si.");
        }
    }


    private void handleViewShipments(ActionEvent e) {

        String customerId = JOptionPane.showInputDialog("Kargoları teslimat süresine göre görüntülemek için müşteri ID'sini giriniz:");

        if (customerId != null && !customerId.isEmpty()) {
            StringBuilder shipmentsList = new StringBuilder();
            boolean customerFound = false;


            for (Shipment shipment : kargoKuyrugu.getHeap()) {
                CustomerNode temp = customerList.head;
                while (temp != null) {
                    if (temp.customer.getCustomerId().equals(customerId)) {
                        shipmentsList.append("Gönderi ID: ").append(shipment.getShipmentID())
                                .append(" Teslim Süresi: ").append(shipment.getDeliveryTime()+1).append(" gün\n");
                        customerFound = true;
                        break;
                    }
                    temp = temp.next;
                }
            }

            if (customerFound) {
                JOptionPane.showMessageDialog(null, shipmentsList.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Bu müşteri ID'sine ait teslimat bilgisi bulunamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Geçersiz müşteri ID'si.");
        }
    }




    private void handleViewRecentShipments(ActionEvent e) {

        String customerId = JOptionPane.showInputDialog("Son 5 gönderiyi görüntülemek için müşteri ID'sini giriniz:");

        if (customerId != null && !customerId.isEmpty()) {
            CustomerNode temp = customerList.head;
            boolean customerFound = false;

            while (temp != null) {
                if (temp.customer.getCustomerId().equals(customerId)) {
                    StringBuilder recentShipments = new StringBuilder();
                    recentShipments.append("Müşteri: ").append(temp.customer.getFirstname())
                            .append(" ").append(temp.customer.getLastname()).append("\n");


                    Stack tempStack = new Stack();
                    ShipmentNode shipmentTemp = temp.customer.getShipmentHistory().head;


                    int count = 0;
                    while (shipmentTemp != null && count < 5) {
                        Shipment shipment = shipmentTemp.shipment;
                        recentShipments.append("  Gönderi ID: ").append(shipment.getShipmentID())
                                .append(" - Teslimat Süresi: ").append(shipment.getDeliveryTime())
                                .append(" gün - Durum: ").append(shipment.getDeliveryStatus() ? "Teslim Edildi" : "Teslim Edilmedi")
                                .append("\n");
                        shipmentTemp = shipmentTemp.next;
                        count++;
                    }


                    JTextArea textArea = new JTextArea(recentShipments.toString());
                    textArea.setEditable(false);
                    textArea.setColumns(40);
                    textArea.setRows(10);


                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Son 5 Gönderi: " + temp.customer.getFirstname() + " " + temp.customer.getLastname(), JOptionPane.INFORMATION_MESSAGE);

                    customerFound = true;
                    break;
                }
                temp = temp.next;
            }

            if (!customerFound) {
                JOptionPane.showMessageDialog(null, "Bu müşteri ID'si ile ilgili gönderiler bulunamadı.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Geçersiz müşteri ID'si.");
        }
    }



    private void handleViewRoutes(ActionEvent e) {

        City rootCity = deliveryTree.getRoot();

        if (rootCity == null) {
            JOptionPane.showMessageDialog(null, "Şehir ağacı boş!");
            return;
        }


        StringBuilder routeInfo = new StringBuilder();
        routeInfo.append("Kargo Rotaları ve Teslimat Süreleri:\n");


        for (City city : rootCity.getSubCities()) {
            routeInfo.append("Şehir: ").append(city.getCityName())
                    .append(" - Teslimat Süresi: ").append(city.getDeliveryTime()).append(" gün\n");


            for (City district : city.getSubCities()) {
                routeInfo.append("  İlçe: ").append(district.getCityName())
                        .append(" - Teslimat Süresi: ").append(district.getDeliveryTime()).append(" gün\n");
            }
        }


        JTextArea textArea = new JTextArea(routeInfo.toString());
        textArea.setEditable(false);
        textArea.setColumns(40);
        textArea.setRows(10);


        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "Kargo Rotaları", JOptionPane.INFORMATION_MESSAGE);
    }

    private City findCityInTree(City rootCity, String cityName) {

        if (rootCity == null || rootCity.getCityName().equals(cityName)) {
            return rootCity;
        }

        for (City subCity : rootCity.getSubCities()) {
            City foundCity = findCityInTree(subCity, cityName);
            if (foundCity != null) {
                return foundCity;
            }
        }

        return null;
    }




}

