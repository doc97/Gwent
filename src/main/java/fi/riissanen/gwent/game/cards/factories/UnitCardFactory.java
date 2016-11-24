package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.attributes.Agile;
import fi.riissanen.gwent.game.cards.attributes.Attribute;
import fi.riissanen.gwent.game.cards.attributes.Hero;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class UnitCardFactory implements CardFactory {

    @Override
    public UnitCard createCard(CardData data) {
        List<Ability> abilities = extractAbilities(data.abilities);
        List<Attribute> attributes = extractAttributes(data.attributes);
        UnitType type = extractUnitType(data.unitType);
        
        int strength = Integer.parseInt(data.strength);
        Unit unit = new Unit(data.name, data.description);
        unit.setFriendlyStatus(true);
        unit.addAbilities(abilities);
        unit.setBaseStrength(strength);
        unit.addAttributes(attributes);
        unit.setUnitType(type);
        unit.reload();
        return new UnitCard(unit);
    }
    
    private List<Ability> extractAbilities(String inputStr) {
        List<Ability> abilities = new ArrayList<>();
        String[] abilitiesStr = inputStr.split(",");
        for (String abilityStr : abilitiesStr) {
            switch (abilityStr) {
                case "Medic" :
                    abilities.add(new Ability() {});
                    break;
                case "Morale boost" :
                    abilities.add(new Ability() {});
                    break;
                case "Muster" :
                    abilities.add(new Ability() {});
                    break;
                case "Tight bond" :
                    abilities.add(new Ability() {});
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
                case "Agile" :
                    attributes.add(new Agile() {});
                    break;
                case "Hero" :
                    attributes.add(new Hero());
                    break;
                case "Spy" :
                    // TODO Add spy
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
