import java.io.Serializable;

public class GameSaved implements Serializable
{
    private Tile[] track;
    private Dice die;
    private int n;
    private String playerName;
    private int cur;
    private boolean outOfCage;
    private int lastCheckPoint;

    private int snakes;
    private int crickets;
    private int vultures;
    private int trampolines;

    public GameSaved(Tile[] track, Dice die, int n, String playerName, int cur, boolean outOfCage, int lastCheckPoint, int snakes, int crickets, int vultures, int trampolines) {
        this.track = track;
        this.die = die;
        this.n = n;
        this.playerName = playerName;
        this.cur = cur;
        this.outOfCage = outOfCage;
        this.lastCheckPoint = lastCheckPoint;
        this.snakes = snakes;
        this.crickets = crickets;
        this.vultures = vultures;
        this.trampolines = trampolines;
    }

    public Tile[] getTrack() {
        return track;
    }

    public Dice getDie() {
        return die;
    }

    public int getN() {
        return n;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCur() {
        return cur;
    }

    public boolean isOutOfCage() {
        return outOfCage;
    }

    public int getLastCheckPoint() {
        return lastCheckPoint;
    }

    public int getSnakes() {
        return snakes;
    }

    public int getCrickets() {
        return crickets;
    }

    public int getVultures() {
        return vultures;
    }

    public int getTrampolines() {
        return trampolines;
    }

    @Override
    public boolean equals(Object o) {
        if(o!=null && getClass()==o.getClass())
        {
            GameSaved o1=(GameSaved)o;
            return o1.playerName==this.playerName && o1.cur==this.cur;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Name: "+playerName+", Current position: "+cur;
    }
}
