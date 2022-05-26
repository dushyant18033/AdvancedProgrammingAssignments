import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner s=new Scanner(System.in);
        float companyAcc=0;

        int id=0;       //variable for storing input inside switch-case
        int iCode=0;    //Number of items

        HashMap<String, ArrayList<item>> products=new HashMap<>();  //category -> item
        HashMap<Integer, item> productsByCode=new HashMap<>();      //itemID -> item
        HashMap<Integer,UserAccount> C=new HashMap<>();                //userID -> customer
        HashMap<Integer,UserAccount> M=new HashMap<>();                //userID -> merchant

        //5 default Merchant Accounts
        M.put(1,new merchant(1,"jack","Delhi"));
        M.put(2,new merchant(2,"john","Mumbai"));
        M.put(3,new merchant(3,"james","Kolkata"));
        M.put(4,new merchant(4,"jeff","Chennai"));
        M.put(5,new merchant(5,"joseph","Bangalore"));

        //5 default Customer Accounts
        C.put(1,new customer(1,"ali","Jaipur"));
        C.put(2,new customer(2,"nobby","Kanpur"));
        C.put(3,new customer(3,"bruno","Lucknow"));
        C.put(4,new customer(4,"borat","Ahmedabad"));
        C.put(5,new customer(5,"aladeen","Patna"));

        while (true)
        {
            //Display Main Menu
            System.out.println("Welcome to Mercury\n" +
                    "1) Enter as Merchant\n" +
                    "2) Enter as Customer\n" +
                    "3) See user details\n" +
                    "4) Company account balance\n" +
                    "5) Exit");
            int response=s.nextInt();

            switch (response)
            {
                case 1: //Enter as Merchant
                    System.out.println("Choose Merchant");
                    for(int i : M.keySet())
                        System.out.println(i+" "+M.get(i).getName());

                    id=s.nextInt();
                    if(!M.containsKey(id))
                    {
                        System.out.println("No user with entered id has a Merchant A/C.");
                        break;
                    }
                    merchant cur= (merchant) M.get(id);

                    while (true)
                    {
                        System.out.println("Welcome "+cur.getName());
                        System.out.println("Merchant Menu\n" +
                                "1) Add item\n" +
                                "2) Edit item\n" +
                                "3) Search by category\n" +
                                "4) Add offer\n" +
                                "5) Rewards won\n" +
                                "6) Exit");

                        int reply=s.nextInt();
                        if(reply==6)    //Exit
                            break;

                        switch (reply)
                        {
                            case 1: //Add item
                                if(cur.getMainLots()<=(-cur.getRewardLots()))
                                {
                                    System.out.println("Cannot add more items to your a/c. Items limit has been exhausted");
                                    break;
                                }

                                System.out.println("Enter item details");
                                System.out.print("Name: ");     String n=s.next();
                                System.out.print("Price: ");    int p=s.nextInt();
                                System.out.print("Quantity: "); int q=s.nextInt();
                                System.out.print("Category: "); String cat=s.next();

                                item i1=new item(++iCode,n,p,q,cat,cur);
                                System.out.println(i1);
                                cur.getItems().put(iCode,i1);

                                if(!products.containsKey(cat))
                                    products.put(cat,new ArrayList<item>());

                                products.get(cat).add(i1);
                                productsByCode.put(iCode,i1);
                                cur.setMainLots(cur.getMainLots()-1);
                                break;

                            case 2: //Edit item
                                System.out.println("Choose item by code");
                                for (item i:cur.getItems().values())
                                    System.out.println(i);
                                int sel=s.nextInt();
                                if(sel>iCode)
                                    System.out.println("Item with given ID not found in your a/c");
                                else
                                {
                                    item i123=cur.getItems().get(sel);
                                    System.out.println("Enter price and quantity: ");
                                    i123.update(s.nextInt(),s.nextInt());
                                }
                                break;

                            case 3: //Search items by category
                                cur.productSearch(s,products);
                                break;

                            case 4: //Add offer
                                cur.updateOffer(s, iCode);
                                break;

                            case 5: //Rewards won
                                System.out.println(cur.rewards());
                                break;

                            default:
                                System.out.println("Invalid response");
                        }
                    }
                    break;
                    ////////////////////////////////////////////////////

                case 2: //Enter as Customer
                    System.out.println("Choose Customer");
                    for(int i : C.keySet())
                        System.out.println(i+" "+C.get(i).getName());

                    id=s.nextInt();
                    if(!C.containsKey(id))
                    {
                        System.out.println("No user with entered id has a Customer A/C.");
                        break;
                    }
                    customer cust= (customer) C.get(id);

                    while(true)
                    {
                        System.out.println("Welcome "+cust.getName());
                        System.out.println("Customer Menu\n" +
                                "1) Search item\n" +
                                "2) checkout cart\n" +
                                "3) Reward won\n" +
                                "4) print latest orders\n" +
                                "5) Exit");
                        int reply=s.nextInt();
                        if(reply==5)
                            break;

                        switch (reply)
                        {
                            case 1: //Search item
                                System.out.println("Choose item by code");
                                cust.productSearch(s,products);
                                System.out.print("Enter item code: ");
                                int code=s.nextInt();
                                if(code>iCode || code<=0)
                                {
                                    System.out.println("No item with given code exist");
                                    break;
                                }
                                System.out.print("Enter quantity: ");
                                int quan=s.nextInt();
                                item buy=productsByCode.get(code);
                                if(buy.getQuantity()<quan)
                                {
                                    System.out.println("Sorry. Not enough quantity available.");
                                    break;
                                }
                                System.out.println("Choose method of transaction\n" +
                                        "1) Buy item\n" +
                                        "2) Add item to cart\n" +
                                        "3) Exit");
                                int option=s.nextInt();
                                if(option==3)
                                    break;
                                else if(option==2)
                                    cust.addToCart(buy.copy(quan));
                                else if(option==1)
                                    companyAcc+=0.01*cust.Buy(buy.copy(quan));
                                break;

                            case 2: //Checkout cart
                                companyAcc+=0.01*cust.checkOut();
                                break;

                            case 3: //Total rewards won
                                System.out.println(cust.rewards());
                                break;

                            case 4: //Print latest orders
                                cust.lastTenTransactions();
                                break;

                            default:
                                System.out.println("Invalid response");
                        }
                    }
                    break;
                    ////////////////////////////////////////////////////

                case 3: //User details
                    String type=s.next();
                    id=s.nextInt();
                    if(type.equals("M"))
                        System.out.println(M.get(id));
                    else if(type.equals("C"))
                        System.out.println(C.get(id));
                    break;

                case 4: //Company A/c Balance
                    System.out.println("Company Account Balance is Rs."+companyAcc);
                    break;

                case 5: //Exit
                    System.out.println("Terminating...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid response");
            }
        }
    }
}
