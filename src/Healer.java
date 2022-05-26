public class Healer extends Hero {

    Healer()
    {
        super();
        setAttack(4);
        setDefence(8);
    }

    public void heal()  //Special power of Healer
    {
        setHP((int) (getHP()*1.05));
        if(getHP() > getMaxHP())
            setHP(getMaxHP());
    }
}
