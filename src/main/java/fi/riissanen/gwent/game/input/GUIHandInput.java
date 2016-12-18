package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.ui.GUICard;
import fi.riissanen.gwent.game.ui.GUIComponent;

/**
 * GUI input for {@link GUIHandInput}.
 * @author Daniel
 */
public class GUIHandInput extends GUIInputAdapter {

    private final Gwent game;
    
    /**
     * Constructor.
     * @param game The game instance
     */
    public GUIHandInput(Gwent game) {
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
