package fi.riissanen.gwent.engine.render.fonts;

import java.util.ArrayList;
import java.util.List;

/**
 * A line of text.
 * @author Daniel
 */
public class TextLine {

    private final List<TextWord> words = new ArrayList<>();
    private final double spaceSize;
    private final double maxLength;
    private double currLength;
    
    /**
     * Constructs a text line with attributes.
     * @param spaceWidth The width of a space with font size 1
     * @param fontSize The font size to use
     * @param maxLength The max length of a line
     */
    public TextLine(double spaceWidth, double fontSize, double maxLength) {
        this.spaceSize = spaceWidth * fontSize;
        this.maxLength = maxLength;
    }
    
    /**
     * Attempts to add a word to the line.
     * 
     * <p>
     * The word width must be smaller than what is left on the line from
     * the current line length to the max line length, or if max line length
     * is -1, then there is no limit.
     * @param word The word to add
     * @return True if the word fit on the line, false otherwise
     */
    public boolean addWord(TextWord word) {
        boolean noLimit = maxLength == -1;
        double additionalLength = word.getWidth();
        additionalLength += word.isEmpty() ? 0 : spaceSize;
        if (currLength + additionalLength <= maxLength || noLimit) {
            words.add(word);
            currLength += additionalLength;
            return true;
        }
        return false;
    }
    
    /**
     * Returns the words on the line.
     * @return The list of words
     */
    public List<TextWord> getWords() {
        return words;
    }
    
    /**
     * The max line length specified in the constructor.
     * @return The max line length
     */
    public double getMaxLineLength() {
        return maxLength;
    }
    
    /**
     * The current line length.
     * @return The line length
     */
    public double getCurrentLineLength() {
        return currLength;
    }
}
