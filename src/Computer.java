public final class Computer
{
    public static void play(String name, int n, Tile[] track, Dice die) throws GameWinnerException  /////MAIN GAME/////
    {
        System.out.println("===============GAME STARTED===============");
        int cur=0;
        boolean outOfCage=false;

        while(true)
        {
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
    }
}
