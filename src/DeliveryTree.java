public class DeliveryTree {
    private City root;

    public DeliveryTree(City root) {
        this.root = root;
    }

    public City getRoot() {
        return root;
    }

    public void setRoot(City root) {
        this.root = root;
    }

    public void assignDeliveryTimes() {
        assignDeliveryTimesRecursive(root, 0);
    }

    private void assignDeliveryTimesRecursive(City city, int depth) {

        city.setDeliveryTime(depth + 1);

        for (City subCity : city.getSubCities()) {
            assignDeliveryTimesRecursive(subCity, depth + 1);
        }
    }

    public void displayTree() {
        displayTreeRecursive(root, 0);
    }

    private void displayTreeRecursive(City city, int depth) {

        if (city.getCityId() == 0)
            System.out.println(" ".repeat(depth ) + city.getCityName());
        else
            System.out.println(" ".repeat(depth ) + city.getCityName() + " (ID: " + city.getCityId() + ", Teslimat Süresi: " + city.getDeliveryTime() + " gün)");

        for (City subCity : city.getSubCities()) {
            displayTreeRecursive(subCity, depth + 1);
        }
    }
}