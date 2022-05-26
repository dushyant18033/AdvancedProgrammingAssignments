public class item
{
    public static String[] OFFERS={"None", "buy one get one free","25% off"};

    private final int id;
    private final String name;
    private final String category;
    private float price;
    private int quantity;
    private int offer;  //0 - None, 1 - BuyOneGetOneFree, 2 - 25%Off
    private final merchant mr;

    public merchant getMr() {
        return mr;
    }

    item(int i, String n, float p, int q, String cat, merchant m)
    {
        id=i;
        name=n;
        price=p;
        quantity=q;
        category=cat;
        mr=m;
        this.offer=0;
    }

    public item copy(int q)
    {
        float p=0;
        if(offer==2)
            p= (float) (0.75*price*q);
        else if(offer==1)
            p = (q/2 + q%2)*price;
        else
            p=price*q;

        quantity-=q;

        return new item(id,name,p,q,OFFERS[offer],mr);
    }

    public void update(int p, int q)
    {
        System.out.println("Enter edit details.");
        System.out.print("Item price: ");
        this.setPrice(p);
        System.out.print("Item quantity: ");
        this.setQuantity(q);
        System.out.println(this);
    }

    public String toString()
    {
        return id+" "+name+" "+price+" "+quantity+" "+OFFERS[offer]+" "+category;
    }

    public String boughtItem()
    {
        return "Bought item "+name+" quantity: "+quantity+" for Rs "+price+" from "+mr.getName();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }
}
