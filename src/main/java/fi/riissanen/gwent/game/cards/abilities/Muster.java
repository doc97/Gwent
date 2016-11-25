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
 *
 * @author Daniel
 */
public class Muster implements Ability, UnitAbility {

    private final Card card;
    
    public Muster(Card card) {
        this.card = card;
    }
    
    @Override
    public void activate(GameSystem system) {
        Player player = card.getOwner();
        GameBoard board = system.getBoard();
        int rowIndex = board.getRowIndex(board.getRow(getUnit()));
        List<Card> cards = player.getDeckCardsByName(getUnit().getName());
        for (Card card : cards) {
            if (card instanceof UnitCard) {
                player.removeCardFromDeck(card);
                system.getBoard().addUnit(
                        ((UnitCard) card).getUnit(),
                        UnitType.values()[rowIndex], true);
            }
            
            for (Ability ability : card.getAbilities()) {
                // Prevent recursion
                if (!(ability instanceof Muster)) {
                    ability.activate(system);
                }
            }
        }
        
    }
    
    @Override
    public Unit getUnit() {
        if (card instanceof UnitCard) {
            return ((UnitCard) card).getUnit();
        }
        return null;
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
