
import java.io.Serializable;
import java.util.ArrayList;
public class Flight implements Serializable {
    String flightNumber;
    String departureCity;
    String destinationCity;
    String departureTime;
    String arrivalTime;
    
    int availableSeats;//for reservation
    double rows;
    int charting;
    ArrayList<String> available;
    String duration;
    public Flight(String flightNumber, String departureCity, String destinationCity, String departureTime, String duration,int availableSeats) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.duration = duration;
        this.available = new ArrayList<>();
        this.availableSeats=availableSeats;
        this.charting = availableSeats;//create the maps 
        this.rows= Math.ceil((double)(availableSeats)/6);
        seats(available);
    }

    public void seats(ArrayList available) {
        int count=availableSeats;
        for (int i = 1; i <= Math.ceil((double)(availableSeats)/6); i++) {
            String format = String.format("%02d", i);
            for (char j = 'A'; j <= 'F'; j++) {
                String seats = format + j;
                if(count>=0) available.add(seats);
                count--;
                if(count==0) break;
            }
        }
    }

    public ArrayList<String> getAvailable() {
        return available;
    }
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public String getDepartureCity() {
        return departureCity;
    }
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }
    public String getDestinationCity() {
        return destinationCity;
    }
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

   public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void removeSeat(String seat) {
        available.remove(seat);
    }

    public double getRows() {
        return rows;
    }

    public void setRows(double rows) {
        this.rows = rows;
    }

    public int getCharting() {
        return charting;
    }

    public void setCharting(int charting) {
        this.charting = charting;
    }
    
    @Override
    public String toString() {
        String result = String.format("%-10s %-20s %-20s %-20s %-10s %-3s", flightNumber, departureCity, destinationCity, departureTime, duration,availableSeats);
        return result;
    }
}
