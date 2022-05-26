import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public final class Computer
{
    private static void showStatus()
    {
        System.out.println("Total Snake Bites = "+Snake.getSnakeBites()+"\n" +
                "Total Vulture Bites = "+Vulture.getVultureBites()+"\n" +
                "Total Cricket Bites = "+Cricket.getCricketBites()+"\n" +
                "Total Trampolines = "+Trampoline.getTrampolines());
    }
    public static int play(Scanner s,String name, int n, Tile[] track, Dice die, int cur, boolean outOfCage, int lastCheckPoint) throws GameWinnerException  /////MAIN GAME/////
    {
        return Computer.play(s,name,n,track,die,cur,outOfCage,lastCheckPoint,0);
    }
    public static int play(Scanner s,String name, int n, Tile[] track, Dice die, int cur, boolean outOfCage, int lastCheckPoint, int sel) throws GameWinnerException  /////MAIN GAME/////
    {
        System.out.println("===============GAME STARTED===============");
        //int cur=0;
        //boolean outOfCage=false;

        while(true)
        {
            if(cur >= n/4 && lastCheckPoint==0)  //Checkpoint 1
            {
                lastCheckPoint=1;

                System.out.print("First checkpoint reached." +
                        "\nWould you like to " +
                        "\n(1)save the game and exit" +
                        "\n(2)continue the game?: ");

                if(s!=null)
                    sel=s.nextInt();

                if(sel==1)
                {
                    HashMap<String, GameSaved> saves;

                    ObjectInputStream in=null;
                    ObjectOutputStream out=null;

                    try
                    {
                        in = new ObjectInputStream(new FileInputStream("save.txt"));
                        saves = (HashMap<String, GameSaved>) in.readObject();
                    }
                    catch (Exception e)
                    {
                        saves=new HashMap<String, GameSaved>();
                    }
                    finally
                    {
                        try {
                            in.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                        catch (NullPointerException e){
                            //e.printStackTrace();
                        }
                    }

                    saves.put(name,new GameSaved(track,die,n,name,cur,outOfCage,lastCheckPoint,Snake.getSnakeBites(),Cricket.getCricketBites(),Vulture.getVultureBites(),Trampoline.getTrampolines()));

                    try {
                        out = new ObjectOutputStream(new FileOutputStream("save.txt"));
                        out.writeObject(saves);
                        System.out.println("Game has been saved.");
                    }
                    catch (Exception e)
                    {
                    }
                    finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    showStatus();
                    break;
                }
            }

            if(cur >= n/2 && lastCheckPoint==1)  //Checkpoint 2
            {
                lastCheckPoint=2;

                System.out.print("Second checkpoint reached." +
                        "\nWould you like to " +
                        "\n(1)save the game and exit" +
                        "\n(2)continue the game?: ");

                if(s!=null)
                    sel=s.nextInt();

                if(sel==1)
                {
                    HashMap<String, GameSaved> saves;

                    ObjectInputStream in=null;
                    ObjectOutputStream out=null;

                    try
                    {
                        in = new ObjectInputStream(new FileInputStream("save.txt"));
                        saves = (HashMap<String, GameSaved>) in.readObject();
                    }
                    catch (Exception e)
                    {
                        saves=new HashMap<String, GameSaved>();
                    }
                    finally
                    {
                        try {
                            in.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                        catch (NullPointerException e){
                            //e.printStackTrace();
                        }
                    }

                    saves.put(name,new GameSaved(track,die,n,name,cur,outOfCage,lastCheckPoint,Snake.getSnakeBites(),Cricket.getCricketBites(),Vulture.getVultureBites(),Trampoline.getTrampolines()));

                    try {
                        out = new ObjectOutputStream(new FileOutputStream("save.txt"));
                        out.writeObject(saves);
                        System.out.println("Game has been saved.");
                    }
                    catch (Exception e)
                    {
                    }
                    finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    showStatus();
                    break;
                }
            }

            if(cur >= 3*n/4 && lastCheckPoint==2)  //Checkpoint 3
            {
                lastCheckPoint=3;

                System.out.print("Third checkpoint reached." +
                        "\nWould you like to " +
                        "\n(1)save the game and exit" +
                        "\n(2)continue the game?: ");

                if(s!=null)
                    sel=s.nextInt();

                if(sel==1)
                {
                    HashMap<String, GameSaved> saves;

                    ObjectInputStream in=null;
                    ObjectOutputStream out=null;

                    try
                    {
                        in = new ObjectInputStream(new FileInputStream("save.txt"));
                        saves = (HashMap<String, GameSaved>) in.readObject();
                    }
                    catch (Exception e)
                    {
                        saves=new HashMap<String, GameSaved>();
                    }
                    finally
                    {
                        try {
                            in.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                        catch (NullPointerException e){
                            //e.printStackTrace();
                        }
                    }

                    saves.put(name,new GameSaved(track,die,n,name,cur,outOfCage,lastCheckPoint,Snake.getSnakeBites(),Cricket.getCricketBites(),Vulture.getVultureBites(),Trampoline.getTrampolines()));

                    try {
                        out = new ObjectOutputStream(new FileOutputStream("save.txt"));
                        out.writeObject(saves);
                        System.out.println("Game has been saved.");
                    }
                    catch (Exception e)
                    {
                    }
                    finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    showStatus();
                    break;
                }
            }

            int roll=die.roll();
            System.out.print("[Roll-"+die.getRolls()+"]: "+name+" rolled "+roll+" at Tile-"+(cur+1)+". ");

            if(cur==0 && !outOfCage)    //Out of cage
            {
                if(roll==6)
                {
                    System.out.println("You are out of the cage! You get a free roll");
                    outOfCage=true;
                }
                else
                    System.out.println("OOPs you need 6 to start.");
            }

            else if( (cur+roll) == n-1 )    //Current roll sends to Tile n
            {
                System.out.println("landed on Tile "+n);
                throw new GameWinnerException();
            }

            else
            {
                if((cur+roll)>=n)
                {
                    System.out.println("landed on Tile "+(cur+1)+".");
                    continue;
                }
                cur+=roll;

                System.out.println("landed on Tile "+(cur+1)+".\n" +
                        "\tTrying to shake Tile "+(cur+1));

                Tile t1=track[cur];
                int act=0;

                System.out.println("\t"+t1);

                try {
                    t1.shake();
                }
                catch (VultureBiteException ex)
                {
                    System.out.println("\tEXCEPTION OCCURRED: "+ex.getMessage());
                }
                catch (CricketBiteException ex)
                {
                    System.out.println("\tEXCEPTION OCCURRED: "+ex.getMessage());
                }
                catch (SnakeBiteException ex)
                {
                    System.out.println("\tEXCEPTION OCCURRED: "+ex.getMessage());
                }
                catch (TrampolineException ex)
                {
                    System.out.println("\tEXCEPTION OCCURRED: "+ex.getMessage());
                }
                catch (Exception e)
                {
                    System.out.println("Somethings going wrong. Terminating...");
                    System.exit(0);
                }
                finally {
                    act=t1.getX();
                }

                if((cur+act)<0)
                {
                    cur=0;
                    System.out.println("\t"+name+" moved to Tile 1 as it can't go back further.");
                    outOfCage=false;
                }
                else if((cur+act)>=n)
                {
                    cur=n-1;
                    System.out.println("\t"+name+" moved to Tile "+n+" as it can't go ahead further.");
                }
                else
                {
                    cur+=act;
                    System.out.println("\t"+name+" moved to Tile "+(cur+1));
                }
            }

            if( cur == n-1 )    //If trampoline sent ahead
            {
                throw new GameWinnerException();
            }
        }

        return cur;
    }
}
