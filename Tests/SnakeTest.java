import org.junit.Test;

public class SnakeTest
{
    @Test(expected = SnakeBiteException.class, timeout=1000)
    public void SnakeExceptions() throws Exception
    {
        Tile snake=new Snake(0);
        snake.shake();
    }
}
