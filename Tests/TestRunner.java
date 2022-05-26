import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner
{
    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(TestSuite.class);

        if(result.wasSuccessful())
            System.out.println("|| ALL THE TEST CASES PASSED ||");
        else
        {
            System.out.println("|| THE FOLLOWING TESTS FAILED ||");

            for (Failure failure : result.getFailures())
                System.out.println(failure.toString());

        }
    }
}