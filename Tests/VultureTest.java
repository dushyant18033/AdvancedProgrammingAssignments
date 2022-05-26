import org.junit.Test;

public class VultureTest
{
    @Test(expected = VultureBiteException.class, timeout=1000)
    public void VultureExceptions() throws Exception
    {
        Tile vulture=new Vulture(0);
        vulture.shake();
    }
}
