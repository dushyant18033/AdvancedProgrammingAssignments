import java.util.Random;

public class Monster
{
    private int HP;
    private int maxHP;
    private int level;
    private Random rn;

    Monster()
    {
        rn=new Random();
    }

    int attack()
    {
        double k=rn.nextGaussian();
        int hpBy8=(int) (HP/8.0);
        return (int) ((k*hpBy8) + hpBy8);
    }

    int getLevel() {
        return level;
    }

    int getHP() {
        return HP;
    }

    int getMaxHP() {
        return maxHP;
    }

    int takeDamage(int d)
    {
        HP-=d;
        if(HP<0)
        {
            int temp=HP+d;
            HP = 0;
            return temp;
        }
        else
            return d;
    }

    void revive()
    {
        HP=maxHP;
    }

    void setHP(int HP) {
        this.HP = HP;
    }

    void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    void setLevel(int level) {
        this.level = level;
    }
}
