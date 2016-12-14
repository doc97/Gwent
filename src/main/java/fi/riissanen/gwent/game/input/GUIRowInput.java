package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.abilities.ClearWeather;
import fi.riissanen.gwent.game.cards.abilities.Fog;
import fi.riissanen.gwent.game.cards.abilities.Frost;
import fi.riissanen.gwent.game.cards.abilities.Rain;
import fi.riissanen.gwent.game.cards.neutral.WeatherCard;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.ui.GUIComponent;
import fi.riissanen.gwent.game.ui.GUIRow;

/**
 * GUI input for {@link GUIRowInput}.
 * @author Daniel
 */
public class GUIRowInput extends GUIInputAdapter {

    private final Gwent game;
    
    /**
     * Constructor.
     * @param game The instance of the game
     */
    public GUIRowInput(Gwent game) {
        this.game = game;
    }
    
    /**
     * Adds itself to listening for appropriate rows.
     * @param guiInput The GUI input
     * @param rows The rows to select from which to listen to
     * @param card The card with which the selection is done
     */
    public void load(GUIInput guiInput, GUIRow[] rows, Card card) {
        guiInput.removeListenerFromAll(this);
        if (card instanceof UnitCard) {
            int[] indices = ((UnitCard) card).getUnit().getTypeIndices();
            for (int i = 0; i < indices.length; i++) {
                guiInput.addListener(rows[indices[i]], this);
            }
        } else if (card instanceof WeatherCard) {
            Ability ability = ((WeatherCard) card).getAbilities().get(0);
            
            if (ability instanceof Frost) {
                guiInput.addListener(rows[UnitType.MELEE.getIndex()], this);
            } else if (ability instanceof Fog) {
                guiInput.addListener(rows[UnitType.RANGED.getIndex()], this);
            } else if (ability instanceof Rain) {
                guiInput.addListener(rows[UnitType.SIEGE.getIndex()], this);
            } else if (ability instanceof ClearWeather) {
                for (int i = 0; i < UnitType.values().length; i++) {
                    guiInput.addListener(rows[i], this);
                }
            }
        }
    }
    
    @Override
    public void releaseComponent(GUIComponent component, int button) {
        if (component instanceof GUIRow) {
            CombatRow row = ((GUIRow) component).getRow();
            int index = game.getGameSystem().getBoard().getRowIndex(row);
            game.getGameSystem().playCard(index);
        }
    }
}
