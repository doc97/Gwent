package fi.riissanen.gwent;

import fi.riissanen.gwent.game.TestCardCollection;
import fi.riissanen.gwent.game.TestCombatRow;
import fi.riissanen.gwent.game.TestGameBoard;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;



/**
 * Runs the JUnit tests
 * @author Daniel
 */
public class TestRunner {
    public static void main(String[] args) {
        Class<?>[] clazzes = new Class<?>[] {
            TestGameBoard.class,
            TestCombatRow.class,
            TestCardCollection.class
        };
        Result result = JUnitCore.runClasses(clazzes);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
        
        System.out.println("Success: " + result.wasSuccessful());
    }
}
