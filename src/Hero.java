public class Hero
{
    private int XP;
    private int HP;
    private int maxHP;
    private int level;

    private int attack;
    private int defence;

    void setLevel(int level) {
        this.level = level;
    }

    Hero()
    {
        HP=100;
        maxHP=100;
        XP=0;
        level=1;
        //specialMovesLeft=0;
    }

    int getHP() {
        return HP;
    }

    int getMaxHP() {
        return maxHP;
    }

    void addXP(int a)
    {
        XP+=a;
        updateLevel();
        healthRefill();
    }

    int takeDamage(int d)
    {
        if(d<0)
            return 0;
        HP-=d;
        if(HP<0)
        {
            int temp=HP+d;
            HP=0;
            return temp;
        }
        return d;
    }
    private void updateLevel()
    {
        int new_level=(int)(XP/20) + 1;

        if(level==new_level)
            return;

        attack++;
        defence++;

        level=new_level;

        switch (level)
        {
            case 1: maxHP=100;  System.out.println("Level Up: level:1");    break;
            case 2: maxHP=150;  System.out.println("Level Up: level:2");    break;
            case 3: maxHP=200;  System.out.println("Level Up: level:3");    break;
            case 4: maxHP=250;  System.out.println("Level Up: level:4");    break;
        }

        System.out.println("Health refilled to max.");
    }

    void healthRefill()
    {
        HP=maxHP;
    }

    int getAttack() {
        return attack;
    }

    int getDefence() {
        return defence;
    }

    void setAttack(int attack) {
        this.attack = attack;
    }

    void setDefence(int defence) { this.defence = defence; }

    void setHP(int HP) {
        this.HP = HP;
    }
}