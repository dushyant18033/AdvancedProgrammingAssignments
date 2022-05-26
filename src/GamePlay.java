import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public final class GamePlay
{
    private final Scanner s;
    private final int[][] edges;
    private Monster[] layout;

    private final String username;
    private final Hero player;
    private int pos;

    private HintSystem hints;
    private boolean useHints;

    GamePlay(Scanner s, Hero player, String name)
    {
        this.s=s;
        this.player=player;
        this.username=name;
        pos=-1; //start

        edges=new int[][]{      //HardCoded Graph Edges
                {0, 1, 0, 0, 1, 0, 0, 1, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 0},
                {0, 1, 0, 0, 1, 1, 0, 1, 1, 1},
                {0, 1, 0, 0, 1, 0, 0, 1, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 0},
                {0, 1, 1, 0, 1, 0, 0, 1, 1, 1},
                {0, 1, 0, 0, 1, 0, 0, 1, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 0},
                {0, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 0, 1, 0, 0, 1, 0}
        };

        layout=new Monster[10];

        Random rn=new Random();
        for(int i=0; i<9; i++)
        {
            int lev=rn.nextInt(3);
            if(lev==0)
                layout[i]=new Goblin();
            else if(lev==1)
                layout[i]=new Zombie();
            else
                layout[i]=new Fiend();
        }
        layout[9]=new Lionfang();

       /* ////////For Debugging///////
        for(int i=0; i<10; i++)
            System.out.println(layout[i].getLevel());
        //////////////////////////// */
    }

    void begin()
    {
        Random ran=new Random();
        int[] hintedPath=null;
        int hint_ctr=0;

        System.out.println("Welcome "+username);

        System.out.println("Would you like to use hint system?(1/0)");
        int r=s.nextInt();
        if(r==1)
        {
            useHints=true;
            hints=new HintSystem(layout);
            hintedPath=hints.getOptimalPath();
        }

        while(true)
        {
            if (pos == 9 || player.getHP()<=0)   //If killed the boss || Lost
            {
                if(player.getHP()>0)
                    System.out.println("YOU WON!");
                else
                    System.out.println("YOU LOST!");
                System.out.println("GAME OVER. Would you like to restart/exit?(1/0)");
                int rep=s.nextInt();
                if(rep==0)  //exit to main menu
                {
                    System.out.println("Exiting...");
                    return;
                }
                else if(rep==1) //restart the game
                {
                    pos=-1;
                    layout=new Monster[10];
                    Random rn=new Random();
                    for(int i=0; i<9; i++)
                    {
                        int lev=rn.nextInt(3);
                        if(lev==0)
                            layout[i]=new Goblin();
                        else if(lev==1)
                            layout[i]=new Zombie();
                        else
                            layout[i]=new Fiend();
                    }
                    layout[9]=new Lionfang();
                    player.setLevel(1);
                    player.healthRefill();
                    hint_ctr=0;
                    continue;
                }
                else
                {
                    System.out.println("Please enter a valid input!");
                    continue;
                }
            }
            else if (pos == -1)
                System.out.print("You are at the starting location.");
            else
                System.out.print("You are at the location " + pos + ".");

            System.out.println("Choose path:");
            HashMap<Integer, Integer> map = new HashMap<>();

            if(pos==-1) //Starting Location
            {
                System.out.println("1) Go to Location 0\n" +
                        "2) Go to Location 3\n" +
                        "3) Go to Location 6");
                map.put(1,0);
                map.put(2,3);
                map.put(3,6);
            }
            else    //Other locations
            {
                int ctr = 0;
                for (int i = 0; i < 10; i++)
                {
                    if (edges[pos][i] == 1)
                    {
                        System.out.println((++ctr) + ") Go to Location " + i);
                        map.put(ctr, i);
                    }
                }
            }
            System.out.println("Enter -1 to exit");


            if(useHints)    //If hints enabled
                if (hintedPath != null && hint_ctr < 4)
                    System.out.println("HINT: Select Location " + hintedPath[hint_ctr]);


            int rep=s.nextInt();
            if(rep==-1) {   //Exit
                System.out.println("Exiting");
                return;
            }
            else if(!map.containsKey(rep)) {    //Invalid input
                System.out.println("Invalid Input!");
                continue;
            }
            else    //Proceed to next position
            {
                hint_ctr++;
                pos = map.get(rep);
                System.out.println("Moving to location "+pos);
                int specialReady=0, specialMovesLeft=0;

                Monster mon=layout[pos];
                if(mon.getLevel()==4)
                    System.out.println("Fight started. You're fighting the boss LionFang.");
                else
                    System.out.println("Fight started. You're fighting a level "+mon.getLevel()+" monster.");

                while(true) //Attack/Defence
                {
                    int defence=0;

                    System.out.println("Choose move:\n" +
                            "1) Attack\n" +
                            "2) Defense");
                    if(specialReady>=3)
                        System.out.println("3) Special Attack");

                    int type=s.nextInt();

                    if(type==1)
                    {
                        //Attack

                        System.out.println("You chose to attack");

                        int damage=player.getAttack();

                        if(specialMovesLeft>0)
                        {
                            if(player instanceof Warrior)
                            {
                                damage+=5;
                                System.out.println("5 extra damage applied.");
                            }
                            else if(player instanceof Mage)
                            {
                                mon.takeDamage((int) (0.05*mon.getHP()));
                                System.out.println("Monster's HP reduced by 5%");
                            }
                            else if(player instanceof Healer)
                            {
                                ((Healer) player).heal();
                                System.out.print("Your HP increased by 5%");
                            }
                            specialMovesLeft--;

                            if(specialMovesLeft==0)
                                System.out.println("Special Power Deactivated");
                        }

                        damage=mon.takeDamage(damage);

                        System.out.println("You attacked and inflicted "+damage+" damage to the monster.");

                        System.out.println("Your HP: "+player.getHP()+"/"+player.getMaxHP()+
                                " Monsters HP: "+mon.getHP()+"/"+mon.getMaxHP());

                        specialReady++;
                    }
                    else if(type==2)
                    {
                        //Defend

                        System.out.println("You chose to defend.");

                        defence=player.getDefence();

                        if(specialMovesLeft>0)
                        {
                            if(player instanceof Warrior)
                            {
                                defence+=5;
                                System.out.println("5 extra defence applied.");
                            }
                            else if(player instanceof Mage)
                            {
                                mon.takeDamage((int) (0.05*mon.getHP()));
                                System.out.println("Monster's HP reduced by 5%");
                            }
                            else if(player instanceof Healer)
                            {
                                ((Healer) player).heal();
                                System.out.print("Your HP increased by 5%");
                            }
                            specialMovesLeft--;

                            if(specialMovesLeft==0)
                                System.out.println("Special Power Deactivated");
                        }

                        System.out.println("Monster attack reduced by "+defence);

                        specialReady++;
                    }
                    else if(type==3 && specialReady>=3)
                    {
                        //SpecialMove
                        specialMovesLeft=3;
                        System.out.println("Special Power Activated.");

                        if(player instanceof Thief)
                        {
                            System.out.println("Performing Special Attack");
                            int steal=((Thief) player).stealHealth(mon.getHP());
                            steal=mon.takeDamage(steal);
                            System.out.println("You have stolen "+steal+" HP from the monster!");
                            System.out.println("Special Power Deactivated.");

                            specialMovesLeft=0;
                        }

                        specialReady=0;
                    }
                    else
                    {
                        System.out.println("Please enter a valid move!");
                        continue;
                    }

                    if(mon.getHP()<=0)  //Monster dies
                    {
                        System.out.println("Monster Killed.");
                        int l=20*mon.getLevel();
                        System.out.println(l+" XP awarded.");
                        player.addXP(l);
                        mon.revive();
                        break;
                    }

                    //Monster attack
                    System.out.println("Monster attack!");

                    int g=ran.nextInt(10);
                    int damage;

                    if(g==0 && (mon instanceof Lionfang))
                        damage = player.getHP() / 2;
                    else
                        damage = mon.attack();

                    damage = player.takeDamage(damage - defence);

                    System.out.println("The monster attacked and inflicted " + damage + " damage to you.");

                    System.out.println("Your HP: " + player.getHP() + "/" + player.getMaxHP() +
                            " Monsters HP: " + mon.getHP() + "/" + mon.getMaxHP());

                    if(player.getHP()<=0)   //Player dies
                    {
                        System.out.println("You died.");
                        break;
                    }
                }
            }
        }
    }
}
