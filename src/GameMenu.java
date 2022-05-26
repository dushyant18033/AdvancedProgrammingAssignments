import java.util.HashMap;
import java.util.Scanner;

public final class GameMenu
{
    private static HashMap<String,GamePlay> players;
    private static Scanner s;

    private static void init()
    {
        players=new HashMap<>();
        s=new Scanner(System.in);
    }

    private static void add()
    {
        System.out.print("Enter Username: ");
        String user=s.next();

        if(players.containsKey(user))
        {
            System.out.println("User already exists. Proceed to login.");
            return;
        }

        String type="";
        System.out.println("Choose a Hero\n" +
                "1) Warrior\n" +
                "2) Thief\n" +
                "3) Mage\n" +
                "4) Healer");
        int n=s.nextInt();

        switch (n)
        {
            case 1: players.put(user,new GamePlay(s,new Warrior(),user));    type="Warrior";    break;
            case 2: players.put(user,new GamePlay(s,new Thief(),user));      type="Thief";      break;
            case 3: players.put(user,new GamePlay(s,new Mage(),user));       type="Mage";       break;
            case 4: players.put(user,new GamePlay(s,new Healer(),user));     type="Healer";     break;

            default:    System.out.println("Invalid input. User creation failed."); return;
        }
        System.out.println("User Creation done. Username: "+user+". Hero type: "+type+". Log in to play the game . Exiting");
    }

    private static void login()
    {
        System.out.print("Enter Username: ");
        String user=s.next();
        if(!players.containsKey(user))
            System.out.println("User not found. Please register first.");
        else
        {
            System.out.println("User Found... logging in");
            players.get(user).begin();
        }
    }

    public static void main(String[] args)
    {
        GameMenu.init();

        while(true)
        {
            System.out.println("Welcome to ArchLegends\n" +
                    "Choose your option\n" +
                    "1) New User\n" +
                    "2) Existing User\n" +
                    "3) Exit");
            int sel = s.nextInt();

            switch (sel)
            {
                case 1: GameMenu.add();         break;
                case 2: GameMenu.login();       break;
                case 3: System.exit(0);   break;
                default:    System.out.println("Invalid input!");   break;
            }
        }
    }
}
