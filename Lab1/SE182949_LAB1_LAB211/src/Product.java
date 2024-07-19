
import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    protected String productCode;
    protected String productName;
    protected int quantity;

    public Product() {
    }

    public Product(String productCode, String productName) {
        this.productCode = productCode;
        this.productName = productName;
    }

    public Product(String productCode, String productName, int quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return productCode + productName + quantity;
    }
}
