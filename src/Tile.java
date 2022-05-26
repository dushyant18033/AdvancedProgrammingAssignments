public abstract class Tile
{
    final int x;    //random value b/w 1-10

    public Tile(int x)
    {
        this.x = x;
    }

    public abstract void shake() throws Exception;

    public abstract int getX();

    public abstract String toString();
}
