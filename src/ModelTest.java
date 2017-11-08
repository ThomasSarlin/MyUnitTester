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
    public void shouldIdentifyTestClass(){
        Assert.assertTrue(new Model().checkClass("OneMethodTest"));
    }
    @Test
    public void shouldIdentifyInvalidTestClass(){
        Assert.assertFalse(new Model()
                .checkClass("FakeMethodTest"));
    }
    @Test
    public void shouldReturnArrayList() throws IOException {
        Assert.assertTrue(new Model().initiateTest("OneMethodTest")
                .getClass().equals(ArrayList.class));
    }
    @Test
    public void shouldReturnStringList() throws IOException {
        Assert.assertTrue(new Model().initiateTest("OneMethodTest")
                .get(0).getClass().equals(String.class));
    }

    @Test
    public void shouldIgnoreClassWithParamConstr(){
        Assert.assertFalse(
                new Model().checkClass("TestClassWithParameter"));
    }

    @Test
    public void shouldIndicateNoTestsAvailable() throws IOException {
        Assert.assertTrue(new Model().initiateTest("TestClassWithoutTests")
                .get(0).equals("No tests available"));
    }
}
