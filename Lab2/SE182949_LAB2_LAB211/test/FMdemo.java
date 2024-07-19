/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class FMdemo {
        /*
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
                FDdate = Utils.getStringreg("Enter departure time(31d/12m/yyyyT23h:59p):",
                        "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/\\d{4}T((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])",
                        "**Departure time cant be empty", "**Please enter the right departure time format");
                t1 = LocalDateTime.parse(FDdate, formatter);
                if (t1.isBefore(now)) {
                    System.out.println("**Invalid date.Departure date has already passed");
                    invalid = 1;
                } else {
                    invalid = 0;
                }
            } while (invalid == 1);

            do {
                FAdate = Utils.getStringreg("Enter arrival time(31d/12m/yyyyT23h:59p):",
                        "((0[1-9])|([12][0-9])|(3[01]))/((0[1-9])|(1[012]))/\\d{4}T((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])",
                        "**Arrival time cant be empty", "**Please enter the right arrival time format");
                t2 = LocalDateTime.parse(FAdate, formatter);
                if (t2.isBefore(t1)) {
                    System.out.println("**Invalid time.Departure time is after arrival time");
                    invalid = 1;
                }else if(t1.equals(t2)){
                    System.out.println("**Invalid time.Arrival time is the same as departure time");
                    invalid=1;
                } else {
                    invalid = 0;
                }
            } while (invalid == 1);
            String from = Utils.getString("Enter departure city: ", "**Departure city cant be empty");
            String des = Utils.getString("Enter destination city: ", "**Destination city cant be empty");
            Duration d = Duration.between(t1, t2);
            long hours = d.toHours();
            long minutes = d.toMinutes() % 60;
            String dura = String.format("%02d:%02d", hours, minutes);
            System.out.println("Dura:" + dura);
            Flight f = new Flight(fnumb, from, des, FDdate, dura);
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
     */
       /*
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

        if (count == 1) {
            for (int i = 1; i <= 20; i++) {
                String formattedNumber = String.format("%02d", i);
                String status;
                for (char j = 'A'; j <= 'F'; j++) {
                    String seat = formattedNumber + j;
                    if (uf.available.contains(seat)) {
                        status = "(A)";
                    } else {
                        status = "(T)";
                    }
                    System.out.print(seat + status + "  ");
                }
                System.out.println();
            }
            System.out.println("(A):Available\t(T):Taken");
            String urseats = Utils.getStringreg("Choose your seats:", "^([01][1-9]|10|1[1-9]|20)[A-F]$", "**You need to choose a seat", "**Seat does not exist");
            if (uf.getAvailable().contains(urseats)) {
                rf.setSeats(urseats);
                uf.getAvailable().remove(urseats);                
            } else {
                System.out.println("**Seat is taken");
            }
            if (rf.getSeats() != null) {
                System.out.println("----------------------------------------------------------------");
                System.out.println("BOARDING PASS: ");
                System.out.println();
                System.out.println("Flight no: " + uf.getFlightNumber());
                System.out.println();
                System.out.println("Departure time: " + uf.getDepartureTime());
                System.out.println("From: " + uf.departureCity + " \t\tTo :" + uf.getDestinationCity());
                System.out.println("Flight duration: "+uf.getDuration());
                System.out.println();
                System.out.println("Passenger: " + rf.getPname() + "\t\tSeat:" + rf.getSeats());
                save1();
            }
        } else {
            System.out.println("**There's no such reservationID");
        }
        save1();
    }
     */
    //THESE ONE ARE IN FLIGHT CLASS
    //int availableSeats=120;//modify = available.size()
    /*    public void seats(ArrayList available) {
        for (int i = 1; i <= 20; i++) {
            String format = String.format("%02d", i);
            for (char j = 'A'; j <= 'F'; j++) {
                String seats = format + j;
                available.add(seats);
            }
        }
    }
*/   
}
