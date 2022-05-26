public class Vulture extends Tile
{
    private static int vultureBites=0;

    public Vulture(int x) {
        super(x);
    }

    @Override
    public void shake() throws VultureBiteException
    {
        vultureBites++;
        throw new VultureBiteException();
    }

    @Override
    public int getX() {
        return -this.x;
    }

    @Override
    public String toString() {
        return "Yapping...! I am a Vulture, you go back "+this.x+" tiles!";
    }

    public static int getVultureBites() {
        return vultureBites;
    }

    public static void setVultureBites(int vultureBites) {
        Vulture.vultureBites = vultureBites;
    }
}
