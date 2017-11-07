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
    public void shouldIdentifyTestClass(){
        Assert.assertTrue(new Model().checkTextField("OneMethodTest"));
    }
    @Test
    public void shouldIdentifyInvalidTestClass(){
        Assert.assertFalse(new Model()
                .checkTextField("FakeMethodTest"));
    }
    @Test
    public void shouldReturnArrayWithStrings() throws IOException {
        Assert.assertTrue(new Model().runTest("OneMethodTest")
                .getClass().equals(ArrayList.class));
    }

    @Test
    public void shouldIgnoreClassWithParameterConstructor(){
        Assert.assertFalse(
                new Model().checkTextField("TestClassWithParameter"));
    }

    @Test
    public void shouldIndicateNoTestsAvailable() throws IOException {
        Assert.assertTrue(new Model().runTest("TestClassWithoutTests")
                .get(0).equals("No tests available"));
    }
}
