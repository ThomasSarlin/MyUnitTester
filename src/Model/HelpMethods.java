package Model;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;

public class HelpMethods {
    /**
     * Tries to Controller a specified Method from
     * a specified class on a specific Object
     * @param method - Method to Controller
     * @param tempClass - Class which has method
     * @param classObject - Instance of Object(should be of class tempClass)
     */
    public static void tryMethod(String method,Class<?> tempClass
            , Object classObject) throws NoSuchMethodException
            , InvocationTargetException, IllegalAccessException {

            tempClass.getMethod(method)
                    .invoke(classObject);
    }

    /**
     * Invokes each method in the specified class which fills the requirements
     * (name starts with "test", 0 parameters, returns boolean)
     * @param methods - all methods of corresponding class
     * @param tempClass - Class which contains methods (Reflection)
     * @param classObject - Instantiated object of said class
     * @return ArrayList representing the data from the invocations.
     */
    public static ArrayList<String> invokeMethods(ArrayList<Method> methods
            , Class<?> tempClass, Object classObject) {

        boolean result;
        ArrayList<String> methodResults=new ArrayList<>();
        int successCount=0,failCount=0,exceptionFailCount=0;

        for (Method m:methods){
            try {
                tryMethod("setUp",tempClass,classObject);
            } catch (NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                Debug.log(Level.WARNING,e.getCause()
                        +" caught in method tryMethod");
            }
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

        try {
            tryMethod("tearDown",tempClass,classObject);
        } catch (NoSuchMethodException | IllegalAccessException
                | InvocationTargetException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method tryMethod");
        }
        if(methodResults.size()!=0)
            methodResults.add("\nSuccessful tests: " +successCount
                    +"\nFailed tests:" +failCount
                    +"\nFailed tests with Exception thrown: "
                    + exceptionFailCount + "\n ------------------ \n ");
        else
            methodResults.add("No tests available");
        return methodResults;
    }


    /**
     * Checks if class by the name "className" implements TestClass.class
     * @param className name of class to be checked
     * @return if class is implementation of TestClass
     */

    public static boolean checkClass(String className) throws ClassNotFoundException{
        boolean result = false;

        try {
            result= (Tests.TestClass.class.isAssignableFrom(Class.forName(className))
                        &&Class.forName(className)
                        .getConstructor().getParameterCount()==0);
        } catch (NoSuchMethodException e) {
            Debug.log(Level.WARNING,e.getMessage()
                    + " caught in method checkClass");
        }
        return result;
    }

    /**
     * @return methods represented in class from className
     */
    public static ArrayList<Method> initiateMethods(String className)
            throws ClassNotFoundException {
        ArrayList<Method> result = new ArrayList<>();
        Method[] methods = Class.forName(className).getMethods();
        for(Method m:methods)
            if(checkTestMethod(m))result.add(m);

        return (result.size()==0)?null:result;
    }
    /**
     * Checks if method name starts with "test"
     * , has 0 parameters and returns a boolean
     * @param m method to be checked
     * @return if correct format
     */
    public static boolean checkTestMethod(Method m){
        return(m.getName().substring(0,4).equals("test")
                &&m.getParameterCount()==0
                &&m.getReturnType().toString().equals("boolean"));
    }

    /**
     * @return Object representing given Class from className
     */
    public static Object instantiateClassObject(String className){
        Object result= null;
        try {

            result= Class.forName(className).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateClass");
        }
        return result;
    }

    /**
     * @return initiated Class from given className
     */
    public static Class<?> initiateTempClass(String className){
        Class<?> result=null;
        try {
            result= Class.forName(className);
        } catch (ClassNotFoundException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateTempClass: "
                    +className + ".class does not exist");
        }
        return result;
    }
}
