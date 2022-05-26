public final class Knight extends SideKick
{
    Knight(int xp)
    {
        HP=100;
        XP=0;
        base_attack=2;
        base_cost=8;//XP
        addExtraXPFromHero(xp-base_cost);
    }

    int defence(Monster m1)
    {
        if(m1 instanceof Zombie)
        {
            System.out.println("Additional 5 defence applied by the sidekick Knight against Zombie.");
            return 5;
        }
        else
            return 0;
    }
}
