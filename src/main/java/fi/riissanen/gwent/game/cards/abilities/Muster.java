package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.List;

/**
 * Muster ability.
 * @author Daniel
 */
public class Muster implements Ability, UnitAbility {

    private final UnitCard card;
    
    /**
     * Constructor.
     * @param card The ability owner
     */
    public Muster(UnitCard card) {
        this.card = card;
    }
    
    @Override
    public void activate(GameSystem system) {
        Player player = card.getOwner();
        GameBoard board = system.getBoard();
        int rowIndex = board.getRowIndex(board.getRow(getUnit()));
        List<Card> cards = player.getDeckCardsByName(getUnit().getName());
        for (Card c : cards) {
            if (c instanceof UnitCard) {
                player.removeCardFromDeck(c);
                system.getBoard().addUnit(
                        ((UnitCard) c).getUnit(),
                        UnitType.values()[rowIndex], true);
            }
            
            for (Ability ability : c.getAbilities()) {
                // Prevent recursion
                if (!(ability instanceof Muster)) {
                    ability.activate(system);
                }
            }
        }
        
    }
    
    @Override
    public Unit getUnit() {
        return card.getUnit();
    }

    @Override
    public String getName() {
        return "Muster";
    }

    @Override
    public String getDescription() {
        return "Play all cards with the same name in the deck";
    }

}
