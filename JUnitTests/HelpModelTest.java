
import Model.HelpMethods;
import Tests.OneMethodTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class HelpModelTest {

    //Tests for checkTestMethod
    @Test
    public void shouldRecognizeValidTestMethod() throws NoSuchMethodException {
        Assert.assertTrue(HelpMethods.checkTestMethod(
                Tests.OneMethodTest.class.getMethod("testReturnTrue")));
    }
    @Test
    public void shouldRecognizeInvalidTestMethod()
            throws NoSuchMethodException {
        Assert.assertFalse(HelpMethods.checkTestMethod(
                Tests.OneMethodTest.class.getMethod("setUp")));
    }

    //Tests for checkClass
    @Test
    public void shouldIgnoreClassWithParamConstr() throws NoSuchMethodException, ClassNotFoundException {
        Assert.assertFalse(
                HelpMethods.checkClass("Tests.TestClassWithParameter"));
    }

    @Test
    public void shouldIdentifyTestClass() throws NoSuchMethodException, ClassNotFoundException {
        Assert.assertTrue(HelpMethods.checkClass("Tests.OneMethodTest"));
    }

    @Test (expected = ClassNotFoundException.class)
    public void shouldThrowExceptionNoSuchClass() throws NoSuchMethodException
            , ClassNotFoundException {
        HelpMethods.checkClass("FakeMethodTest");
    }

    @Test
    public void shouldReturnFalseInvalidTestClass() throws NoSuchMethodException
            , ClassNotFoundException {
        Assert.assertFalse(HelpMethods.checkClass(
                "Tests.TestNoImplementation"));
    }


    //Tests for instantiateClassObject();
    @Test
    public void shouldInstantiateClassObject(){
        Assert.assertEquals(OneMethodTest.class,HelpMethods
                .instantiateClassObject("Tests.OneMethodTest")
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
        Assert.assertEquals(Tests.OneMethodTest.class,HelpMethods
                .initiateTempClass("Tests.OneMethodTest"));
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
                .initiateMethods("Tests.OneMethodTest")!=null);
    }
    @Test
    public void shouldReturnNullInMethodInitiation()
            throws ClassNotFoundException {
        Assert.assertTrue(HelpMethods
                .initiateMethods("Tests.TestClassWithoutTests")==null);
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
                HelpMethods.initiateMethods("Tests.OneMethodTest"),
                OneMethodTest.class,new OneMethodTest()).getClass());
    }
}

