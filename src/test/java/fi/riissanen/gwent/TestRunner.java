package fi.riissanen.gwent;

import fi.riissanen.gwent.game.GameBoardTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;



/**
 * Runs the JUnit tests
 * @author Daniel
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(GameBoardTest.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
        
        System.out.println("Success: " + result.wasSuccessful());
    }
}
