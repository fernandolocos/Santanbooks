package pt.c03ensaios.naxxramas;

import java.awt.Color;

/**
 * Represents a slice in a pie chart.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class Slice {
    private int percentage, value;
    private Color color;
    private String label;
    
    public Slice(int percentage, int value, Color color, String label) {
        this.percentage = percentage;
        this.value = value;
        this.color = color;
        this.label = label;
    }
    
    public int getPercentage() {
        return this.percentage;
    }

    public int getValue() {
        return this.value;
    }

    public Color getColor() {
        return this.color;
    }
    
    public String getLabel() {
        return this.label;
    }
}