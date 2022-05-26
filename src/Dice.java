import java.util.Random;

public final class Dice
{
    private final Random rn = new Random();
    private int numRolls=0;

    public int roll()
    {
        numRolls++;
        return 1 + rn.nextInt(6);
    }
    public int getRolls()
    {
        return numRolls;
    }
}
