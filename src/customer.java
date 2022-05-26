import java.util.*;

public class customer implements UserAccount
{
    private final String name;
    private final String addr;
    private final int id;
    private float mainBalance;
    private float rewardBalance;
    private ArrayList<item> cart;
    private Stack<String> transactions;

    customer(int i, String n, String add)
    {
        name=n;
        addr=add;
        id=i;
        mainBalance=100;
        rewardBalance=0;
        transactions=new Stack<String>();
        cart=new ArrayList<item>();
    }

    public void lastTenTransactions()
    {
        if(transactions.size()==0)
        {
            System.out.println("No transaction history found in your a/c.");
            return;
        }
        Stack<String> temp = (Stack<String>) transactions.clone();

        int i=0;
        while( !temp.empty() && i<10)
        {
            System.out.println(temp.pop());
            i++;
        }
    }

    public float checkOut()
    {
        if(cart.size()==0)
        {
            System.out.println("Your cart is empty! Please add some items to your cart.");
            return 0;
        }
        Iterator it=cart.iterator();
        float pricePaid=0;

        while(it.hasNext())
        {
            item i= (item) it.next();
            float priceForI=i.getPrice();
            if((priceForI+pricePaid)*1.005 <= mainBalance+rewardBalance)
            {
                pricePaid += priceForI;
                i.setPrice((float) (priceForI*1.005));
                String success=i.boughtItem();
                System.out.println(success);
                transactions.push(success);
                i.getMr().addTotConToComp(priceForI);
            }
            else
            {
                System.out.println("Sorry you do not have enough balance to buy the remaining products in your cart.");
                break;
            }
        }

        cart=new ArrayList<>();

        mainBalance-=pricePaid*1.005;
        rewardBalance=(transactions.size()/5)*10;

        return pricePaid;
    }

    @Override
    public void productSearch(Scanner s, HashMap<String, ArrayList<item>> products)
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

    public float Buy(item i)
    {
        float priceForI=i.getPrice();

        if(priceForI*1.005 > (mainBalance+rewardBalance))
        {
            System.out.println("Sorry, you do not have enough balance.");
            return 0;
        }
        i.setPrice((float) (priceForI*1.005));

        transactions.push(i.boughtItem());
        i.getMr().addTotConToComp(priceForI);

        mainBalance-=i.getPrice();

        rewardBalance=(transactions.size()/5)*10;

        System.out.println("Item successfully bought.");

        return priceForI;
    }

    public void addToCart(item i) {
        cart.add(i);
    }

    @Override
    public String rewards()
    {
        return "Total rewards you've won is Rs "+this.rewardBalance;
    }

    @Override
    public String toString() {
        return "Name: "+name+"\nAddress: "+addr+"\nNumber of Orders Placed: "+transactions.size();
    }

    @Override
    public String getName() {
        return name;
    }
}