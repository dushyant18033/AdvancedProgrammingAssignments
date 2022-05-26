import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public interface UserAccount
{
    public String toString();
    public String getName();
    public void productSearch(Scanner s, HashMap<String, ArrayList<item>> products);
    public String rewards();
}