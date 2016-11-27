package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.engine.assets.Asset;

/**
 * A container for raw card data, used for card creation
 * @author Daniel
 */
public class CardData implements Asset {
    public String name = "";
    public String description = "";
    public String type = "";
    public String abilities = "";
    public String strength = "";
    public String attributes = "";
    public String unitType = "";
}
