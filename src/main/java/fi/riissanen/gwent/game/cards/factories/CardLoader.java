package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.assets.AssetLoader;
import fi.riissanen.gwent.engine.assets.AssetParams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An {@link AssetLoader} that loads {@link CardData} from files.
 * @author Daniel
 */
public class CardLoader extends AssetLoader {

    private static final String NAME_KEY        = "Name";
    private static final String DESCRIPTION_KEY = "Description";
    private static final String TYPE_KEY        = "Type";
    private static final String ABILITIES_KEY   = "Abilities";
    
    private static final String STRENGTH_KEY    = "Strength";
    private static final String ATTRIBUTES_KEY  = "Attributes";
    private static final String UNIT_TYPE_KEY   = "UnitType";
    
    private static final List<String> TYPES = new ArrayList<>();
    private static final List<String> ABILITIES = new ArrayList<>();
    private static final List<String> ATTRIBUTES = new ArrayList<>();
    private static final List<String> UNIT_TYPES = new ArrayList<>();
    
    /**
     * Loads card validation data.
     */
    public CardLoader() {
        TYPES.add("Unit");
        TYPES.add("Weather");
        TYPES.add("Leader");
        TYPES.add("Special");
        
        ABILITIES.add("Medic");
        ABILITIES.add("Morale boost");
        ABILITIES.add("Muster");
        ABILITIES.add("Tight bond");
        ABILITIES.add("Frost");
        ABILITIES.add("Fog");
        ABILITIES.add("Rain");
        
        ATTRIBUTES.add("Agile");
        ATTRIBUTES.add("Hero");
        ATTRIBUTES.add("Spy");
        
        UNIT_TYPES.add("Melee");
        UNIT_TYPES.add("Ranged");
        UNIT_TYPES.add("Siege");
    }
    
    @Override
    public CardData load(AssetParams params) {
        if (!params.getFilename().endsWith(".card")) {
            throw new IllegalArgumentException("Card data file must be .card");
        }
        
        try {
            List<String> lines = Engine.INSTANCE.files.readLines(params.getFilename());
            Map<String, String> data = parseLines(lines);
            
            if (validateData(data)) {
                return loadCardData(data);
            } else {
                log = "Invalid card data file format";
            }
        } catch (IOException ex) {
            log = ex.getMessage();
        }
        return null;
    }
    
    private CardData loadCardData(Map<String, String> data) {
        CardData cardData = new CardData();
        cardData.name = data.get(NAME_KEY);
        cardData.description = data.get(DESCRIPTION_KEY);
        cardData.type = data.get(TYPE_KEY);
        cardData.abilities = data.get(ABILITIES_KEY);
        if (cardData.type.equals("Unit")) {
            cardData.strength = data.get(STRENGTH_KEY);
            cardData.attributes = data.get(ATTRIBUTES_KEY);
            cardData.unitType = data.get(UNIT_TYPE_KEY);
        }
        return cardData;
    }
    
    private Map<String, String> parseLines(List<String> lines) {
        Map<String, String> data = new HashMap<>();
        for (String line : lines) {
            String[] split = line.split("=", 2);
            // '#' Is a comment
            if (split.length == 2 && !split[0].startsWith("#")) {
                data.put(split[0], split[1]);
            }
        }
        return data;
    }
    
    private boolean validateData(Map<String, String> data) {
        if (!hasKeys(data)) {
            return false;
        }
        
        boolean typeValidated = validateType(data.get(TYPE_KEY));
        boolean abilitiesValidated = validateAbilities(data.get(ABILITIES_KEY));
        boolean unitValidated = !data.get(TYPE_KEY).equals("Unit") ||
                validateUnit(data.get(STRENGTH_KEY),
                        data.get(ATTRIBUTES_KEY),
                        data.get(UNIT_TYPE_KEY));
        
        return typeValidated && abilitiesValidated && unitValidated;
    }
    
    private boolean hasKeys(Map<String, String> data) {
        if (!(data.containsKey(NAME_KEY) &&
              data.containsKey(DESCRIPTION_KEY) &&
              data.containsKey(TYPE_KEY))) {
            return false;
        }
        
        if (data.get(TYPE_KEY).equals("Unit")) {
            if (!(data.containsKey(STRENGTH_KEY) &&
                  data.containsKey(ATTRIBUTES_KEY) &&
                  data.containsKey(UNIT_TYPE_KEY))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean validateType(String input) {
        for (String type : TYPES) {
            if (input.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean validateAbilities(String abilityStr) {
        if (abilityStr.equals("")) {
            return true;
        }
        
        String[] abilities = abilityStr.split(",");
        for (String ability : abilities) {
            if (!ABILITIES.contains(ability)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean validateUnit(String str, String attr, String type) {
        return validateStrength(str) &&
               validateAttributes(attr) &&
               validateUnitType(type);
    }
    
    private boolean validateStrength(String strength) {
        try {
            int str = Integer.parseInt(strength);
            return str >= 0 && str <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean validateAttributes(String attribStr) {
        if (attribStr.equals("")) {
            return true;
        }
        
        String[] attribs = attribStr.split(",");
        for (String attrib : attribs) {
            if (!ATTRIBUTES.contains(attrib)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean validateUnitType(String type) {
        return UNIT_TYPES.contains(type);
    }
}
