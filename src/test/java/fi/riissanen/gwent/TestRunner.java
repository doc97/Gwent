package fi.riissanen.gwent;

import fi.riissanen.gwent.game.TestGameSystem;
import fi.riissanen.gwent.game.cards.TestCardCollection;
import fi.riissanen.gwent.game.cards.TestDeck;
import fi.riissanen.gwent.game.cards.TestHand;
import fi.riissanen.gwent.game.combat.TestCombatRow;
import fi.riissanen.gwent.game.combat.TestGameBoard;
import fi.riissanen.gwent.game.combat.TestUnit;
import fi.riissanen.gwent.game.combat.TestUnitType;
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
            TestCardCollection.class,
            TestDeck.class,
            TestHand.class,
            TestUnitType.class,
            TestUnit.class,
            TestGameSystem.class
        };
        Result result = JUnitCore.runClasses(clazzes);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
        
        System.out.println("Success: " + result.wasSuccessful());
    }
}
