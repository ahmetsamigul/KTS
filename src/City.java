import java.util.ArrayList;
import java.util.List;

public class City {
    private String cityName;
    private int cityId;
    private int deliveryTime;
    private List<City> subCities;
    private String routeInfo;

    public City(String cityName, int cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
        this.subCities = new ArrayList<>();
        this.deliveryTime = 0;
        this.routeInfo = "";
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<City> getSubCities() {
        return subCities;
    }

    public void addSubCity(City city) {
        this.subCities.add(city);
    }

    public void setRouteInfo(String routeInfo) {
        this.routeInfo = routeInfo;
    }

    public boolean hasRoute() {
        return routeInfo != null && !routeInfo.isEmpty();
    }

    public String getRouteInfo() {
        return routeInfo;
    }
}
