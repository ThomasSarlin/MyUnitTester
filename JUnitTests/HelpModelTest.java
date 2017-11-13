
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class HelpModelTest {

    //Tests for checkTestMethod
    @Test
    public void shouldRecognizeValidTestMethod() throws NoSuchMethodException {
        Assert.assertTrue(HelpMethods.checkTestMethod(
                OneMethodTest.class.getMethod("testReturnTrue")));
    }
    @Test
    public void shouldRecognizeInvalidTestMethod()
            throws NoSuchMethodException {
        Assert.assertFalse(HelpMethods.checkTestMethod(
                OneMethodTest.class.getMethod("setUp")));
    }

    //Tests for checkClass
    @Test
    public void shouldIgnoreClassWithParamConstr() throws NoSuchMethodException, ClassNotFoundException {
        Assert.assertEquals("Invalid parameter count",
                HelpMethods.checkClass("TestClassWithParameter"));
    }

    @Test
    public void shouldIdentifyTestClass() throws NoSuchMethodException, ClassNotFoundException {
        Assert.assertEquals("Valid class"
                ,HelpMethods.checkClass("OneMethodTest"));
    }

    @Test
    public void shouldReturnNoSuchClass() throws NoSuchMethodException
            , ClassNotFoundException {
        Assert.assertEquals("Class not found"
                ,HelpMethods.checkClass("FakeMethodTest"));
    }

    @Test
    public void shouldReturnFalseInvalidTestClass() throws NoSuchMethodException
            , ClassNotFoundException {
        Assert.assertEquals("does not implement TestClass"
                ,HelpMethods.checkClass("TestNoImplementation"));
    }


    //Tests for instantiateClassObject();
    @Test
    public void shouldInstantiateClassObject(){
        Assert.assertEquals(OneMethodTest.class,HelpMethods
                .instantiateClassObject("OneMethodTest")
                .getClass());
    }
    @Test
    public void shouldNotInstantiateClassObject(){
        Assert.assertEquals(null
                ,HelpMethods.instantiateClassObject("Derp"));
    }

    //Tests for initiateTempClass

    @Test
    public void shouldInitiateTempClass(){
        Assert.assertEquals(OneMethodTest.class,HelpMethods
                .initiateTempClass("OneMethodTest"));
    }
    @Test
    public void shouldNotInitiateTempClass(){
        Assert.assertEquals(null,HelpMethods
                .initiateTempClass("Bombastic"));
    }

    /// /Tests for tryMethod
    @Test
    public void shouldRunMethod() throws NoSuchMethodException
            , IllegalAccessException, InvocationTargetException {

        OneMethodTest oneMethodTest = new OneMethodTest();
        HelpMethods.tryMethod("testReturnTrue"
                ,OneMethodTest.class,oneMethodTest);
    }

    @Test(expected = NoSuchMethodException.class)
    public void shouldThrowMethodException() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        OneMethodTest oneMethodTest = new OneMethodTest();
        HelpMethods.tryMethod("doesNotExistMethod"
                ,OneMethodTest.class,oneMethodTest);
    }

    //Tests for initiateMethods
    @Test
    public void shouldInitiateMethods()
            throws ClassNotFoundException {
        Assert.assertTrue(HelpMethods
                .initiateMethods("OneMethodTest")!=null);
    }
    @Test
    public void shouldReturnNullInMethodInitiation()
            throws ClassNotFoundException {
        Assert.assertTrue(HelpMethods
                .initiateMethods("TestClassWithoutTests")==null);
    }
    @Test (expected = ClassNotFoundException.class)
    public void shouldThrowExceptionIfClassNonexistent()
            throws ClassNotFoundException {
        HelpMethods.initiateMethods("SomeThingWrong");
    }

    //Tests for invokeMethods

    @Test
    public void shouldReturnStringArray() throws ClassNotFoundException {
        Assert.assertEquals(ArrayList.class,HelpMethods.invokeMethods(
                HelpMethods.initiateMethods("OneMethodTest"),
                OneMethodTest.class,new OneMethodTest()).getClass());
    }
}

