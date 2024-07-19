
import java.io.Serializable;



public class DailyProduct extends Product implements Serializable{

    public String unit;
    public String size;

    public DailyProduct(String unit, String size) {
        this.unit = unit;
        this.size = size;
    }

    public DailyProduct(String productCode, String productName, String unit, String size) {
        super(productCode, productName);
        this.unit = unit;
        this.size = size;
    }

    public DailyProduct(String productCode, String productName, int quantity, String unit, String size) {
        super(productCode, productName, quantity);
        this.unit = unit;
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String adjustLength(String str, int length) {
    if (str.length() < length) {
        return String.format("%-" + length + "s", str);
    } else {
        return str.substring(0, length);
    }
}
    @Override
    public String toString() {
        return String.format("%12s%20s%12s%12s%12s%12s%12s", super.getProductCode(), super.getProductName(), super.getQuantity(), unit, size, "NA", "NA");
    }

}
