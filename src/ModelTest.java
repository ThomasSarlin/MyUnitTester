import org.junit.Test;
import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ModelTest {
    @Test
    public void shouldBeAbleToCreate(){
        new Model();
    }
    @Test
    public void shouldIdentifyTestClass(){
        Assert.assertTrue(new Model().checkTextField("OneMethodTest"));
    }
    @Test
    public void shouldIdentifyInvalidTestClass(){
        Assert.assertFalse(new Model()
                .checkTextField("FakeMethodTest"));
    }
    @Test
    public void shouldReturnArrayWithStrings() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        Assert.assertTrue(new Model().runTest("OneMethodTest")
                .getClass().equals(ArrayList.class));
    }

    @Test
    public void shouldBeAbleToInitializeClass(){
        Model model = new Model();
    }
}
