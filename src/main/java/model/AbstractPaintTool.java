/*
 * TCSS 305 - Assignment 5
 */

package model;

import view.DrawingPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * An abstract class that represents the default behavior of a piant tool.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public abstract class AbstractPaintTool implements PaintTool {
    
    /** A point outside the drawing area. */
    protected static final Point NO_POINT = new Point(-50, -50);
    
    /** A US locale. */
    private static final Locale US = new Locale("en", "US");

//    private static final Logger logger = Logger.getLogger(DrawingPanel.class.getName());
    
    /** The icon for a selected paint tool button. */
    private Icon myActiveIcon;
    
    /** The icon for an unselected paint tool button. */
    private Icon myInactiveIcon;
    
    /** The initial anchor point for the shape drawn by this tool. */
    private Point myStartPoint; 
    
    /** The end point for the shape drawn by this tool. */
    private Point myEndPoint; 
    
    /** The color of the shape in the paint tool. */
    private Color myColor;
    
    /** The fill color of the shape in the paint tool. */
    private Color myFillColor;
    
    /** The stroke width of the shape in the paint tool. */
    private int myStrokeWidth;
    
    /** Whether or not the fill mode is set to true. */
    private boolean myFillMode;

    
    /**
     * Defines an abstract paint tool and sets the start and end points to negative 
     * coordinates not displayed on the GUI.
     */
    public AbstractPaintTool() {
        
        myStartPoint = NO_POINT;
        myEndPoint = NO_POINT;
        
        findActiveIcon();
        findInactiveIcon();
    }
    
    @Override
    public void setStartPoint(final Point thePoint) {      
        myStartPoint = (Point) thePoint.clone();
        myEndPoint = (Point) thePoint.clone();
    }

    /**
     * Gets the start point of the shape.
     * 
     * @return the start point for this PaintTool.
     */
    protected Point getStartPoint() {
        return myStartPoint;
    }

    /**
     * Gets the end point of the shape.
     * 
     * @return the end point for this PaintTool.
     */
    protected Point getEndPoint() {
        return myEndPoint;
    }
    
    @Override
    public void setDrawColor(final Color theColor) {
        myColor = theColor;
    }
    
    @Override
    public void setStrokeWidth(final int theStrokeWidth) {
        myStrokeWidth = theStrokeWidth;
    }
    
    @Override
    public void setFillColor(final Color theFillColor) {
        myFillColor = theFillColor;
    }
    
    @Override
    public void setFillMode(final boolean theFillMode) {
        myFillMode = theFillMode;
    }
    
    @Override
    public Color getDrawColor() {
        return myColor;
    }

    @Override
    public int getStrokeWidth() {
        return myStrokeWidth;
    }
    
    @Override
    public Color getFillColor() {
        return myFillColor;
    }
    
    @Override
    public boolean hasFillMode() {
        return myFillMode;
    }

    /**
     * Finds and assigns the active paint tool icon to myActiveIcon.
     */
    private void findActiveIcon() {
        
        final String activeIconFileName = "/files/"
                + this.toString().toLowerCase(US) + ".gif";
        
        try {
//            myActiveIcon = new ImageIcon(getClass().getClassLoader().getResource(activeIconFileName));
            myActiveIcon = new ImageIcon(getClass().getResource(activeIconFileName));
        } catch (final NullPointerException e) {
            System.out.println("Could not read active icon... " + e);
        }
    }
    
    /**
     * Finds and assigns the inactive paint tool icon to myActiveIcon.
     */
    private void findInactiveIcon() {
        
        final String inactiveIconFileName = "/files/"
                + this.toString().toLowerCase(US) + "_bw.gif";
        
        try {
//            myInactiveIcon = new ImageIcon(getClass().getClassLoader().getResource(inactiveIconFileName));
            myInactiveIcon = new ImageIcon(getClass().getResource(inactiveIconFileName));
        } catch (final NullPointerException e) {
            System.out.println("Could not read inactive icon... " + e);
        }
    }

    @Override
    public Icon getActiveIcon() {
        return myActiveIcon;
    }
    
    @Override
    public Icon getInactiveIcon() {
        return myInactiveIcon;
    }
    
}
