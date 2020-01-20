package mn.edu.num.tuvshinbileg.lab07.model;

import androidx.annotation.NonNull;

public class CurrentDay {
    private String date;
    private String temp;
    private String description;
    private String windSpeed;

    public CurrentDay(String date, String temp, String description, String windSpeed) {
        this.date = date;
        this.temp = temp;
        this.description = description;
        this.windSpeed = windSpeed;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "CurrentDay{" +
                "date='" + date + '\'' +
                ", temp='" + temp + '\'' +
                ", description='" + description + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                '}';
    }
}
