import Controller.Controller;

/**
 * Program that runs all test methods(specific structure) in
 * a given class and displays the result of each test method in a textArea.
 * The given class must also follow a specific structure.
 *
 * Specifiations of class:
 * Should implement "Tests.TestClass.class"
 * No parameters in constructor
 * Could have a setUp method(will be called before each test).
 * Could have a tearDown method(will be called at end of all test).
 *
 * Specifications of testMethod:
 * each test method should start with "test",
 * have no parameters and return boolean.
 *
 * @Author Thomas Sarlin - id15tsn, thomas.sarlin@gmail.com
 */

public class MyUnitTester {
    public static void main(String args[]){
        new Controller();
    }
}
