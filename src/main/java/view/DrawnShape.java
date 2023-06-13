/*
 * TCSS 305 - Assignment 5
 */

package view;

import java.awt.*;

/**
 * Represents a shape drawn by a paint tool. 
 * Stores the shape (which contains its size and coordinates), as well as the shape's color
 * and stroke width.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class DrawnShape {

    /** The shape. */
    private final Shape myShape;

    /** The color of the shape. */
    private final Color myColor;

    /** The stroke width of the shape.*/
    private final int myStrokeWidth;
    
    /** The fill mode of the shape. */
    private final boolean myFillMode;
    
    /** The fill color of the shape. */
    private final Color myFillColor;
    
    

    /**
     * Initializes a new drawn shape which stores a shape, color, and a stroke width.
     * 
     * @param theShape The shape to store.
     * @param theColor The color to store.
     * @param theStrokeWidth The stroke width to store.
     */
    public DrawnShape(final Shape theShape, final Color theColor, 
            final int theStrokeWidth, final boolean theFillMode, final Color theFillColor) {
        
        myShape = theShape;
        myColor = theColor;
        myStrokeWidth = theStrokeWidth;
        myFillMode = theFillMode;
        myFillColor = theFillColor;
    }

    /**
     * Gets the shape.
     * 
     * @return The shape.
     */
    public Shape getShape() {
        return myShape;
    }

    /**
     * Gets the shape's color.
     * 
     * @return The shape's color.
     */
    public Color getColor() {
        return myColor;
    }

    /**
     * Gets the shape's stroke width.
     * 
     * @return The shape's stroke width.
     */
    public int getStrokeWidth() {
        return myStrokeWidth;
    }

    /**
     * Gets the shape's fill color.
     * 
     * @return The shape's fill color.
     */
    public Color getFillColor() {
        return myFillColor;
    }
    
    /**
     * Gets the fill mode.
     * 
     * @return Whether fill mode is activated.
     */
    public boolean hasFillMode() {
        return myFillMode;
    }

}
