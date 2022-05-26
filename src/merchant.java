import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class merchant implements UserAccount
{
    private final String name;
    private final String addr;
    private final int id;
    private float totConToComp;
    private int mainLots;
    private int rewardLots;
    private HashMap<Integer,item> items;

    merchant(int i, String n, String add)
    {
        name=n;
        addr=add;
        id=i;
        mainLots=10;
        rewardLots=0;
        totConToComp=0;
        items=new HashMap<>();
    }

    @Override
    public String toString() {
        return "Name: "+name+"\nAddress: "+addr+"\nTotal contribution to company's account: "+totConToComp;
    }

    @Override
    public String getName() {
        return name;
    }

    void updateOffer(Scanner s, int iCode)
    {
        System.out.println("Choose item by code");
        for (item i:items.values())
            System.out.println(i);

        int sel=s.nextInt();

        if(sel>iCode || sel<=0)
        {
            System.out.println("Item with given ID not found in your a/c");
            return;
        }
        else
        {
            System.out.println("choose offer\n" +
                    "1) buy one get one free\n" +
                    "2) 25% off");
            int of=s.nextInt();
            items.get(sel).setOffer(of);
        }
        System.out.println(items.get(sel));
    }

    @Override
    public String rewards()
    {
        return this.rewardLots+" lots won as reward.";
    }

    public void addTotConToComp(float totConToComp) {
        this.totConToComp += 0.005*totConToComp;
        this.rewardLots= (int) ((this.totConToComp)/100);
    }

    @Override
    public void productSearch(Scanner s,HashMap<String, ArrayList<item>> products)
    {
        System.out.println("Choose a category:-");
        int i=1;
        for(String keys:products.keySet())
            System.out.println((i++) +") "+keys);
        int ca=s.nextInt();
        if(ca>products.keySet().size() || ca<=0)
        {
            System.out.println("No such category exists.");
            return;
        }
        i=1;
        String find="";
        for(String keys:products.keySet())
        {
            if (i == ca)
                find = keys;
            i++;
        }
        Iterator iter=products.get(find).iterator();
        while(iter.hasNext())
        {
            item temp=(item)iter.next();
            System.out.println(temp);
        }
    }

    public int getMainLots() {
        return mainLots;
    }

    public void setMainLots(int mainLots) {
        this.mainLots = mainLots;
    }

    public int getRewardLots() {
        return rewardLots;
    }

    public HashMap<Integer, item> getItems() {
        return items;
    }
}
