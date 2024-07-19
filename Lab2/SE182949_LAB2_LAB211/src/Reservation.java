
import java.io.Serializable;
public class Reservation implements Serializable{

    String ID;
    String fnumb;
    String pname;
    String pcontact;
    String seats;

    public Reservation() {
    }

    public Reservation(String ID, String fnumb, String pname,String pcontact) {
        this.ID = ID;
        this.fnumb=fnumb;
        this.pname=pname;
        this.pcontact=pcontact;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFnumb() {
        return fnumb;
    }

    public void setFnumb(String fnumb) {
        this.fnumb = fnumb;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcontact() {
        return pcontact;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
    
    @Override
    public String toString() {
        return String.format("%-10s %-10s %-30s %-30s", ID,fnumb,pname,pcontact);
    }

}
