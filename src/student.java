import java.util.ArrayList;
import java.util.HashSet;

public class student
{
    private final float cgpa;
    private final String branch;
    private final int rollno;
    private HashSet<company> appliedIn;
    private boolean placed;
    private String placedIn;

    student(float c, String b, int r)
    {
        cgpa=c;
        branch=b;
        rollno=r;
        placed=false;
        placedIn=null;
        appliedIn=new HashSet<company>();
    }

    public String toString()
    {
        String status="";
        if(placed)
            status="Placed in `"+placedIn+"`";
        else
            status="Not Placed";
        return rollno+"\n"+cgpa+"\n"+branch+"\nPlacement Status: "+status;
    }

    public float getCgpa() {
        return cgpa;
    }

    public String getBranch() {
        return branch;
    }

    public int getRollno() {
        return rollno;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public String getPlacedIn() {
        return placedIn;
    }

    public void setPlacedIn(String placedIn) {
        this.placedIn = placedIn;
    }

    public HashSet<company> getAppliedIn() {
        return appliedIn;
    }

    public void setAppliedIn(HashSet<company> appliedIn) {
        this.appliedIn = appliedIn;
    }
}
