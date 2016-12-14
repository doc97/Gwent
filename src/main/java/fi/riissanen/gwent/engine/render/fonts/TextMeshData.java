package fi.riissanen.gwent.engine.render.fonts;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains data about a {@code Text}'s mesh.
 * @author Daniel
 */
public class TextMeshData {
    
    private final List<TextLine> lines = new ArrayList<>();
    private final List<Float> quadData = new ArrayList<>();
    private final List<Float> uvs = new ArrayList<>();
    
    private float[] quadDataArr;
    private float[] uvArr;
    
    /**
     * Creates the mesh data.
     * @param text The text to create the data of
     */
    public void create(Text text) {
        clear();
        calculateLineStructure(text);
        calculateMesh(text, lines);
        storeToArrays();
    }
    
    private void clear() {
        lines.clear();
        quadData.clear();
        uvs.clear();
        quadDataArr = new float[0];
        uvArr = new float[0];
    }
    private void calculateLineStructure(Text text) {
        char[] chars = text.getText().toCharArray();
        Font font = text.getFont();
        TextLine currLine = new TextLine(
                font.getSpaceWidth(),
                text.getFontSize(),
                text.getMaxLineLength());
        TextWord currWord = new TextWord(text.getFontSize());
        for (char c : chars) {
            int ascii = (int) c;
            if (ascii == Font.ASCII_SPACE) {
                addWordToLine(lines, currLine, currWord, text);
                currWord = new TextWord(text.getFontSize());
                continue;
            }
            Glyph character = text.getFont().getGlyph(ascii);
            currWord.addCharacter(character);
        }
        addWordToLine(lines, currLine, currWord, text);
        lines.add(currLine);
    }
    
    private void addWordToLine(List<TextLine> lines, TextLine currLine, TextWord currWord, Text text) {
        boolean added = currLine.addWord(currWord);
        // Create new line
        if (!added) {
            lines.add(currLine);
            currLine = new TextLine(
                    text.getFont().getSpaceWidth(),
                    text.getFontSize(),
                    text.getMaxLineLength());
            currLine.addWord(currWord);
        }
    }
    
    private void calculateMesh(Text text, List<TextLine> lines) {
        text.setNumberOfLines(lines.size());
        double lastCursorX = 0;
        double cursorX = 0;
        double cursorY = 0;
        for (TextLine line : lines) {
            for (TextWord word : line.getWords()) {
                for (Glyph glyph : word.getCharacters()) {
                    addGlyphVertices(cursorX, cursorY, glyph,
                            text.getFontSize(), text.getFont().getLineHeight());
                    addGlyphUVs(glyph.getU1(), glyph.getV1(), glyph.getU2(), glyph.getV2());
                    cursorX += glyph.getXAdvance() * text.getFontSize();
                }
                cursorX += text.getFont().getSpaceWidth() * text.getFontSize();
            }
            lastCursorX = cursorX;
            cursorX = 0;
            cursorY -= text.getFont().getLineHeight() * text.getFontSize();
        }
        
        if (text.getNumberOfLines() > 1) {
            text.setLineLength(text.getMaxLineLength());
        } else if (text.getNumberOfLines() == 1) {
            text.setLineLength((float) lastCursorX);
        }
    }
    
    private void addGlyphVertices(double cursorX, double cursorY, Glyph glyph,
            double fontSize, double lineHeight) {
        double width = glyph.getWidth() * fontSize;
        double height = glyph.getHeight() * fontSize;
        double x = cursorX + glyph.getXOffset() * fontSize;
        double y = lineHeight * fontSize - cursorY - glyph.getYOffset() * fontSize - height;
        addQuadData(x, y, width, height);
    }
    
    private void addQuadData(double x, double y, double width, double height) {
        quadData.add((float) x);
        quadData.add((float) y);
        quadData.add((float) width);
        quadData.add((float) height);
    }
    
    private void addGlyphUVs(double u1, double v1, double u2, double v2) {
        uvs.add((float) u1);
        uvs.add((float) v1);
        uvs.add((float) u2);
        uvs.add((float) v2);
    }
    
    private void storeToArrays() {
        quadDataArr = floatListToArray(quadData);
        uvArr = floatListToArray(uvs);
    }
    
    private float[] floatListToArray(List<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
    
    public float[] getQuadData() {
        return quadDataArr;
    }
    
    public float[] getUVs() {
        return uvArr;
    }
    
    public int getVertexCount() {
        return quadDataArr.length;
    }
    
    public int getGlyphCount() {
        return getVertexCount() / 4;
    }
}
