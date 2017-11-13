
/**
 * Class Responsibility: Model, implement the UnitTest.
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Model {

    /**
     * @param className
     * @return list of method results or
     * null if class doesn't implement TestClass.class or
     * if an exception is caught, specified in log.
     */
    public ArrayList<String> initiateTest(String className)
            throws ClassNotFoundException {
            ArrayList<String> result = new ArrayList<>();
            result.add(HelpMethods.checkClass(className));
            return(result.get(0).equals("Valid class")?runTest(className):result);
    }

    /**
     * Main method of model, invokes methods of given class
     * and returns a test result.
     * @param className - name of Class
     * @return ArrayList representing result from tests
     * or null if error has occurred.
     */
    private ArrayList<String> runTest(String className)
            throws ClassNotFoundException {
        ArrayList<Method> methods= HelpMethods.initiateMethods(className);

        Object classObject= HelpMethods.instantiateClassObject(className);
        Class<?> tempClass = HelpMethods.initiateTempClass(className);

        return (classObject==null|tempClass==null) ?
                null : HelpMethods.invokeMethods(methods,tempClass,classObject);
    }
}
