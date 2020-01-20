package mn.edu.num.tuvshinbileg.lab07.model;

public class Entry {
    String timeFrom;
    String timeTo;
    String windDirection;
    String temperature;
    String clouds;

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", temperature='" + temperature + '\'' +
                ", clouds='" + clouds + '\'' +
                '}';
    }
}
