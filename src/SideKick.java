public class SideKick implements Comparable
{
    int HP;

    int base_attack;    //Base
    private int extra_from_hero;    // Given by Hero
    private int extra_from_gained;   //Gained

    int base_cost;
    int extraXP;  //Given by Hero
    int XP; //Gained

    void displayHP()
    {
        System.out.println("Sidekick HP: "+HP+"/100");
    }

    int getXP() {
        return XP;
    }

    public int attack(boolean disp)
    {
        int i = base_attack + extra_from_hero + extra_from_gained;
        if(disp)
            System.out.println("Sidekick attacked and inflicted "+i+" damage to the monster.");
        return i;
    }

    public void takeDamage(int d)
    {
        HP-= d;
        if(HP<=0)
            HP=0;
    }

    void addExtraXPFromHero(int i)
    {
        extraXP+=i;
        extra_from_hero = extraXP / 2;
    }

    void gainXPFromFight(int d)
    {
        XP+=d;
        HP=100;
        extra_from_gained=(XP/5);
    }

    int getHP() {
        return HP;
    }

    public void setHP(int i) {
        this.HP=i;
    }

    @Override
    public int compareTo(Object o) {
        SideKick o1= (SideKick) o;
        int a=this.getXP();
        int b=o1.getXP();

        if(a==b)
            return 0;
        else if(a<b)
            return -1;
        else
            return 1;
    }

    @Override
    public boolean equals(Object obj1) {
        SideKick obj=(SideKick)obj1;
        return this.getClass()==obj.getClass() &&
               this.HP==obj.HP &&
               this.extra_from_gained==obj.extra_from_gained &&
               this.extra_from_hero==obj.extra_from_hero &&
               this.extraXP==obj.extraXP &&
               this.XP==obj.XP;
    }
}
