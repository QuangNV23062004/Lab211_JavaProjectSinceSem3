
import MyUtils.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Order implements Serializable {

    ArrayList<Product> productInOrder;
    String Ordertype;
    String Ordercode;
    String OrderDate;

    public Order(String Ordertype, String Ordercode, String OrderDate) {
        this.Ordertype = Ordertype;
        this.Ordercode = Ordercode;
        this.OrderDate = OrderDate;
        productInOrder = new ArrayList<>();
    }

    public String getOrdertype() {
        return Ordertype;
    }

    public void setOrdertype(String Ordertype) {
        this.Ordertype = Ordertype;
    }

    public String getOrdercode() {
        return Ordercode;
    }

    public void setOrdercode(String Ordercode) {
        this.Ordercode = Ordercode;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ordertype: ").append(Ordertype)
                .append(", Ordercode: ").append(Ordercode)
                .append(", OrderDate: ").append(OrderDate)
                .append("\nproductInOrder:\n")
                .append(String.format("%12s%20s%12s%12s%12s%12s%12s", "Code", "Name", "Quantity", "Unit", "Size", "ManuDate", "ExDate"))
                .append("\n");
        for (Product p : productInOrder) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

}
