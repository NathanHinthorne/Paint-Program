/*
 * TCSS 305 - Assignment 5
 */

package view;

import model.PaintTool;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * Listens for mouse interactions which causes drawings on the GUI panel.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class MyMouseListener extends MouseAdapter {
    
    /** The type of paint tool to use to draw a shape. */
    private final PaintTool myPaintTool;
    
    /** The main JPanel acting as the canvas for the GUI. */
    private final DrawingPanel myPanel;
    
    /** The previously drawn shapes. */
    private final List<DrawnShape> myDrawnShapes;
    
    
    /**
     * Initializes a new mouse listener that defines a paint canvas, a paint tool, 
     * and the drawn shapes.
     * 
     * @param thePaintTool The type of paint tool to use to draw a shape.
     * @param thePanel The main JPanel acting as the canvas for the GUI.
     * @param theDrawnShapes The previously drawn shapes.
     */
    public MyMouseListener(final PaintTool thePaintTool, final DrawingPanel thePanel,
            final List<DrawnShape> theDrawnShapes) {
        
        super();
        
        myPanel = thePanel;
        myPaintTool = thePaintTool;
        myDrawnShapes = theDrawnShapes;
    }
    
    
    /**
     * Sets up a new shape when the mouse is pressed.
     * 
     * @param theEvent The event that occurs from the mouse interaction.
     */
    @Override
    public void mousePressed(final MouseEvent theEvent) {
        
        myPanel.setShapeBeingDrawn(true);
        
        final Point startPoint = new Point(theEvent.getX(), theEvent.getY());
        myPaintTool.startShape(startPoint);
    }
    
    /**
     * Updates the shape when the mouse is pressed.
     * 
     * @param theEvent The event that occurs from the mouse interaction.
     */
    @Override
    public void mouseDragged(final MouseEvent theEvent) {
        
        final Point endPoint = new Point(theEvent.getX(), theEvent.getY());
        myPaintTool.updateShape(endPoint);
        
        myPanel.repaint();
    }
    
    /**
     * Finishes the shape when the mouse is pressed.
     * 
     * @param theEvent The event that occurs from the mouse interaction.
     */
    @Override
    public void mouseReleased(final MouseEvent theEvent) {
        
        final Point endPoint = new Point(theEvent.getX(), theEvent.getY());
        myPaintTool.endShape(endPoint);
        
        // add the shape to the list of onscreen shapes
        final DrawnShape shapeDetails = new DrawnShape(myPaintTool.getShape(),
                myPaintTool.getDrawColor(), myPaintTool.getStrokeWidth(), 
                myPaintTool.hasFillMode(), myPaintTool.getFillColor());
        
        myDrawnShapes.add(shapeDetails);
        
        myPanel.setShapeBeingDrawn(false); //needed?
        
        myPanel.repaint();              
    }
}
