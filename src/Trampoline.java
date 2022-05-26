public class Trampoline extends Tile
{
    static int trampolines=0;

    public Trampoline(int x) {
        super(x);
    }

    @Override
    public void shake() throws TrampolineException
    {
        trampolines++;
        throw new TrampolineException();
    }

    @Override
    public int getX() {
        return this.x;
    }


    @Override
    public String toString() {
        return "PingPong! I am a Trampoline, you advance "+this.x+" tiles";
    }

    public static int getTrampolines() {
        return trampolines;
    }

    public static void setTrampolines(int trampolines) {
        Trampoline.trampolines = trampolines;
    }
}
