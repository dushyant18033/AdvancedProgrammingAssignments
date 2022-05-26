import org.junit.Test;
import java.util.Random;

public class GameWinTest
{
    @Test(expected = GameWinnerException.class, timeout=1000)
    public void GameWinExceptions() throws Exception
    {
        int n=10;

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
        Computer.play(null,"TestingGuy",n,track,new Dice(),0,false,-1);
    }
}
