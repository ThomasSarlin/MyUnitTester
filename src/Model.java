
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Model {

    public boolean checkTextField(String className){
        try {
            return TestClass.class.isAssignableFrom(Class.forName(className));
            }
            catch (ClassNotFoundException e1) {
            return false;
        }
    }

    public ArrayList<String> runTest(String className)
            throws InvocationTargetException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException {

        boolean result;
        Object tempClass;
        Method[] methods;

        ArrayList<String> methodResults= new ArrayList<>();
        int successCount=0;
        int failCount=0;
        int exceptionFailCount=0;

        tempClass=Class.forName(className).newInstance();
        methods=Class.forName(className).getMethods();

        for (Method m:methods){
            if(checkTestMethod(m)){
                Class.forName(className).getMethod("setUp")
                        .invoke(tempClass);

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
                }
            }
        }

        try {
            Class.forName(className).getMethod("tearDown")
                    .invoke(tempClass);
        } catch (NoSuchMethodException e) {
            System.out.println("No tearDown available");
            e.printStackTrace();
        }
        methodResults.add("\n\nSuccessful tests: " + successCount
                +"\nFailed tests: " + failCount
                +"\nException generated fails: " + exceptionFailCount);
        return methodResults;
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
