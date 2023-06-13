/*
 * TCSS 305 - Assignment 5
 */

package view;

import model.PaintTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.List;

/**
 * An action that occurs when a paint tool button is pressed.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public class PaintToolAction extends AbstractAction {
    
    /** A generated serial number. */
    @Serial
    private static final long serialVersionUID = 2631302410736393514L;
    
    /** The list of actions correspoinding to each type of paint tool. */
    private List<PaintToolAction> myActionList;  

    /** The type of paint tool to use to draw a shape. */
    private final PaintTool myTool;
    
    /** The main JPanel acting as the canvas for the GUI. */
    private final DrawingPanel myPanel;
        
            
    /**
     * Initializes a new paint tool action that occurs when a paint tool button is pressed.
     * 
     * @param theTool The type of paint tool to use to draw a shape.
     * @param thePanel The main JPanel acting as the canvas for the GUI.
     */
    public PaintToolAction(final PaintTool theTool, final DrawingPanel thePanel) {
        
        super(theTool.toString());
        
        myTool = theTool;
        myPanel = thePanel;
        
        putValue(Action.SELECTED_KEY, true);
        putValue(Action.SMALL_ICON, myTool.getInactiveIcon());
    }
    
    /**
     * Sets the GUI's current tool to be the tool passed to this action.
     * Determines what Icon to use.
     * 
     * @param theEvent The event that occurs once an action has been performed.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {

        myPanel.setCurrentTool(myTool);
        
        // give the selected button an active icon
        this.putValue(Action.SMALL_ICON, myTool.getActiveIcon());
        
        myActionList.remove(this);
        
        // give the remainder of the buttons (the unselected ones) inactives icons.
        for (final PaintToolAction a : myActionList) {
            a.putValue(Action.SMALL_ICON, a.getInactiveIcon());
        }
        
        myActionList.add(this);
    } 
    
    /**
     * Sets ACTION_LIST to the given list of actions.
     * 
     * @param theActionsList The list of possible actions corresponding to each paint tool.
     */
    public void setActionsList(final List<PaintToolAction> theActionsList) {
        myActionList = theActionsList;
    }
    
    /**
     * Gets the inactive icon for this action.
     * 
     * @return The inactive icon for this action.
     */
    public Icon getInactiveIcon() {
        return myTool.getInactiveIcon();
    }

    @Override
    public String toString() {
        return myTool.toString() + "Action";
    }
    
}
