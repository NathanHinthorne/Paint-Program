/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Represents a line paint tool. Contains a line which can be edited.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class LineTool extends AbstractPaintTool {

    /** The shape to be drawn. */
    private Line2D myLine;

    /**
     * Instantiates a new line tool.
     */
    public LineTool() {
        super();
        
        myLine = new Line2D.Double(getStartPoint(), getEndPoint());
    }

    @Override
    public void startShape(final Point theStartPoint) {
        myLine = new Line2D.Double(theStartPoint, getEndPoint());
        
        setStartPoint(theStartPoint);
    }
    
    @Override
    public void updateShape(final Point theEndPoint) {
        
        myLine.setLine(getStartPoint(), theEndPoint);
    }
    
    @Override
    public void endShape(final Point theEndPoint) {
        myLine.setLine(getStartPoint(), theEndPoint);
    }
    
    @Override
    public Shape getShape() {
        return (Shape) myLine.clone();
    }
    
    // The line should never allow its drawings to be filled.
    @Override
    public boolean hasFillMode() {
        return false;
    }
    
    @Override
    public String toString() {
        return "Line";
    }

}
