/**
 * TestClass called in GUI to test if SwingWorker works as intended.
 */

public class TestClassWait implements TestClass {
    public TestClassWait(){}
    public void setUp(){

    }
    public void tearDown(){}

    public boolean testWaitThreeSeconds(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
