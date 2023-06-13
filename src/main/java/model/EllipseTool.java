/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents an ellipse paint tool. Contains an ellipse which can be edited.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class EllipseTool extends AbstractPaintTool {

    /** The shape to be drawn. */
    private Ellipse2D myEllipse;

    /**
     * Instantiates a new ellipse tool.
     */
    public EllipseTool() {
        super();
        
        myEllipse = new Ellipse2D.Double(getStartPoint().getX(), getStartPoint().getY(),
                getEndPoint().getX(), getEndPoint().getY());
    }

    @Override
    public void startShape(final Point theStartPoint) {
        myEllipse = new Ellipse2D.Double(getStartPoint().getX(), getStartPoint().getY(),
                getEndPoint().getX(), getEndPoint().getY());
        
        setStartPoint(theStartPoint);
    }
    
    @Override
    public void updateShape(final Point theEndPoint) {
        myEllipse.setFrameFromDiagonal(getStartPoint(), theEndPoint);
    }
    
    @Override
    public void endShape(final Point theEndPoint) {
        myEllipse.setFrameFromDiagonal(getStartPoint(), theEndPoint);
    }
    
    @Override
    public Shape getShape() {
        return (Shape) myEllipse.clone();
    }
    
    @Override
    public String toString() {
        return "Ellipse";
    }

}
