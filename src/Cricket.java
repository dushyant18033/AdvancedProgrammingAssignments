public class Cricket extends Tile
{
    static int cricketBites=0;

    public Cricket(int x) {
        super(x);
    }

    @Override
    public void shake() throws CricketBiteException
    {
        cricketBites++;
        throw new CricketBiteException();
    }

    @Override
    public int getX() {
        return -this.x;
    }


    @Override
    public String toString() {
        return "Chirp...! I am a Cricket, you go back "+this.x+" tiles!";
    }

    public static int getCricketBites() {
        return cricketBites;
    }

    public static void setCricketBites(int cricketBites) {
        Cricket.cricketBites = cricketBites;
    }
}
