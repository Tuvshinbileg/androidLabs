package mn.edu.num.tuvshinbileg.lab07.model;

public class Weather {
    private String coordLon;
    private String coordLat;
    private String cityName;
    private String weatherDescription;
    private String temp;
    private String date;

    public Weather(String coordLon, String coordLat, String cityName, String weatherDescription, String temp,String date) {
        this.coordLon = coordLon;
        this.coordLat = coordLat;
        this.cityName = cityName;
        this.weatherDescription = weatherDescription;
        this.temp = temp;
        this.date=date;
    }

    public String getCoordLon() {
        return coordLon;
    }

    public void setCoordLon(String coordLon) {
        this.coordLon = coordLon;
    }

    public String getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(String coordLat) {
        this.coordLat = coordLat;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "coordLon='" + coordLon + '\'' +
                ", coordLat='" + coordLat + '\'' +
                ", cityName='" + cityName + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", temp='" + temp + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
