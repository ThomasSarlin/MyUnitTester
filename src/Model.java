import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;

public class Model {
    private String className;

    public ArrayList<String> runTest(String className) throws IOException {
        this.className=className;
        Method[] methods=initiateMethods();
        Object classObject=instantiateClassObject();
        Class<?> tempClass = initiateTempClass();
        return (methods==null|classObject==null)
                ?null:runMethods(methods,tempClass,classObject);
    }

    private Method[] initiateMethods(){
        try {
            return Class.forName(className).getMethods();
        } catch (ClassNotFoundException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method initateMethods");
            return null;
        }
    }
    private Object instantiateClassObject(){
        try {

            return Class.forName(className).newInstance();
        } catch (ClassNotFoundException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateClass");
            return null;
        } catch (IllegalAccessException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateClass");
            return null;
        } catch (InstantiationException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method instantiateClass");
            return null;
        }
    }

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

    private ArrayList<String> runMethods(Method[] methods
            ,Class<?> tempClass, Object classObject) {

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
                } catch(InvocationTargetException e){
                    methodResults.add(" FAIL Generated "
                            + e.getCause() +"\n");
                    exceptionFailCount++;
                } catch (IllegalAccessException e) {
                    Debug.log(Level.WARNING,e.getCause()
                            +" caught in method runMethods");
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

    private void tryMethod(String method,Class<?> tempClass, Object classObject){

        try {
            tempClass.getMethod(method)
                    .invoke(classObject);
        } catch (IllegalAccessException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method tryMethod");
        } catch (InvocationTargetException e) {
            Debug.log(Level.WARNING,e.getCause()
                    +" caught in method tryMethod");
        } catch (NoSuchMethodException e) {
            Debug.log(Level.INFO,e.getCause()
                    +" caught in method tryMethod"+": "
                    +method+ " does not exist");
        }
    }

    public boolean checkTextField(String className){
        try {
            return (TestClass.class.isAssignableFrom(Class.forName(className))
                    &&Class.forName(className)
                    .getConstructor().getParameterCount()==0);
        }
        catch (ClassNotFoundException e) {
            Debug.log(Level.INFO,e.getCause()
                    +", invalid className: " +className);
            return false;
        } catch (NoSuchMethodException e) {
            Debug.log(Level.INFO,e.getCause()
                    +", caught in method checkTextField");
            return false;
        }
    }

    private boolean checkTestMethod(Method m){
        return(m.getName().charAt(0)=='t'
                &&m.getName().charAt(1)=='e'
                &&m.getName().charAt(2)=='s'
                &&m.getName().charAt(3)=='t'
                &&m.getParameterCount()==0
                &&m.getReturnType().toString().equals("boolean"));
    }
}
