
import java.io.Serializable;


public class LongShelfProduct extends Product implements Serializable{

    String manu;
    String ex;

    public LongShelfProduct(String manu, String ex) {
        this.manu = manu;
        this.ex = ex;
    }

    public LongShelfProduct(String productCode, String productName, String manu, String ex) {
        super(productCode, productName);
        this.manu = manu;
        this.ex = ex;
    }

    public LongShelfProduct(String productCode, String productName, int quantity, String manu, String ex) {
        super(productCode, productName, quantity);
        this.manu = manu;
        this.ex = ex;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
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

        return String.format("%12s%20s%12s%12s%12s%12s%12s", super.getProductCode(), super.getProductName(), super.getQuantity(), "NA", "NA", manu, ex);
    }

}
