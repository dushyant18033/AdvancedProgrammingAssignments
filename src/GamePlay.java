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

        SideKick sk=null;
        boolean useSideKick=false;

        int[] hintedPath=null;

        System.out.println("Welcome "+username);

        while(true) //Use hints or not?
        {
            System.out.println("Would you like to use hint system?(1/0)");
            int r=s.nextInt();
            if(r==1)
            {
                useHints=true;
                hints=new HintSystem(layout);
                hintedPath=hints.getOptimalPath();
                break;
            }
            else if(r==0)
            {
                useHints=false;
                break;
            }
            else if(r==-1)
            {
                System.out.println("Returning to menu...");
                return;
            }
            else
                System.out.println("Invalid Input!");
        }

        while(true)
        {
            if (pos == 9 || player.getHP()<=0)   //If killed the boss || Lost
            {
                if(player.getHP()>0)
                    System.out.println("YOU'VE WON!");
                else
                    System.out.println("YOU'VE LOST!");
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
                    player.resetSideKicks();
                    System.out.print("Would you like to disable hints?(yes/no): ");
                    if(s.next().toLowerCase().equals("yes"))
                    {
                        hintedPath=null;
                        useHints=false;
                    }
                    else
                    {
                        hints=new HintSystem(layout);
                        hintedPath=hints.getOptimalPath();
                    }
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
                if (hintedPath != null)
                {
                    System.out.println("HINT: Follow the optimal path as follows.");
                    for(int h=0; h<3; h++)
                        System.out.print(hintedPath[h]+"-->");
                    System.out.println(hintedPath[3]);
                }


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
                if(useSideKick && sk instanceof Minion)
                    ((Minion) sk).destroyClonesIfUsed();

                pos = map.get(rep);
                System.out.println("Moving to location "+pos);
                int specialReady=0, specialMovesLeft=0;

                Monster mon=layout[pos];
                if(mon.getLevel()==4)
                    System.out.println("Fight started. You're fighting the boss LionFang.");
                else
                    System.out.println("Fight started. You're fighting a level "+mon.getLevel()+" monster.");

                 if(player.hasSideKicks())  //Use sidekick or not
                 {
                     System.out.print("Would you like to use a sidekick? (yes/no): ");
                     String inp = s.next();
                     if (inp.toLowerCase().equals("yes"))
                     {
                         sk = player.getSideKick();
                         useSideKick = true;

                         if(sk instanceof Minion)
                         {
                             System.out.println("You have a sidekick Minion with you. Attack of sidekick is " + sk.attack(false));
                             System.out.print("Use cloning ability or move on to the fight?(c/f): ");
                             String c=s.next();
                             if(c.equals("c"))
                                 ((Minion) sk).useCloning();
                         }
                         else
                             System.out.println("You have a sidekick Knight with you. Attack of sidekick is "+sk.attack(false));
                     }
                     else
                     {
                         useSideKick = false;
                         sk=null;
                     }
                 }
                 else
                 {
                     sk=null;
                     useSideKick=false;
                 }

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

                        ////////////////////////////////////////
                        if(useSideKick) //If sidekick is enabled
                        {
                            if(sk instanceof Minion)
                                mon.takeDamage(sk.attack(true));
                            else if(sk instanceof Knight)
                                mon.takeDamage(sk.attack(true));
                        }
                        ////////////////////////////////////////

                        System.out.println("Your HP: "+player.getHP()+"/"+player.getMaxHP()+
                                " Monsters HP: "+mon.getHP()+"/"+mon.getMaxHP());

                        if(sk!=null && useSideKick)
                            sk.displayHP(); //Sidekick HP

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


                        ///////////////KNIGHT DEFENCE///////////
                        if(useSideKick && sk instanceof Knight)
                            defence+=((Knight) sk).defence(mon);
                        ////////////////////////////////////////

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
                        System.out.println("Fight Won, Proceed further.");

                        if(sk!=null)
                        {
                            if(sk.getHP()<=0)
                            {
                                player.forget(sk);
                                sk=null;
                                useSideKick=false;
                            }
                            else
                                sk.gainXPFromFight(l/10);
                        }

                        if(mon instanceof Lionfang)
                        {
                            player.addXP(l);
                            mon.revive();
                            break;
                        }

                        /////////////////////BUY SIDEKICK or UPGRADE LEVEL//////////////
                        System.out.print("Would you like to buy a side kick? (yes/no): ");
                        String repl=s.next();
                        while(repl.toLowerCase().equals("yes"))
                        {
                            int cur=player.getXP()+l;
                            int maxXP=cur - (player.getLevel()-1)*20;
                            System.out.println("Current XP: "+cur);
                            System.out.println("You can spend atmost "+maxXP+"XP towards SideKick right now.");
                            System.out.print("Buy Minion/Knight? (1/2): ");
                            int ty=s.nextInt();
                            System.out.print("XP to spend: ");
                            int xp=s.nextInt();

                            if(ty==1)
                            {
                                if(xp<5)
                                    System.out.println("You need to spend more XP to buy a Minion SideKick.");
                                else if(xp>maxXP)
                                    System.out.println("You cannot spend too much, as your level can't be dropped.");
                                else
                                {
                                    SideKick mini=new Minion(xp);
                                    player.addSideKick(mini);
                                    player.setXP(cur-xp);

                                    System.out.println("You bought a sidekick: Minion\n" +
                                            "XP of the sidekick is "+ mini.getXP()+"\n" +
                                            "Attack of the sidekick is "+mini.attack(false));
                                    mon.revive();
                                    break;
                                }
                            }
                            else if(ty==2)
                            {
                                if(xp<8)
                                    System.out.println("You need to spend more XP to buy a Knight SideKick.");
                                else if(xp>maxXP)
                                    System.out.println("You cannot spend too much, as your level can't be dropped.");
                                else
                                {
                                    SideKick kn=new Knight(xp);
                                    player.addSideKick(kn);
                                    player.setXP(player.getXP()+l-xp);

                                    System.out.println("You bought a sidekick: Knight\n" +
                                            "XP of the sidekick is "+ kn.getXP()+"\n" +
                                            "Attack of the sidekick is "+kn.attack(false));
                                    mon.revive();
                                    break;
                                }
                            }
                            else
                                System.out.println("Invalid Input!");
                        }
                        if(!repl.toLowerCase().equals("yes"))
                            player.addXP(l);
                        //////////////////////////////////////////////////

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

                    if(sk!=null && useSideKick)
                        sk.takeDamage((int) (damage*1.5));  //SideKick take damage

                    System.out.println("The monster attacked and inflicted " + damage + " damage to you.");

                    System.out.println("Your HP: " + player.getHP() + "/" + player.getMaxHP() +
                            " Monsters HP: " + mon.getHP() + "/" + mon.getMaxHP());

                    if(sk!=null)
                    {
                        if(sk.getHP()==0)
                        {
                            System.out.println("Sidekick Died");
                            useSideKick=false;
                            sk.setHP(-1);
                        }
                        else if(sk.getHP()>0)
                            sk.displayHP();
                    }

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
