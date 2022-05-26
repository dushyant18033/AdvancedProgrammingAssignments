import org.junit.Test;

public class CricketTest
{
    @Test(expected = CricketBiteException.class, timeout=1000)
    public void CricketExceptions() throws Exception
    {
        Tile cricket=new Cricket(0);
        cricket.shake();
    }
}
