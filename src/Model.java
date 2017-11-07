
import com.sun.media.jfxmedia.logging.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;

public class Model {
    private String className;

    public ArrayList<String> runTest(String className) throws IOException {
        this.className=className;
        Method[] methods;
        ArrayList<String> methodResults= new ArrayList<>();
        try {
            methods = Class.forName(className).getMethods();
            Object tempClass=Class.forName(className).newInstance();
            methodResults=runMethods(methods,tempClass);
        } catch (ClassNotFoundException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method runTest");
        } catch (IllegalAccessException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method runTest");
        } catch (InstantiationException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method runTest");
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
                    methodResults.add(m.getName());
                    result = (boolean) m.invoke(tempClass);
                    if(result) {
                        methodResults.add(" SUCCESS\n");
                        successCount++;
                    }
                    else {
                        methodResults.add(" FAIL\n");
                        failCount++;
                    }
                }catch(InvocationTargetException e){
                    methodResults.add(" FAIL Generated "
                            + e.getCause() +"\n");
                    exceptionFailCount++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        tryTearDown(tempClass);
        if(methodResults.size()!=0)
            methodResults.add("\nSuccessful tests: " +successCount
                    +"\nFailed tests:" +failCount
                    +"\nFailed tests with Exception thrown: "
                    + exceptionFailCount);
        else
            methodResults.add("No tests available");
        return methodResults;
    }

    private void trySetUp(Object tempClass){

        try {
            Class.forName(className).getMethod("setUp")
                    .invoke(tempClass);
        } catch (IllegalAccessException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method trySetUp");
        } catch (InvocationTargetException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method trySetUp");
        } catch (NoSuchMethodException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method trySetUp");
        } catch (ClassNotFoundException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method trySetUp");
        }
    }

    private void tryTearDown(Object tempClass){

        try {
            Class.forName(className).getMethod("tearDown")
                    .invoke(tempClass);
        } catch (NoSuchMethodException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method tryTearDown, no teardown available");
        } catch (IllegalAccessException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method tryTearDown");
        } catch (InvocationTargetException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method tryTearDown");
        } catch (ClassNotFoundException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method tryTearDown");
        }
    }


    public boolean checkTextField(String className){
        try {
            return (TestClass.class.isAssignableFrom(Class.forName(className))
                    &&Class.forName(className).getConstructor().getParameterCount()==0);
        }
        catch (ClassNotFoundException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +", invalid className: " +className);
            return false;
        } catch (NoSuchMethodException e) {
            DebugLog.log(Level.WARNING,e.getCause()
                    +" caught in method checkTextField");
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
