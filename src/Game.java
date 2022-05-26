import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public final class Game
{
    private static Scanner s=new Scanner(System.in);
    private static Tile[] track;
    private static Dice die=new Dice();
    private static int n;
    private static String playerName;

    private static void setup()
    {
        while(true) //Exception Handling for Input n
        {
            try
            {
                System.out.print("Enter total number of tiles on the race track (length>=10): ");
                n=s.nextInt();
                if(n<10)
                {
                    System.out.println("Length of the track should be at least 10.");
                    continue;
                }
                break;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Only Integer is allowed as an Input.");
                s.nextLine();
            }
        }

        System.out.println("Setting up the race track...");
        track=new Tile[n];
        Random rn=new Random();

        int x=0,y=0,z=0,t=0;    //counters

        int a=1+rn.nextInt(10);
        int b=1+rn.nextInt(10);
        int c=1+rn.nextInt(10);
        int d=1+rn.nextInt(10);

        rn=new Random();
        for(int i=0; i<n; i++)
        {
            int rand=rn.nextInt(10);
            switch (rand)
            {
                case 0: track[i]=new Snake(a);          x++;     break;
                case 2: track[i]=new Vulture(c);        z++;     break;
                case 4: track[i]=new Cricket(b);        y++;     break;
                case 8: track[i]=new Trampoline(d);     t++;     break;
                default: track[i]=new White(0);               break;
            }
        }

        System.out.println("Danger: There are "+x+", "+y+", "+z+" numbers of Snakes, Cricket, and Vultures respectively on your track!");
        System.out.println("Danger: Each Snake, Cricket, and Vultures can throw you back by "+a+", "+b+", "+c+" number of Tiles respectively!");
        System.out.println("Good News: There are "+t+" number of Trampolines on your track!");
        System.out.println("Good News: Each Trampoline can help you advance by "+d+" number of Tiles");

        System.out.print("Enter the Player Name: ");
        playerName =s.next();

        System.out.println("Starting the game with "+ playerName +" at Tile-1\n" +
                "Control transferred to Computer for rolling the Dice for "+ playerName +
                "\nHit enter to start the game.");
    }

    private static void wins()
    {
        System.out.println(playerName +" wins the race in "+die.getRolls()+" rolls!\n" +
                "Total Snake Bites = "+Snake.getSnakeBites()+"\n" +
                "Total Vulture Bites = "+Vulture.getVultureBites()+"\n" +
                "Total Cricket Bites = "+Cricket.getCricketBites()+"\n" +
                "Total Trampolines = "+Trampoline.getTrampolines());
    }

    public static void main(String[] args)
    {
        Game.setup();  //Setting up the track

        s.nextLine();
        s.nextLine();   //Wait for enter key press.

        try
        {
            Computer.play(playerName,n,track,die);
        }
        catch (GameWinnerException e)
        {
            System.out.println(e.getMessage());
            Game.wins();
        }
        finally
        {
            System.out.println("Terminating the application...");
        }
    }
}