/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.riissanen.gwent.engine.render;

/**
 * Represents RGBA color
 * @author Daniel
 */
public class Color {
    public static final Color WHITE = new Color(1, 1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0, 1);
    public static final Color RED = new Color (1, 0, 0, 1);
    public static final Color GREEN = new Color(0, 1, 0, 1);
    public static final Color BLUE = new Color(0, 0, 1, 1);
    public static final Color YELLOW = new Color(1, 1, 0, 1);
    public static final Color MAGENTA = new Color(1, 0, 1, 1);
    public static final Color CYAN = new Color(0, 1, 1, 1);
    public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1);
    
    private float r, g, b, a;
    
    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public Color cpy() {
        return new Color(r, g, b, a);
    }
    
    public void set(Color c) {
        set(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }
    
    public void set(float r, float g, float b, float a) {
        this.r = Math.min(Math.max(r, 0), 1);
        this.g = Math.min(Math.max(g, 0), 1);
        this.b = Math.min(Math.max(b, 0), 1);
        this.a = Math.min(Math.max(a, 0), 1);
    }
    
    public void add(float r, float g, float b, float a) {
        this.r = Math.min(Math.max(this.r + r, 0), 1);
        this.g = Math.min(Math.max(this.g + g, 0), 1);
        this.b = Math.min(Math.max(this.b + b, 0), 1);
        this.a = Math.min(Math.max(this.a + a, 0), 1);
    }
    
    public void multiply(float r, float g, float b, float a) {
        this.r = Math.min(Math.max(this.r * r, 0), 1);
        this.g = Math.min(Math.max(this.g * g, 0), 1);
        this.b = Math.min(Math.max(this.b * b, 0), 1);
        this.a = Math.min(Math.max(this.a * a, 0), 1);
    }
    
    public float getRed() {
        return r;
    }
    
    public float getGreen() {
        return g;
    }
    
    public float getBlue() {
        return b;
    }
    
    public float getAlpha() {
        return a;
    }
}
