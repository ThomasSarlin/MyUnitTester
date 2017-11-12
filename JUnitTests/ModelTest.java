/**
 * JUnit test for Model.
 * @Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */
import org.junit.Test;
import org.junit.Assert;
import java.io.IOException;
import java.util.ArrayList;

public class ModelTest {
    @Test
    public void shouldBeAbleToCreate(){
        new Model();
    }

    @Test
    public void shouldReturnArrayList() throws IOException, NoSuchMethodException, ClassNotFoundException {
        Assert.assertTrue(new Model().initiateTest("OneMethodTest")
                .getClass().equals(ArrayList.class));
    }
    @Test
    public void shouldReturnStringList() throws IOException, NoSuchMethodException, ClassNotFoundException {
        Assert.assertTrue(new Model().initiateTest("OneMethodTest")
                .get(0).getClass().equals(String.class));
    }

    @Test
    public void shouldIndicateNoTestsAvailable() throws IOException, NoSuchMethodException, ClassNotFoundException {
        Assert.assertTrue(new Model().initiateTest("TestClassWithoutTests")
                .get(0).equals("No tests available"));
    }
    @Test(expected = ClassNotFoundException.class)
    public void shouldReturnNullAndThrowException()
            throws ClassNotFoundException {

        Assert.assertEquals(null
                ,new Model().initiateTest("NoRealClass"));
    }
}
