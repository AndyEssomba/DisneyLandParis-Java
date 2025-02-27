package src;

import java.time.LocalDateTime;

public class SkipLine {
    private LocalDateTime accessTime;
    private String attraction;
    private int dayNumber;

    public SkipLine(LocalDateTime accessTime, String attraction, int dayNumber){
        this.accessTime = accessTime;
        this.attraction = attraction;
        this.dayNumber = dayNumber;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime =
                accessTime;
    }

    public String getAttraction() {
        return attraction;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public double calculatePriceSkip() {
        return 20;
    }

    public String getDetails() {
        return " - accessTime: " + accessTime + "\n"
                + " - attraction: " + attraction + "\n"
                + " - skip line price: " + calculatePriceSkip() + " €\n";
    }
}
