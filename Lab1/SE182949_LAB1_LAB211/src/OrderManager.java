
import MyUtils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class OrderManager {

    Utils function = new Utils();
    Scanner sc = new Scanner(System.in);
    ArrayList<Product> Product = new ArrayList<>();
    ArrayList<Order> Order = new ArrayList<>();

    public boolean CheckCode(String code) {
        for (Product product : Product) {
            if (product.getProductCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public void AddProduct() {
        int flag = 1;
        int type;
        do {
            String Pcode;
            do {

                type = (int) function.ValueInRange("Add:\n" + "1.DailyProduct\n" + "2.LongShelfProduct\n" + "Your choice: ", 1, 2, "Choose from 1 or 2");
                System.out.println("---------------------------------");
                Pcode = Utils.getString("Enter product code: ", "Product code cant be empty");
                if (CheckCode(Pcode)) {
                    System.out.println("Product code is taken");
                }
            } while (CheckCode(Pcode));
            String Pname = Utils.getString("Enter product name:", "Product name cant be empty");
            if (type == 1) {
                String unit = Utils.getString("Enter product unit: ", "Product unit cant be empty");

                String size = Utils.getString("Enter product size: ", "Product size cant be empty");
                DailyProduct p = new DailyProduct(Pcode, Pname, 0, unit, size);
                Product.add(p);
                SaveToFile();
            } else if (type == 2) {
                String ManufactureDay = Utils.getStringreg("Enter product manufacture date(DD/MM/YYYY): ", "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", "Manufacture day cant be empty", "Invalid manufacture day");

                String expirationDay = Utils.getStringreg("Enter product expiration date(DD/MM/YYYY): ", "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", "Expiration day cant be empty", "Invalid expiration day");
                LongShelfProduct p = new LongShelfProduct(Pcode, Pname, 0, ManufactureDay, expirationDay);
                Product.add(p);
                SaveToFile();
            }

            System.out.println("Continue adding product?(Y/N)");
            String respond = sc.nextLine();
            if (respond.equalsIgnoreCase("y") || respond.equalsIgnoreCase("yes")) {
                flag = 1;
            } else {
                flag++;
            }
        } while (flag == 1);
    }

    public void updateProduct() {
        int check = 0;
        String updateCode;
        String newName = "";
        int newQuantity = 0;
        String other1 = "";
        String other2 = "";
        updateCode = Utils.getString("Product code to update: ", "Product code cant be empty");
        int UpdateIndex = -1;
        for (int i = 0; i < Product.size(); i++) {
            if (Product.get(i).getProductCode().equalsIgnoreCase(updateCode)) {
                UpdateIndex = i;
                break;
            }
        }
        try {
            String oldName = Product.get(UpdateIndex).getProductName();
            int oldQuant = Product.get(UpdateIndex).getQuantity();
            if (UpdateIndex != -1) {

                newName = Utils.getStringCanEmpty("Enter new product name:", oldName);
                newQuantity = oldQuant;
                if (Product.get(UpdateIndex) instanceof DailyProduct) {
                    DailyProduct d = (DailyProduct) Product.get(UpdateIndex);
                    String oldUnit = d.getUnit();
                    String oldSize = d.getSize();
                    String newUnit = Utils.getStringCanEmpty("Enter product unit: ", oldUnit);
                    String newSize = Utils.getStringCanEmpty("Enter new product size: ", oldSize);
                    other1 = newUnit;
                    other2 = newSize;
                    Product.set(UpdateIndex, new DailyProduct(updateCode, newName, newQuantity, newUnit, newSize));
                    System.out.println("Updated product: " + Product.get(UpdateIndex).getProductCode() + "," + Product.get(UpdateIndex).getProductName() + "," + newUnit + "," + newSize);
                    SaveToFile();
                } else if (Product.get(UpdateIndex) instanceof LongShelfProduct) {
                    LongShelfProduct l = (LongShelfProduct) Product.get(UpdateIndex);
                    String oldManu = l.getManu();
                    String oldEx = l.getEx();
                    String newManu = Utils.getStringregCanEmpty("Enter product manufacture date(DD/MM/YYYY): ", "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", oldManu, "Invalid manufacture day");
                    String newEX = Utils.getStringregCanEmpty("Enter product expiration date(DD/MM/YYYY): ", "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", oldEx, "Invalid manufacture day");
                    other1 = newManu;
                    other2 = newEX;
                    Product.set(UpdateIndex, new LongShelfProduct(updateCode, newName, newManu, newEX));
                    SaveToFile();
                    System.out.println("Updated product: " + Product.get(UpdateIndex).getProductCode() + "," + Product.get(UpdateIndex).getProductName() + "," + newManu + "," + newEX);
                }

                for (Order order : Order) {
                    for (int i = 0; i < order.productInOrder.size(); i++) {
                        updateProductInOrder(updateCode, newName, other1, other2);
                    }
                }

            } else {
                System.out.println("Product does not exist");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Product doesnt exist");

        }
    }

    public void removeProduct() {
        String pcode = "";
        int foundIndex = -1;
        System.out.println("Product code to delete: ");
        pcode = sc.nextLine();
        for (int i = 0; i < Product.size(); i++) {
            if (pcode.equalsIgnoreCase(Product.get(i).getProductCode())) {
                foundIndex = i;
            }
        }
        if (foundIndex != -1) {
            System.out.println("Do you want to delete product with product code: " + pcode + " ?(Y/N)");
            String respond = sc.nextLine();
            if (respond.equalsIgnoreCase("y") || respond.equalsIgnoreCase("yes")) {
                Product.remove(foundIndex);
                System.out.println("product " + pcode + " is removed");
                ArrayList<Order> ordersContainingProduct = new ArrayList<>();
                for (Order o : Order) {
                    if (CheckCodeInOrder(pcode)) {
                        ordersContainingProduct.add(o);
                    }
                }
                SaveToFile();
            } else {
                System.out.println("Product " + pcode + " wont be delete");
            }
        } else {
            System.out.println("Product does not exist");
        }
    }

    public void ListProduct() {
        if (Product.size() > 0) {
            System.out.println(String.format("%12s%20s%12s%12s%12s%12s%12s", "Code", "Name", "Quantity", "Unit", "Size", "ManuDate", "ExDate"));
            for (int i = 0; i < Product.size(); i++) {
                if (Product.get(i) instanceof DailyProduct) {
                    System.out.println(Product.get(i).toString());
                } else if (Product.get(i) instanceof LongShelfProduct) {
                    System.out.println(Product.get(i).toString());
                }
            }
        } else {
            System.out.println("Empty");
        }
    }
    static String ordercode = "0000000";

    public void AddOrder(int choice2) {
        String ordertype = "";
        if (choice2 == 1) {
            ordertype = "import";
        } else if (choice2 == 2) {
            ordertype = "export";
        }

        int codeInt = Integer.parseInt(ordercode);
        codeInt++;
        ordercode = String.format("%07d", codeInt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        String orderdate = today.format(formatter);
        System.out.println("Order code: " + ordercode + "-Ordertype: " + ordertype + "-Orderdate: " + orderdate);
        Order o = new Order(ordertype, ordercode, orderdate);
        Order.add(o);
        AddProductInOrder(choice2, ordercode);
        System.out.println("Receipt " + ordertype + " sucessful");
        SaveToFile();
    }

    public void Expiration() {
        int count = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        System.out.println(String.format("%12s%20s%12s%12s%12s%12s%12s", "Code", "Name", "Quantity", "Unit", "Size", "ManuDate", "ExDate"));
        for (Product p : Product) {
            if (p instanceof LongShelfProduct) {
                LongShelfProduct l = (LongShelfProduct) p;
                LocalDate expirationDate = LocalDate.parse(l.getEx(), formatter);
                if (today.isAfter(expirationDate)) {
                    System.out.println(p);
                    count++;
                }
            }

        }
        if (count == 0) {
            System.out.println("No product expired");
        }
    }

    public void StoreSelling() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        System.out.println(String.format("%12s%20s%12s%12s%12s%12s%12s", "Code", "Name", "Quantity", "Unit", "Size", "ManuDate", "ExDate"));
        for (Product p : Product) {
            if (p instanceof DailyProduct) {
                if (((DailyProduct) p).quantity > 0) {
                    System.out.println(p);
                }
            } else if (p instanceof LongShelfProduct) {
                LongShelfProduct l = (LongShelfProduct) p;
                LocalDate expirationDate = LocalDate.parse(l.getEx(), formatter);
                if (today.isBefore(expirationDate) && l.quantity > 0) {
                    System.out.println(p.toString());
                }
            }
        }
    }

    public void OutOfOrder() {
        int count = 0;
        System.out.println(String.format("%12s%20s%12s%12s%12s%12s%12s", "Code", "Name", "Quantity", "Unit", "Size", "ManuDate", "ExDate"));
        Collections.sort(Product, new Comparator<Product>() {
            @Override
            public int compare(Product t, Product t1) {
                return t.productName.compareTo(t1.productName);
            }

        });

        for (Product p : Product) {
            if (p.quantity <= 3) {
                System.out.println(p);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No product out of order");
        }
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

    public void SaveToFile() {
        saveToFileDAT("Warehouse.dat", Order);
        saveToFileDAT("Product.dat", Product);
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
            System.out.println("Class not found");
            System.out.println(c + fname);
        }
        return data;
    }

    public void LoadFromFile() {
        File f0 = new File("Warehouse.dat");
        File f1 = new File("Product.dat");
        if (f0.exists()) {
            Order = (ArrayList<Order>) loadFromFileDAT("Warehouse.dat");
            if (!Order.isEmpty()) {
                Order lastest = Order.get(Order.size() - 1);
                ordercode = lastest.getOrdercode();
            }
        }
        if (f1.exists()) {
            Product = (ArrayList<Product>) loadFromFileDAT("Product.dat");
        }
    }

    public void ProductInOrder() {
        System.out.println("product code:");
        String pcode = sc.nextLine();
        for (Order o : Order) {
            for (Product p : o.productInOrder) {
                if (pcode.equalsIgnoreCase(p.getProductCode())) {
                    System.out.println(o);
                    System.out.println("\t\t\t----------------------------");
                }
            }
        }
    }

    public void save() {
        SaveToFile();
        System.out.println("saved");
    }

    public boolean CheckCodeInOrder(String code) {
        for (Order o : Order) {
            for (Product product : o.productInOrder) {
                if (product.getProductCode().equals(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int findProductInOrder(Order order, String productCode) {
        for (int i = 0; i < order.productInOrder.size(); i++) {
            if (order.productInOrder.get(i).getProductCode().equals(productCode)) {
                return i;
            }
        }
        return -1;
    }

    public void CleanOrder() {
        Iterator<Order> iterator = this.Order.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.productInOrder.isEmpty()) {
                iterator.remove();
            }
        }
    }

    public void AddProductInOrder(int flag, String ocode) {
        int count = 1;
        int same = 0;
        Order o = null;
        for (Order order : Order) {
            if (order.getOrdercode().equals(ocode)) {
                o = order;
                break;
            }
        }
        if (o != null) {
            do {
                String Pcode;
                Pcode = Utils.getString("Enter product code: ", "Product code cant be empty");
                Product existingProduct = null;
                if (CheckCodeInOrder(Pcode)) {
                    same = 1;
                }
                for (Product pr : Product) {
                    if (pr.getProductCode().equals(Pcode)) {
                        existingProduct = pr;
                        break;
                    }
                }
                if (existingProduct != null) {
                    System.out.println("Product information:");
                    System.out.println(String.format("%12s%20s%12s%12s%12s%12s%12s", "Code", "Name", "Quantity", "Unit", "Size", "ManuDate", "ExDate"));
                    System.out.println(existingProduct);
                    int quantity = Utils.getInt("Enter product quantity: ", 0);
                    Product p = null;
                    if (existingProduct instanceof DailyProduct) {
                        DailyProduct d = (DailyProduct) existingProduct;
                        p = new DailyProduct(d.getProductCode(), d.getProductName(), quantity, d.getUnit(), d.getSize());
                    } else if (existingProduct instanceof LongShelfProduct) {
                        LongShelfProduct l = (LongShelfProduct) existingProduct;
                        p = new LongShelfProduct(l.getProductCode(), l.getProductName(), quantity, l.getManu(), l.getEx());
                        p.setQuantity(quantity);
                    }

                    if (p != null) {
                        int a = findProductInOrder(o, p.getProductCode());
                        if (a != -1) {
                            o.productInOrder.get(a).setQuantity(o.productInOrder.get(a).getQuantity() + quantity);
                        } else {
                            o.productInOrder.add(p);
                            save();
                        }
                    }
                    if (flag == 1) {
                        existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                        SaveToFile();
                    } else if (flag == 2) {
                        if (existingProduct.getQuantity() - quantity > 0) {
                            existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
                            SaveToFile();
                        } else {
                            System.out.println("More than the product's quantity");
                        }
                    }

                    System.out.println("Add more product?(Y/N)");

                    String respond = sc.nextLine();
                    if (respond.equalsIgnoreCase("n") || respond.equalsIgnoreCase("no")) {
                        count++;
                    }
                } else {
                    System.out.println("Product doesnt exist");
                }
            } while (count == 1);
        }
    }

    public void removeProductInOrder(String pcode) {
        int foundIndex = -1;
        Order o = null;
        for (Order order : Order) {
            if (order.getOrdercode().equals(ordercode)) {
                o = order;
                for (int i = 0; i < o.productInOrder.size(); i++) {
                    if (pcode.equalsIgnoreCase(o.productInOrder.get(i).getProductCode())) {
                        foundIndex = i;
                    }
                }
                if (foundIndex != -1) {
                    o.productInOrder.remove(foundIndex);
                    System.out.println("product " + pcode + "is removed");
                    SaveToFile();
                } else {
                    System.out.println("Product does not exist");
                }
            }
        }
        if (o == null) {
            System.out.println("Order not found");
            return;
        }
    }

    public void updateProductInOrder(String updateCode, String newName, String other1, String other2) {
        int check = 0;
        int UpdateIndex = -1;
        Order o = null;
        for (Order order : this.Order) {
            o = order;
            for (int i = 0; i < o.productInOrder.size(); i++) {
                if (o.productInOrder.get(i).getProductCode().equalsIgnoreCase(updateCode)) {
                    UpdateIndex = i;
                    break;
                }
            }
            try {
                if (o.productInOrder.get(UpdateIndex) instanceof DailyProduct) {
                    DailyProduct d = (DailyProduct) o.productInOrder.get(UpdateIndex);
                    String newUnit = other1;
                    String newSize = other2;
                    o.productInOrder.set(UpdateIndex, new DailyProduct(updateCode, newName, o.productInOrder.get(UpdateIndex).getQuantity(), newUnit, newSize));
                } else if (o.productInOrder.get(UpdateIndex) instanceof LongShelfProduct) {
                    LongShelfProduct l = (LongShelfProduct) o.productInOrder.get(UpdateIndex);
                    String newManu = other1;
                    String newEX = other2;
                    o.productInOrder.set(UpdateIndex, new LongShelfProduct(updateCode, newName, o.productInOrder.get(UpdateIndex).getQuantity(), newManu, newEX));
                    save();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("product doesnt exist");
            }

        }
        if (o == null) {
            System.out.println("Order not found");
            return;
        }

        if (UpdateIndex != -1) {
            System.out.println("Updated product in order: " + o.productInOrder.get(UpdateIndex).getProductCode() + "," + o.productInOrder.get(UpdateIndex).getProductName() + "," + other1 + "," + other2);
        } else {
            System.out.println("Product does not exist in order");
        }
    }
}
