public class Thief extends Hero {

    Thief()
    {
        setAttack(6);
        setDefence(5);
    }

    public int stealHealth(int monHP)   //Special power of Thief
    {
        int abc = (int) Math.floor((double) 0.3*monHP + 0.5);
        setHP(getHP()+abc);
        if(getHP() > getMaxHP())
            setHP(getMaxHP());
        return abc;
    }
}
