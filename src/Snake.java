public class Snake extends Tile
{
    static private int snakeBites=0;

    public Snake(int x) {
        super(x);
    }

    @Override
    public void shake() throws SnakeBiteException
    {
        snakeBites++;
        throw new SnakeBiteException();
    }

    @Override
    public int getX() {
        return -this.x;
    }

    @Override
    public String toString() {
        return "Hiss...! I am a Snake, you go back "+this.x+" tiles!";
    }

    public static int getSnakeBites() {
        return snakeBites;
    }

    public static void setSnakeBites(int snakeBites) {
        Snake.snakeBites = snakeBites;
    }
}
