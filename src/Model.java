
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Model {
    String className;
    public ArrayList<String> runTest(String className){
        this.className=className;

        Method[] methods;
        ArrayList<String> methodResults= new ArrayList<>();
        try {
            methods = Class.forName(className).getMethods();
            Object tempClass=Class.forName(className).newInstance();
            methodResults=runMethods(methods,tempClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return methodResults;
    }

    private ArrayList<String> runMethods(Method[] methods, Object tempClass) {
        boolean result;
        ArrayList<String> methodResults=new ArrayList<>();
        int successCount=0,failCount=0,exceptionFailCount=0;
        for (Method m:methods){
            if(checkTestMethod(m)){
                trySetUp(tempClass);

                try {
                    result = (boolean) m.invoke(tempClass);
                    methodResults.add(m.getName());
                    if(result) {
                        methodResults.add(" SUCCESS\n");
                        successCount++;
                    }
                    else {
                        methodResults.add(" FAIL\n");
                        failCount++;
                    }
                }catch(InvocationTargetException e){
                    methodResults.add(m.getName() + " FAIL Generated "
                            + e.getCause());
                    exceptionFailCount++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        tryTearDown(tempClass);
        methodResults.add("\n\n\nSuccessful tests: " +successCount
                +"\nFailed tests:" +failCount
                +"\nFailed tests with Exception thrown: "+ exceptionFailCount);
        return methodResults;
    }

    private void trySetUp(Object tempClass){

        try {
            Class.forName(className).getMethod("setUp")
                    .invoke(tempClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void tryTearDown(Object tempClass){

        try {
            Class.forName(className).getMethod("tearDown")
                    .invoke(tempClass);
        } catch (NoSuchMethodException e) {
            System.out.println("No tearDown available");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean checkTextField(String className){
        try {
            return TestClass.class.isAssignableFrom(Class.forName(className));
        }
        catch (ClassNotFoundException e1) {
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
