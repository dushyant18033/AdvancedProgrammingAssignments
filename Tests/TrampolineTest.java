import org.junit.Test;

public class TrampolineTest
{
    @Test(expected = TrampolineException.class, timeout=1000)
    public void TrampolineExceptions() throws Exception
    {
        Tile trampoline=new Trampoline(0);
        trampoline.shake();
    }
}
