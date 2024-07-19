
import java.io.Serializable;

public class Crew implements Serializable{

    public String name;
    public String roles;
    public String assignedFlight;
 


    public Crew(String name, String roles, String assignedFlight) {
        this.name = name;
        this.roles = roles;
        this.assignedFlight = assignedFlight;
    }
    
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getassignedFlight() {
        return assignedFlight;
    }

    public void setAssignment(String assignedFlight) {
        this.assignedFlight = assignedFlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedFlight() {
        return assignedFlight;
    }

    public void setAssignedFlight(String assignedFlight) {
        this.assignedFlight = assignedFlight;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-30s %-30s", assignedFlight,roles,name);    }
}
