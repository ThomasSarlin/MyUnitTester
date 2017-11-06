package tests;

import main.Controller;
import org.junit.Test;

public class ControllerTest {

    @Test
    public void shouldBeAbleToCreate(){
        new Controller();
    }

    @Test
    public void shouldHandleActionListener(){

    }
    @Test (expected = IllegalArgumentException.class)
    public void shouldDetectIncorrectClassName(){
        throw new IllegalArgumentException();
    }
    @Test
    public void shouldDetectCorrectClassName(){

    }
}
