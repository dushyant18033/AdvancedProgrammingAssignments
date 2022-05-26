public class White extends Tile
{
    public White(int x) {
        super(x);
    }

    @Override
    public void shake()
    {
        return;
    }

    @Override
    public int getX() {
        return 0;
    }


    @Override
    public String toString()
    {
        return "I am a White tile!";
    }
}
