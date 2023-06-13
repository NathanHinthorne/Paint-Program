/*
 * TCSS 305 - Assignment 5
 */

package model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

/**
 * Represents a pencil paint tool. Contains a pencil which can be edited.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class PencilTool extends AbstractPaintTool {

    /** The path the pencil makes when the user draws with it. */
    private Path2D myPath;


    /**
     * Instantiates a new pencil tool.
     */
    public PencilTool() {
        super();
        
        myPath = new Path2D.Double();
        myPath.setWindingRule(GeneralPath.WIND_EVEN_ODD);
    }

    @Override
    public void startShape(final Point theStartPoint) {
        myPath = new Path2D.Double();
        
        setStartPoint(theStartPoint);
        myPath.moveTo(theStartPoint.getX(), theStartPoint.getY());
    }
    
    @Override
    public void updateShape(final Point theEndPoint) {
        myPath.lineTo(theEndPoint.getX(), theEndPoint.getY());
    }
    
    @Override
    public void endShape(final Point theEndPoint) {
        myPath.lineTo(theEndPoint.getX(), theEndPoint.getY());
    }
    
    @Override
    public Shape getShape() {
        return (Shape) myPath.clone();
    }
    
    // The pencil should never allow its drawings to be filled.
    @Override
    public boolean hasFillMode() {
        return false;
    }
    
    @Override
    public String toString() {
        return "Pencil";
    }

}
