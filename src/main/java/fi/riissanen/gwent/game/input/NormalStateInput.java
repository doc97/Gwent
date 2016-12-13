package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.ui.GUICard;
import fi.riissanen.gwent.game.ui.GUIComponent;

/**
 *
 * @author Daniel
 */
public class NormalStateInput extends GUIInputAdapter {

    private final Gwent game;
    
    public NormalStateInput(Gwent game) {
        this.game = game;
    }
    
    @Override
    public void releaseComponent(GUIComponent component, int button) {
        if (component instanceof GUICard) {
            Card card = ((GUICard) component).getCard();
            game.getGameSystem().stageCard(card);
        }
    }
}
