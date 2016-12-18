package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.abilities.Medic;
import fi.riissanen.gwent.game.cards.abilities.MoraleBoost;
import fi.riissanen.gwent.game.cards.abilities.Muster;
import fi.riissanen.gwent.game.cards.abilities.TightBond;
import fi.riissanen.gwent.game.cards.attributes.Agile;
import fi.riissanen.gwent.game.cards.attributes.Attribute;
import fi.riissanen.gwent.game.cards.attributes.Hero;
import fi.riissanen.gwent.game.cards.attributes.Spy;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;

/**
 * A specialized {@link CardFactory} for creating {@link UnitCard}'s.
 *
 * @author Daniel
 */
public class UnitCardFactory implements CardFactory {

    @Override
    public UnitCard createCard(CardData data) {

        List<Attribute> attributes = extractAttributes(data.attributes);
        UnitType type = extractUnitType(data.unitType);

        int strength = Integer.parseInt(data.strength);
        Unit unit = new Unit(data.name, data.description);
        unit.setFriendlyStatus(true);
        unit.setBaseStrength(strength);
        unit.addAttributes(attributes);
        unit.setUnitType(type);
        unit.reloadAttributes();

        UnitCard card = new UnitCard(unit);
        unit.setCard(card);
        unit.addAbilities(extractAbilities(data.abilities, card));

        return card;
    }

    private List<Ability> extractAbilities(String inputStr, UnitCard card) {
        List<Ability> abilities = new ArrayList<>();
        String[] abilitiesStr = inputStr.split(",");
        for (String abilityStr : abilitiesStr) {
            switch (abilityStr) {
                case "Medic" :
                    abilities.add(new Medic(card));
                    break;
                case "Morale boost" :
                    abilities.add(new MoraleBoost(card));
                    break;
                case "Muster" :
                    abilities.add(new Muster(card));
                    break;
                case "Tight bond" :
                    abilities.add(new TightBond(card));
                    break;
            }
        }
        return abilities;
    }

    private List<Attribute> extractAttributes(String inputStr) {
        List<Attribute> attributes = new ArrayList<>();
        String[] attributesStr = inputStr.split(",");
        for (String attributeStr : attributesStr) {
            switch (attributeStr) {
                case "Agile":
                    attributes.add(new Agile() {
                    });
                    break;
                case "Hero":
                    attributes.add(new Hero());
                    break;
                case "Spy":
                    attributes.add(new Spy());
                    break;
            }
        }
        return attributes;
    }

    private UnitType extractUnitType(String type) {
        switch (type) {
            case "Melee":
                return UnitType.MELEE;
            case "Ranged":
                return UnitType.RANGED;
            case "Siege":
                return UnitType.SIEGE;
            default:
                return null;
        }
    }
}
