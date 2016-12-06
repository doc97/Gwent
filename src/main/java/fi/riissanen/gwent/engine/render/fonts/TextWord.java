package fi.riissanen.gwent.engine.render.fonts;

import java.util.ArrayList;
import java.util.List;

/**
 * A word of a text.
 * @author Daniel
 */
public class TextWord {

    private final List<Glyph> characters = new ArrayList<>();
    private final double fontSize;
    private double width;
    
    /**
     * Creates a word with a certain font size
     * @param fontSize The font size
     */
    public TextWord(double fontSize) {
        this.fontSize = fontSize;
    }
    
    /**
     * Adds a character to the word.
     * @param glyph 
     */
    public void addCharacter(Glyph glyph) {
        characters.add(glyph);
        width += glyph.getXAdvance() * fontSize;
    }
    
    /**
     * Returns the characters of this word.
     * @return The list of glyphs
     */
    public List<Glyph> getCharacters() {
        return characters;
    }
    
    /**
     * Returns the width of the word.
     * @return The width
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Returns whether the word has any characters in it.
     * @return True if no words have been added
     */
    public boolean isEmpty() {
        return characters.isEmpty();
    }
}
