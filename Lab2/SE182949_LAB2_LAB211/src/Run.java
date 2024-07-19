
import MyUtils.Utils;
import java.util.Scanner;

public class Run extends FlightManager {

    public static void main(String[] args) {
        String choice;
        FlightManager fm = new FlightManager();
        Utils function = new Utils();
        Scanner sc = new Scanner(System.in);
        do {
            fm.load();
            System.out.println("---------------------------Main menu---------------------------");
            System.out.println("1.Flight schedule management");
            System.out.println("2.Passenger reservation and booking");
            System.out.println("3.Passenger check-in and seat allocation");
            System.out.println("4.Crew management and assignments");
            System.out.println("5.Print all lists from file");
            System.out.println("6.Save to file");
            System.out.println("Other-Quit.");
            System.out.println("----------------------------------------------------------------");
            System.out.print("Your choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("----------------------------------------------------------------");
                    fm.addNewFlight();
                    System.out.println("----------------------------------------------------------------");
                    break;
                case "2":
                    System.out.println("----------------------------------------------------------------");
                    fm.addReservation();
                    System.out.println("----------------------------------------------------------------");
                    break;
                case "3":
                    System.out.println("----------------------------------------------------------------");
                    fm.checkInAndSeatsAllocation();
                    System.out.println("----------------------------------------------------------------");
                    break;
                case "4":
                    System.out.println("----------------------------------------------------------------");
                    fm.administator();
                    System.out.println("----------------------------------------------------------------");
                    break;
                case "6":
                    System.out.println("----------------------------------------------------------------");
                    fm.save();
                    System.out.println("----------------------------------------------------------------");
                    break;
                case "5":
                    System.out.println("----------------------------------------------------------------------------------------");
                    fm.load();
                    fm.PrintAllDesDate();
                    sc.nextLine();
                    break;
                default:
                    System.out.println("Closed");
                    choice = "7";                    
                    break;
            }
        } while (choice != "7");
    }
}
