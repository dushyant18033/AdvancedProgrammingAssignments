public final class Minion extends SideKick implements Cloneable
{
    private int cloning;
    private Minion[] clones;

    Minion(int xp)
    {
        HP=100;
        XP=0;
        base_attack=1;
        base_cost=5;//XP
        addExtraXPFromHero(xp-base_cost);
        cloning=0;
        clones=new Minion[3];
    }

    private Minion(int xp, int ownXP)
    {
        HP=100;
        XP=ownXP;
        base_attack=1;
        base_cost=5;//XP
        addExtraXPFromHero(xp-base_cost);
        cloning=0;
        clones=new Minion[3];
    }

    @Override
    public void setHP(int i) {
        super.setHP(i);
        if(cloning==1)
            for(int j=0; j<3; j++)
                clones[j].setHP(i);
    }

    void useCloning()
    {
        if(cloning==2)
        {
            System.out.println("Cloning already used up. Cannot use again.");
            return;
        }

        cloning=1;
        for(int i=0; i<3; i++)
            clones[i]=this.clone();

        System.out.println("Cloning Done.");
    }

    @Override
    public int attack(boolean disp) {
        int tot=super.attack(disp);

        if(cloning==1)
            for(int i=0; i<3; i++)
                tot+=clones[i].attack(disp);

        return tot;
    }

    @Override
    public void takeDamage(int d) {
        super.takeDamage(d);
        if(cloning==1)
            for(int i=0; i<3; i++)
                clones[i].takeDamage(d);
    }

    @Override
    void displayHP() {
        super.displayHP();
        if(cloning==1)
            for(int i=0; i<3; i++)
                clones[i].displayHP();
    }

    void destroyClonesIfUsed()
    {
        if(cloning==0)
            return;
        clones=new Minion[3];
        cloning=2;  //Cloning used up.
    }

    public Minion clone()
    {
        return new Minion(base_cost+extraXP, XP);
    }
}
