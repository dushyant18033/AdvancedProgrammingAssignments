import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class testingGameSave
{
    @Test(timeout=1000)
    public void saveGameCheck()
    {
        int n=100;

        Tile[] track=new Tile[n];

        Random rn=new Random();
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
                case 0: track[i]=new Snake(a);               break;
                case 2: track[i]=new Vulture(c);             break;
                case 4: track[i]=new Cricket(b);             break;
                case 8: track[i]=new Trampoline(d);          break;
                default: track[i]=new White(0);           break;
            }
        }
        int cur=0;
        try {
            cur=Computer.play(null,"TestingGuy",n,track,new Dice(),0,false,0,1);
        } catch (GameWinnerException e) {
            e.printStackTrace();
        }
        ///Game saved.


        ///Let's check if the game was saved successfully
        ObjectInputStream in = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream("save.txt"));
            HashMap<String, GameSaved> saves = (HashMap<String, GameSaved>) in.readObject();
            if(saves!=null)
            {
                if(saves.containsKey("TestingGuy"))
                {
                    GameSaved gs=saves.get("TestingGuy");
                    assertEquals(cur,gs.getCur());
                    System.out.println("\n*GAME SAVE TEST WAS SUCCESSFUL*\n");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Game not saved. Test failed");
        }
        finally //Close the file properly
        {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
