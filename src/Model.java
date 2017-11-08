
/**
 * Class Responsibility: Model, implement the UnitTest.
 *@Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;

public class Model {
    private String className;

    /**
     * @param className
     * @return list of method results or
     * null if class doesn't implement TestClass.class or
     * if an exception is caught, specified in log.
     */
    public ArrayList<String> initiateTest(String className){
        return(checkClass(className)?runTest(className):null);
    }

    /**
     * Main method of model, invokes methods of given class
     * and returns a test result.
     * @param className - name of Class
     * @return ArrayList representing result from tests
     * or null if error has occurred.
     */
    private ArrayList<String> runTest(String className){
        this.className=className;
        Method[] methods=initiateMethods();
        Object classObject=instantiateClassObject();
        Class<?> tempClass = initiateTempClass();
        return (methods==null|classObject==null|tempClass==null)
                ? null: InvokeMethods(methods,tempClass,classObject);
    }

    /**
     * @return methods represented in class from className
     */
    private Method[] initiateMethods(){
        try {
            return Class.forName(className).getMethods();
        } catch (ClassNotFoundException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method initateMethods");
            return null;
        }
    }

    /**
     * @return Object representing given Class from className
     */
    private Object instantiateClassObject(){
        try {

            return Class.forName(className).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateClass");
            return null;
        }
    }

    /**
     * @return initiated Class from given className
     */
    private Class<?> initiateTempClass(){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateTempClass: "
                    +className + ".class does not exist");
            return null;
        }
    }

    /**
     * Invokes each method in the specified class which fills the requirements
     * (name starts with "test", 0 parameters, returns boolean)
     * @param methods - all methods of corresponding class
     * @param tempClass - Class which contains methods (Reflection)
     * @param classObject - Instantiated object of said class
     * @return ArrayList representing the data from the invocations.
     */
    private ArrayList<String> InvokeMethods(Method[] methods
            , Class<?> tempClass, Object classObject) {

        boolean result;
        ArrayList<String> methodResults=new ArrayList<>();
        int successCount=0,failCount=0,exceptionFailCount=0;
        for (Method m:methods){
            if(checkTestMethod(m)){
                tryMethod("setUp",tempClass,classObject);

                try {
                    methodResults.add(m.getName());
                    result = (boolean) m.invoke(classObject);
                    if(result) {
                        methodResults.add(" SUCCESS\n");
                        successCount++;
                    }
                    else {
                        methodResults.add(" FAIL\n");
                        failCount++;
                    }
                } catch(InvocationTargetException | IllegalAccessException e){
                    methodResults.add(" FAIL Generated "
                            + e.getCause() +"\n");
                    exceptionFailCount++;
                }
            }
        }

        tryMethod("tearDown",tempClass,classObject);
        if(methodResults.size()!=0)
            methodResults.add("\nSuccessful tests: " +successCount
                    +"\nFailed tests:" +failCount
                    +"\nFailed tests with Exception thrown: "
                    + exceptionFailCount);
        else
            methodResults.add("No tests available");
        return methodResults;
    }

    /**
     * Tries to run a specified Method from
     * a specified class on a specific Object
     * @param method - Method to run
     * @param tempClass - Class which has method
     * @param classObject - Instance of Object(should be of class tempClass)
     */
    private void tryMethod(String method,Class<?> tempClass
            , Object classObject){

        try {
            tempClass.getMethod(method)
                    .invoke(classObject);

        } catch (NoSuchMethodException | IllegalAccessException
                | InvocationTargetException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method tryMethod");
        }
    }

    /**
     * Checks if class by the name "className" implements TestClass.class
     * @param className name of class to be checked
     * @return if class is implementation of TestClass
     */

    boolean checkClass(String className){
        try {
            return (TestClass.class.isAssignableFrom(Class.forName(className))
                    &&Class.forName(className)
                    .getConstructor().getParameterCount()==0);
        }
        catch (ClassNotFoundException | NoSuchMethodException e) {
            Debug.log(Level.INFO,e.getCause()
                    +" caught in method checkClass");
            return false;
        }
    }

    /**
     * Checks if method name starts with "test"
     * , has 0 parameters and returns a boolean
     * @param m method to be checked
     * @return if correct format
     */
    private boolean checkTestMethod(Method m){
        return(m.getName().substring(0,4).equals("test")
                &&m.getParameterCount()==0
                &&m.getReturnType().toString().equals("boolean"));
    }
}
