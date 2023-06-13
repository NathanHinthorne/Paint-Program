/*
 * TCSS 305 - Assignment 5
 */

package model;

import javax.swing.*;
import java.awt.*;

/**
 * An interface that represents the shared bahaviors of a paint tool.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public interface PaintTool {
    
    /**
     * Gets the paint tool's active icon.
     * 
     * @return The active icon for a paint tool.
     */
    Icon getActiveIcon();
    
    /**
     * Gets the paint tool's inactive icon.
     * 
     * @return The inactive icon for a paint tool.
     */
    Icon getInactiveIcon();

    /**
     * Sets the initial point for the Shape drawn by this tool.
     * 
     * @param thePoint the start point to set
     */
    void setStartPoint(Point thePoint);

    /**
     * Starts a new shape at the given point.
     * 
     * @param theStartPoint The start point at which to start a new shape.
     */
    void startShape(Point theStartPoint);

    /**
     * Updates the shape to adjust to the given end point.
     * 
     * @param theEndPoint The endpoint to adjust to.
     */
    void updateShape(Point theEndPoint);

    /**
     * Ends the shape by adjusting to the given end point one final time.
     * 
     * @param theEndPoint The endpoint to adjust to.
     */
    void endShape(Point theEndPoint);

    /**
     * Returns the draw color of the shape from the paint tool.
     * 
     * @return The draw color of the shape from the paint tool.
     */
    Color getDrawColor();
    
    /**
     * Returns the fill color of the shape from the paint tool.
     * 
     * @return The fill color of the shape from the paint tool.
     */
    Color getFillColor();

    /**
     * Returns the stroke width of the shape from the paint tool.
     * 
     * @return The stroke width of the shape from the paint tool.
     */
    int getStrokeWidth();

    /**
     * Sets the color of the shape from the paint tool.
     * 
     * @param theColor The color of the shape from the paint tool.
     */
    void setDrawColor(Color theColor);
    
    /**
     * Sets the fill color of the shape from the paint tool.
     * 
     * @param theFillColor The fill color of the shape from the paint tool.
     */
    void setFillColor(Color theFillColor);

    /**
     * Sets the stroke width of the shape from the paint tool.
     * 
     * @param theStrokeWidth The stroke width of the shape from the paint tool.
     */
    void setStrokeWidth(int theStrokeWidth);

    /**
     * Returns the shape that this tools draws.
     * 
     * @return The shape to draw.
     */
    Shape getShape();

    /**
     * Sets the fill mode of the paint tool. 
     * 
     * @param theFillMode Whether or not the mode is fill.
     */
    void setFillMode(boolean theFillMode);

    /**
     * Checks if the mode is set to fill in the GUI.
     * 
     * @return The drawing mode of the GUI.
     */
    boolean hasFillMode();
}
