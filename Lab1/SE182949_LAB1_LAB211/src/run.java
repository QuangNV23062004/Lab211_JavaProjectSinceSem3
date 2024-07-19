
import java.util.Scanner;
import MyUtils.Utils;
public class run extends OrderManager {

    public static void main(String[] args) {
        OrderManager f = new OrderManager();
        int choice, choice2;
        Scanner sc = new Scanner(System.in);
        Utils function = new Utils();
        do {
            f.LoadFromFile();
            System.out.println("------------Main menu------------");
            System.out.println("1. Manage products");
            System.out.println("2. Manage Warehouse");
            System.out.println("3. Report");
            System.out.println("4. Store data to files");
            System.out.println("5. Close the application");
            System.out.println("---------------------------------");
            choice = (int) function.ValueInRange("Your choice :", 1, 5, "Please choose from 1 to 5");
            switch (choice) {
                case 1:
                    do {
                        System.out.println("------------Sub menu------------");
                        System.out.println("1.1. Add a product.");
                        System.out.println("1.2. Update product information.");
                        System.out.println("1.3. Delete product.");
                        System.out.println("1.4. Show all product.");
                        System.out.println("1.5. Back to main menu.");
                        System.out.println("--------------------------------");
                        choice2 = (int) function.ValueInRange("Your choice :", 1, 5, "Please choose from 1 to 5");
                        System.out.println("---------------------------------");                       
                        switch (choice2) {
                            case 1:
                                f.AddProduct();
                                break;
                            case 2:
                                f.updateProduct();
                                f.SaveToFile();
                                break;
                            case 3:
                                f.removeProduct();
                                break;
                            case 4:
                                f.ListProduct();
                                break;
                        }

                    } while (choice2 < 5);
                    
                    break;
                case 2:
                    do {
                        System.out.println("------------Sub menu------------");
                        System.out.println("2.1. Create an import receipt.");
                        System.out.println("2.2. Create an export receipt.");
                        System.out.println("2.3. Back to main menu.");
                        System.out.println("--------------------------------");
                        choice2 = (int) function.ValueInRange("Your choice :", 1, 3, "Please choose from 1 to 3");
                        System.out.println("---------------------------------");
                        switch(choice2){
                            case 1:f.AddOrder(choice2);
                            break;
                            case 2:f.AddOrder(choice2);
                            break;
                        }
                    } while (choice2 < 3);
                    break;
                case 3:
                    do {
                        System.out.println("------------Sub menu------------");
                        System.out.println("3.1. Products that have expired.");
                        System.out.println("3.2. The products that the store is selling.");
                        System.out.println("3.3. Products that are running out of stock (sorted in ascending order).");
                        System.out.println("3.4. Import/export receipt of a product.");
                        System.out.println("3.5. Back to main menu.");
                        System.out.println("--------------------------------");
                        choice2 = (int) function.ValueInRange("Your choice :", 1, 12, "Please choose from 1 to 5");
                        System.out.println("---------------------------------");
                        switch(choice2){
                            case 1:
                                f.Expiration();
                            break;
                            case 2:
                                f.StoreSelling();
                                break;
                            case 3:
                                f.OutOfOrder();
                            break;
                            case 4:
                                f.LoadFromFile();
                                f.ProductInOrder();
                            break;
                        }
                    } while (choice2 < 5);
                    break;
                case 4:
                    f.save();
                    break;
            }
        } while (choice < 5);

    }
}
