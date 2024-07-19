
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import MyUtils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FlightManager {

    ArrayList<Crew> crewList = new ArrayList<>();
    Utils u = new Utils();
    ArrayList<Flight> flightList = new ArrayList<>();
    ArrayList<Reservation> reserveList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void addNewFlight() {
        int check;
        int repeat;
        do {
            String fnumb;
            do {
                fnumb = Utils.getStringreg("Enter flight number(Fxxxx): ",
                        "F\\d{4}$", "**Flight number cant be empty", "**Please enter the right flight number format!");
                repeat = 0;
                for (Flight f : flightList) {
                    if (fnumb.equals(f.getFlightNumber())) {
                        repeat = 1;
                        System.out.println("**Flight number already taken ");
                        break;
                    }
                }
            } while (repeat == 1);
            int invalid = 0;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm");
            String FDdate;
            String FAdate;
            LocalDateTime t1;
            LocalDateTime t2;
            do {
                FDdate = Utils.getStringreg("Enter departure time(dd/MM/yyyyThh:mm):",
                        "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/\\d{4}T((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])",
                        "**Departure time cant be empty", "**Please enter the right departure time format");
                t1 = LocalDateTime.parse(FDdate, formatter);
                if (t1.isBefore(now)) {
                    System.out.println("**Invalid date.Departure date has already passed");
                    invalid = 1;
                } else if (t1.isBefore(now.plus(8, ChronoUnit.HOURS))) {
                    System.out.println("**Invalid time.Flight must be add beforehand atleast 8 hours");
                    invalid = 1;
                } else {
                    invalid = 0;
                }
            } while (invalid == 1);

            do {
                FAdate = Utils.getStringreg("Enter arrival time(dd/MM/yyyyThh:mm):",
                        "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/\\d{4}T((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])",
                        "**Arrival time cant be empty", "**Please enter the right arrival time format");
                t2 = LocalDateTime.parse(FAdate, formatter);
                if (t2.isBefore(t1)) {
                    System.out.println("**Invalid time.Departure time is after arrival time");
                    invalid = 1;
                } else if (t1.equals(t2)) {
                    System.out.println("**Invalid time.Arrival time is the same as departure time");
                    invalid = 1;
                } else {
                    invalid = 0;
                }
            } while (invalid == 1);
            String from = Utils.getString("Enter departure city: ", "**Departure city cant be empty");
            String des = Utils.getString("Enter destination city: ", "**Destination city cant be empty");
            int seats = Utils.getInt("Enter number of seats: ", 1);
            Duration d = Duration.between(t1, t2);
            long hours = d.toHours();
            long minutes = d.toMinutes() % 60;
            String dura = String.format("%02d:%02d", hours, minutes);
            System.out.println("Dura:" + dura);
            Flight f = new Flight(fnumb, from, des, FDdate, dura, seats);
            flightList.add(f);
            save1();
            System.out.println("Add more flight?(Y/N)");
            String respond = sc.nextLine();
            if (respond.equalsIgnoreCase("yes") || respond.equalsIgnoreCase("y")) {
                check = 1;
            } else if (respond.equalsIgnoreCase("no") || respond.equalsIgnoreCase("n")) {
                check = 2;
            } else {
                return;
            }
        } while (check == 1);
    }
    static String reservationID = "000000";

    public void addReservation() {
        System.out.println("**RESERVATION ID IS IMPORTANT AND NECCESSARY FOR YOU TO CHECK IN AND CHOOSE YOUR SEAT**");
        String name = "";
        String contact;
        int id = Integer.parseInt(reservationID);
        id++;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm");
        String regex = "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/(\\d{4})";
        String From = Utils.getString("Enter departure city: ", "**Departure city cant be empty");
        String To = Utils.getString("Enter destination city: ", "**Destination city cant be empty");
        String date = Utils.getStringreg("Enter flight departure date: ", regex, "**Flight date cant be empty", "**Please enter the right date format");
        LocalDate d1 = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();
        if (d1.isBefore(now)) {
            System.out.println("Departure date is in the past.");
        }
        int count = 0;
        int index = 0;
        int FoundF = 0;
        ArrayList<String> yourFnumb = new ArrayList();
        reservationID = String.format("%07d", id);
        System.out.println("Available flight: ");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s %-20s %-20s %-10s %-3s", "Fno", "From", "To", "Departure Time", "Duration", "Available"));
        System.out.println("----------------------------------------------------------------------------------------");
        for (Flight f : flightList) {
            LocalDateTime dateTime = LocalDateTime.parse(f.getDepartureTime(), formatter2);
            LocalDate d2 = dateTime.toLocalDate();
            if (f.getAvailableSeats() > 0 && f.getDepartureCity().equalsIgnoreCase(From)
                    && f.getDestinationCity().equalsIgnoreCase(To) && d1.isEqual(d2)) {
                System.out.println(f.toString());
                yourFnumb.add(f.getFlightNumber());
                FoundF = 1;
            }
        }
        System.out.println("----------------------------------------------------------------------------------------");
        if (FoundF == 0) {
            System.out.println("**No flight suitable");
            id = Integer.parseInt(reservationID);
            id--;
            reservationID = String.format("%07d", id);
            return;
        }
        System.out.println("ReservationID: " + reservationID);
        String fnumb;
        do {
            fnumb = Utils.getStringreg("Enter flight number you'd like to reserve:",
                    "F\\d{4}$", "**Flight number cant be empty", "**Please enter the right flight number format!");
            for (Flight f : flightList) {
                if (fnumb.equalsIgnoreCase(f.getFlightNumber()) && yourFnumb.contains(fnumb)) {
                    count = 1;
                }
            }
            if (count == 0) {
                System.out.println("**Please choose a flight from the available flights.");
            }
        } while (count == 0);
        if (count == 1) {
            name = Utils.getString("Enter customer name:", "**Customer name cant be empty");
            int c = (int) u.ValueInRange("Contacts detail:\n1.Phone number:\n2.Email\nYour choice:", 1, 2, "**Please choose 1 or 2");
            if (c == 1) {
                contact = Utils.getString("Enter contact phone number:", "**Contact phone number cant be empty");
            } else {
                contact = Utils.getString("Enter contact email:", "**Contact email cant be empty ");
            }
            for (Flight f : flightList) {
                if (f.getAvailableSeats() > 0 && f.getFlightNumber().equalsIgnoreCase(fnumb)) {
                    index = flightList.indexOf(f);
                }
            }
            flightList.get(index).setAvailableSeats(flightList.get(index).getAvailableSeats() - 1);
            Reservation r = new Reservation(reservationID, flightList.get(index).getFlightNumber(), name, contact);
            reserveList.add(r);
            System.out.println("----------------------------------------------------------------");
            System.out.println("RESERVATION DETAILS:\n ");
            System.out.println("Flight no: " + fnumb);
            System.out.println("From: " + flightList.get(index).getDepartureCity() + "\tTo: " + flightList.get(index).getDestinationCity());
            System.out.println("Date&Time: " + flightList.get(index).getDepartureTime() + "\n");
            System.out.println("Customer: " + name + "\tContact: " + contact);
            System.out.println("Reservation ID: " + reservationID);
        }
        save1();
    }

    public void checkInAndSeatsAllocation() {
        int exist = 0;
        String reID = Utils.getString("Enter reservation ID:", "**ReservationID cant be empty");
        for (Reservation r : reserveList) {
            if (reID.equalsIgnoreCase(r.getID())) {
                exist = 1;
                if (r.getSeats() != null) {
                    System.out.println("**You're already checked in");
                    return;
                }
            }
        }
        if (exist == 0) {
            System.out.println("**Reservation not exist");
            return;
        }
        Flight uf = null;
        int count = -1;
        Reservation rf = null;
        for (Reservation r : reserveList) {
            if (reID.equalsIgnoreCase(r.getID())) {
                for (Flight f : flightList) {
                    if (r.getFnumb().equalsIgnoreCase(f.getFlightNumber())) {
                        uf = f;
                    }
                }
                if (uf == null) {
                    System.out.println("**No flight found for this reservation.");
                    return;
                }
                count = 1;
                rf = r;
            }
        }
        int counting = uf.getCharting();
        if (count == 1) {
            System.out.println("Seating chart:");
            System.out.println();
            for (int i = 1; i <= uf.getRows(); i++) {
                String formattedNumber = String.format("%02d", i);
                for (char j = 'A'; j <= 'F'; j++) {
                    String seat = formattedNumber + j;
                    String status = uf.available.contains(seat) ? "(A)" : "(T)";
                    if (counting >= 0) {
                        System.out.print(seat + status + "  ");
                    }
                    counting--;
                    if (counting == 0) {
                        break;
                    }
                }
                System.out.println();
            }

            System.out.println("(A):Available\t(T):Taken");
            String urseats = Utils.getStringreg("Choose your seats:", "^[0-9]{2}[A-F]$", "**You need to choose a seat", "**Seat does not exist");
            if (uf.getAvailable().contains(urseats)) {
                rf.setSeats(urseats);
                uf.getAvailable().remove(urseats);
            } else {
                System.out.println("**Seat not available");
            }
            if (rf.getSeats() != null) {
                System.out.println("----------------------------------------------------------------");
                System.out.println("BOARDING PASS: ");
                System.out.println();
                System.out.println("Flight no: " + uf.getFlightNumber());
                System.out.println();
                System.out.println("Departure time: " + uf.getDepartureTime());
                System.out.println("From: " + uf.departureCity + " \t\tTo :" + uf.getDestinationCity());
                System.out.println("Flight duration: " + uf.getDuration());
                System.out.println();
                System.out.println("Passenger: " + rf.getPname() + "\t\tSeat:" + rf.getSeats());
                save1();
            }
        } else {
            System.out.println("**There's no such reservationID");
        }

        save1();
    }

    public void ManageFlightSchedule() {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s %-20s %-20s %-10s %-3s", "Fno", "From", "To", "Departure Time", "Duration", "Available"));
        System.out.println("----------------------------------------------------------------------------------------");
        for (Flight f : flightList) {
            System.out.println(f);
        }
        System.out.println("----------------------------------------------------------------------------------------");
        String fnumb = Utils.getStringreg("Enter flight number:", "^F\\d{4}$$",
                "**Flight number cant be empty", "**Please enter the right flight number format!");
        Flight cf = null;
        for (Flight f : flightList) {
            if (fnumb.equals(f.getFlightNumber())) {
                cf = f;
            }
            if (cf != null) {
                cf.toString();
            }
        }
        if (cf != null) {
            cf.toString();
            System.out.println("Would you like to change flight details?(Y/N)");
            String respond = sc.nextLine();
            if (respond.equalsIgnoreCase("y") || respond.equalsIgnoreCase("yes")) {
                String newdata1 = Utils.getStringreg("Enter new departure time(dd/MM/yyyyThh:mm):",
                        "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/\\d{4}T((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])",
                        "**Departure time cant be empty", "**Please enter the right departure time format");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm");
                LocalDateTime l1 = LocalDateTime.parse(newdata1, formatter);
                LocalDateTime now = LocalDateTime.now();
                if (l1.isBefore(now)) {

                    System.out.println("**Invalid time.Departure time is in the past");
                    return;
                }
                String newdata2 = Utils.getStringreg("Enter new arrival time(dd/MM/yyyyThh:mm):",
                        "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/\\d{4}T((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])",
                        "**Arrival time cant be empty", "**Please enter the right arrival time format");
                LocalDateTime l2 = LocalDateTime.parse(newdata2, formatter);

                if (l2.isBefore(l1)) {
                    System.out.println("**Invalid time.Departure time is after arrival time");
                    return;
                } else {
                    cf.setDepartureTime(newdata1);
                    cf.setArrivalTime(newdata2);
                    Duration d = Duration.between(l1, l2);
                    long hours = d.toHours();
                    long minutes = d.toMinutes() % 60;
                    String dura = String.format("%02d:%02d", hours, minutes);
                    cf.setDuration(dura);
                }
            }
        } else {
            System.out.println("**Flight not exist");
        }
        save1();
    }

    public void CrewManagement() {
        int more = 0;
        int count = 0;
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s %-20s %-20s %-10s %-3s", "Fno", "From", "To", "Departure Time", "Duration", "Available"));
        System.out.println("----------------------------------------------------------------------------------------");
        for (Flight f : flightList) {
            System.out.println(f);
        }
        System.out.println("----------------------------------------------------------------------------------------");
        String fnumb = Utils.getStringreg("Enter flight assigned:", "^F\\d{4}$$",
                "**Flight assigned cant be empty", "**Please enter the right flight number format!");
        for (Flight f : flightList) {
            if (fnumb.equals(f.getFlightNumber())) {
                count = 1;
            }
        }
        if (count == 0) {
            System.out.println("**Flight not found");
            return;
        }
        String names;
        String roles = "";
        do {
            names = Utils.getString("Enter crew member's name: ", "**Crew member name cant be empty");
            int assign = (int) u.ValueInRange("Crew member's roles: \n1.Pilots\n2.Flight attendant\n3.Ground staff",
                    1, 3, "**Please choose from 1 to 3");
            switch (assign) {
                case 1:
                    roles = "Pilot";
                    break;
                case 2:
                    roles = "Flight attendant";
                    break;
                case 3:
                    roles = "Ground staff";
                    break;
            }
            Crew c = new Crew(names, roles, fnumb);
            crewList.add(c);
            System.out.println("Do you want to add more crew in this flight?");
            String res = sc.nextLine();
            if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("Yes")) {
                more = 1;
            } else {
                more = 0;
            }
        } while (more == 1);
        save1();
    }

    public void administator() {
        int choice;
        do {
            System.out.println("1.Manage flight schedule");
            System.out.println("2.Crew assignments");
            System.out.println("3.Return");
            choice = (int) u.ValueInRange("Your choice: ", 1, 3, "**Choose 1 or 3");
            switch (choice) {
                case 1:
                    System.out.println("----------------------------------------------------------------------------------------");
                    ManageFlightSchedule();
                    System.out.println("----------------------------------------------------------------------------------------");
                    break;
                case 2:
                    System.out.println("----------------------------------------------------------------");
                    CrewManagement();
                    System.out.println("----------------------------------------------------------------");
                    break;
            }
        } while (choice < 3);
    }

    public void saveToFileDAT(String fname, ArrayList<?> data) {
        File f = new File(fname);
        try {
            FileOutputStream fos = new FileOutputStream(fname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            System.out.println(ioe + fname);
        }
    }

    public ArrayList<?> loadFromFileDAT(String fname) {
        ArrayList<?> data = null;
        try {
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (ArrayList<?>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            System.out.println(ioe + fname);
        } catch (ClassNotFoundException c) {
            System.out.println("**Class not found");
            System.out.println(c + fname);
        }
        return data;
    }

    public void save1() {
        saveToFileDAT("Flight.dat", flightList);
        saveToFileDAT("Reservation.dat", reserveList);
        saveToFileDAT("Assigntments.dat", crewList);
    }

    public void save() {
        saveToFileDAT("Flight.dat", flightList);
        saveToFileDAT("Reservation.dat", reserveList);
        saveToFileDAT("Assigntments.dat", crewList);
        System.out.println("saved");
    }

    public void load() {
        File f0 = new File("Flight.dat");
        File f1 = new File("Reservation.dat");
        File f2 = new File("Assigntments.dat");
        if (f0.exists()) {
            flightList = (ArrayList<Flight>) loadFromFileDAT("Flight.dat");
        }
        if (f1.exists()) {
            reserveList = (ArrayList<Reservation>) loadFromFileDAT("Reservation.dat");
        }
        if (f2.exists()) {
            crewList = (ArrayList<Crew>) loadFromFileDAT("Assigntments.dat");
        }
        for (Reservation r : reserveList) {
            for (Flight f : flightList) {
                if (r.getFnumb().equalsIgnoreCase(f.getFlightNumber())) {
                    if (f.getAvailable().contains(r.getSeats())) {
                        f.getAvailable().remove(r.getSeats());
                    }
                }
            }
        }
        if (!reserveList.isEmpty()) {
            Reservation lastReservation = reserveList.get(reserveList.size() - 1);
            reservationID = lastReservation.getID();
        }
    }

    public void PrintAllDesDate() {
        if (flightList.isEmpty()) {
            System.out.println("**No flight at the moment");
        } else if (!flightList.isEmpty()) {
            flightList.sort(new Comparator<Flight>() {
                @Override
                public int compare(Flight t, Flight t1) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm");
                    LocalDateTime l1 = LocalDateTime.parse(t.getDepartureTime(), formatter);
                    LocalDateTime l2 = LocalDateTime.parse(t1.getDepartureTime(), formatter);
                    return l2.compareTo(l1);
                }
            });
            System.out.println();
            
            for (Flight flight : flightList) {
                System.out.println();
                System.out.println();
                System.out.println("-----------------------------------------"+flight.getFlightNumber()
                +"-----------------------------------------");                
                System.out.println(String.format("%-10s %-20s %-20s %-20s %-10s %-3s", "Fno", "From", "To", "Departure Time", "Duration", "Available"));
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(flight);  
                System.out.println();
                System.out.println("**Reservations details of fno: " + flight.getFlightNumber()+"**");
                System.out.println(String.format("%-10s %-10s %-30s %-30s", "ReID", "ReFnumb", "Passenger", "Contact details"));
                System.out.println("----------------------------------------------------------------------------------------");
                int countRe=0;
                for (Reservation r : reserveList) {
                    if (r.getFnumb().equalsIgnoreCase(flight.flightNumber)) {
                        System.out.println(r.toString());
                        countRe=1;
                    }
                }
                if(countRe==0) System.out.println("Flight no: "+flight.getFlightNumber() + " has no reservation at the moments");
                System.out.println();
                System.out.println("**Crew details of fno:"+flight.getFlightNumber()+"**");
                System.out.println(String.format("%-10s %-30s %-30s","Fno","Roles","Names"));
                System.out.println("----------------------------------------------------------------------------------------");
                int countCre=0;
                for(Crew c : crewList){
                    if(c.assignedFlight.equalsIgnoreCase(flight.flightNumber)) {System.out.println(c.toString());
                    countCre=1;}
                }
                if(countCre==0) System.out.println("Flight no: "+flight.getFlightNumber() + " has no crew member at the moments");
                System.out.println();
                System.out.println();
            }            
            System.out.println("------------------------------------------DONE------------------------------------------");
            System.out.println("PRESS ENTER TO CONTINUE");
        }
    }
}
