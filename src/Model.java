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
    public ArrayList<String> runTest(String className){

        Object tempClass;
        Method[] methods;
        ArrayList<String> methodResults = new ArrayList<>();

        return methodResults;
    }
}
