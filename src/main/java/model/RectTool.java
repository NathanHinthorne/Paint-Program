/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Represents a rectangle paint tool. Contains a rectangle which can be edited.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class RectTool extends AbstractPaintTool {

    /** The shape to be drawn. */
    private Rectangle2D myRect;
    

    /**
     * Instantiates a new rectangle tool.
     */
    public RectTool() {
        super();
        
        myRect = new Rectangle2D.Double(getStartPoint().getX(), getStartPoint().getY(),
                getEndPoint().getX(), getEndPoint().getY());
    }

    @Override
    public void startShape(final Point theStartPoint) {
        myRect = new Rectangle2D.Double(getStartPoint().getX(), getStartPoint().getY(),
                getEndPoint().getX(), getEndPoint().getY());
        
        setStartPoint(theStartPoint);
    }
    
    @Override
    public void updateShape(final Point theEndPoint) {
        myRect.setFrameFromDiagonal(getStartPoint(), theEndPoint);
    }
    
    @Override
    public void endShape(final Point theEndPoint) {
        myRect.setFrameFromDiagonal(getStartPoint(), theEndPoint);
    }
    
    @Override
    public Shape getShape() {
        return (Shape) myRect.clone();
    }
    
    @Override
    public String toString() {
        return "Rectangle";
    }

}
