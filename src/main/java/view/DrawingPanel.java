/*
 * TCSS 305 - Assignment 5
 */

package view;

import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;



/**
 * Presents the GUI for the PowerPaint application.
 * 
 * @author Nathan Hinthorne
 * @version 12/9/22
 */
public final class DrawingPanel extends JPanel {

    /** A generated serial number. */
    @Serial
    private static final long serialVersionUID = 8843247714969249211L;

    /** Java toolkit to allow the class to read the user's screen size. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The user's screen size. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** The user's screen width. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /** The user's screen height. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
    
    /** The scale at which to shrink the JFrame. */
    private static final int SCALE = 3;
    
    /** The default color of purple. */
    private static final Color UW_PURPLE = new Color(51, 0, 111);
    
    /** The default color of purple. */
    private static final Color UW_GOLD = new Color(232, 211, 162);

    /** The picture to display on the JFrame and help button. */
    private static final String PROFILE_PICTURE_PATH = "/files/Profile_Picture.png";


    /** The default stroke thickness of the shape to draw. */
    private static final int DEFAULT_THICKNESS = 3;

    /** The minor tick spacing for the stroke thickness slider. */
    private static final int MINOR_TICK_SPACING = 1;

    /** The major tick spacing for the stroke thickness slider. */
    private static final int MAJOR_TICK_SPACING = 5;


    /** Listens for changes from the mouse. */
    private MyMouseListener myMouseListener;

    /** The frame in which to display the GUI. */
    private final JFrame myFrame;
    
    /** The toolbar which holds the paint tool buttons. */
    private final JToolBar myToolBar;
    
    /** The menu which allows the user to select various settings.  */
    private final JMenuBar myMenuBar;
    
    /** The current color of the shape to draw. */
    private Color myDrawColor;
    
    /** The current color of the shape to draw. */
    private Color myFillColor;
    
    /** The current stroke thickness of the shape to draw. */
    private int myThickness;
    
    /** The previously drawn shapes. */
    private final List<DrawnShape> myDrawnShapes;
    
    /** The currently selected paint tool. */
    private PaintTool myCurrentTool;
    
    /** The types of actions to attach to the paintTools. */
    private List<PaintToolAction> myAvailableActions;
    
    /** The image containing PROFILE_PICTURE. */
    private final Image myProfileImage;
    
    /** The button used to clear shapes on the screen. */
    private JMenuItem myClearButton;
    
    /** Whether or not a shape is being drawn. */
    private boolean myShapeIsBeingDrawn;
    
    /** Whether or not shapes get filled when they are drawn. */
    private boolean myFillMode;
    
    /** The button used to set the fill mode. */
    private JCheckBoxMenuItem myFillCheckbox;
    

    /**
     * Instantiates a new JPanel and sets up the GUI.
     */
    public DrawingPanel() {
        super();

        // initialize instance fields
        myFrame = new JFrame("TCSS 305 Paint – Autumn 2022");
        myToolBar = new JToolBar();
        myMenuBar = new JMenuBar();
        myProfileImage = findProfilePictureImage();
        
        myDrawColor = UW_PURPLE;
        myFillColor = UW_GOLD;
        myThickness = DEFAULT_THICKNESS;
        myDrawnShapes = new ArrayList<DrawnShape>();

        // setup and display the GUI
        start();
    }
    
    /**
     * Performs all tasks necessary to display the UI.
     */
    private void start() {

        // call paint tool methods
        setupPaintTools();
        
        myMouseListener = new MyMouseListener(myCurrentTool, this, myDrawnShapes);
        
        // call panel methods
        this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        this.setBackground(Color.WHITE);
        this.addMouseListener(myMouseListener);
        this.addMouseMotionListener(myMouseListener);
        this.setOpaque(true);
        
        // call menu methods
        setupMenu();

        // call toolbar methods
        setupToolBar();

        // final frame setup
        myFrame.setJMenuBar(myMenuBar);
        myFrame.add(myToolBar, BorderLayout.SOUTH);
        myFrame.add(this, BorderLayout.CENTER);
        
        myFrame.setSize(SCREEN_WIDTH / SCALE, SCREEN_HEIGHT / SCALE);
        myFrame.setLocation(SCREEN_WIDTH / SCALE - getWidth() / SCALE,
                SCREEN_HEIGHT / SCALE - getHeight() / SCALE);
        myFrame.setIconImage(myProfileImage);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

    /**
     * Does all the drawing necessary for the shapes.
     * 
     * @param theGraphics Allow an application to draw onto components that are realized on
     * various devices, as well as onto off-screen images.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        //setup
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
            
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // give the tool parameters to pass to DrawnShape
        myCurrentTool.setDrawColor(myDrawColor);
        myCurrentTool.setStrokeWidth(myThickness); 
        myCurrentTool.setFillColor(myFillColor);
        myCurrentTool.setFillMode(myFillMode);
        
        // decide whether the "clear" button should be enabled
        if (myDrawnShapes.isEmpty()) {
            myClearButton.setEnabled(false);
        } else {
            myClearButton.setEnabled(true);
        }
        
        // decide whether the "fill" button should be enabled
        if (myCurrentTool.toString().equals("Rectangle") 
                || myCurrentTool.toString().equals("Ellipse")) {
            
            myFillCheckbox.setEnabled(true);
        } else {
            myFillCheckbox.setSelected(false);
            myFillCheckbox.setEnabled(false);
        }
        
        // draw previous shapes
        for (final DrawnShape shape : myDrawnShapes) {
            
            if (shape.getStrokeWidth() != 0) {
                
                if (shape.hasFillMode()) {
                    g2d.setPaint(shape.getFillColor());
                    g2d.fill(shape.getShape());
                }
                
                g2d.setPaint(shape.getColor());
                g2d.setStroke(new BasicStroke(shape.getStrokeWidth()));
                g2d.draw(shape.getShape());
            }
        }
        
        // draw current shape
        if (myShapeIsBeingDrawn && myThickness != 0) {
            
            if (myCurrentTool.hasFillMode()) {
                g2d.setPaint(myFillColor);
                g2d.fill(myCurrentTool.getShape());
            }
            
            g2d.setPaint(myDrawColor);
            g2d.setStroke(new BasicStroke(myThickness));
            g2d.draw(myCurrentTool.getShape());
        }            
    }
    
    /**
     * Finds the image for the picture file given.
     *
     * @return The image for the picture file.
     */
    private Image findProfilePictureImage() {
        
        Image img = null;
        
        try (InputStream inputStream = getClass().getResourceAsStream(PROFILE_PICTURE_PATH)) {
            if (inputStream != null) {
                img = ImageIO.read(inputStream);
            } else {
                // if the file is not found, display an error message
                JOptionPane.showMessageDialog(myFrame,
                        "Could not locate profile picture file!");
            }

        } catch (final IOException e) {
            JOptionPane.showMessageDialog(myFrame, 
                    "Could not locate profile picture file!");
        }

        return img;
    }
    
    /**
     * Sets up the paint tools and define myActions.
     */
    private void setupPaintTools() {
        
        myAvailableActions = new ArrayList<>();
        
        final LineTool lineTool = new LineTool();
        final EllipseTool ellipseTool = new EllipseTool();
        final RectTool rectTool = new RectTool();
        final PencilTool pencilTool = new PencilTool();
        
        myAvailableActions.add(new PaintToolAction(lineTool, this));
        myAvailableActions.add(new PaintToolAction(ellipseTool, this));
        myAvailableActions.add(new PaintToolAction(rectTool, this));
        myAvailableActions.add(new PaintToolAction(pencilTool, this));
    }   
    
    /**
     * Sets up the menubar with an options menu, tools menu, and a help menu.
     */
    private void setupMenu() {
        
        final JMenu optionsMenu = setupOptionsMenu();
        final JMenu toolsMenu = setupToolsMenu();
        final JMenu helpMenu = setupHelpMenu();
        
        myMenuBar.add(optionsMenu);
        myMenuBar.add(toolsMenu);
        myMenuBar.add(helpMenu);
    }
    
    /**
     * Sets up the options menu by giving it a thickness slider, a color chooser, 
     * and a clear button.
     * 
     * @return The options menu.
     */
    private JMenu setupOptionsMenu() {
        
        final JMenu optionsMenu = new JMenu("Options");
        
        final JMenuItem thicknessItem = setupThicknessSubmenu();
        final JMenuItem colorItem = setupDrawColorButton();
        final JMenuItem colorFillItem = setupFillColorButton();
        myFillCheckbox = setupFillCheckbox();
        myClearButton = setupClearItem();
        
        myFillCheckbox.setEnabled(false);
        myClearButton.setEnabled(false);
        
        optionsMenu.add(thicknessItem);
        optionsMenu.addSeparator();
        optionsMenu.add(colorItem);
        optionsMenu.add(colorFillItem);
        optionsMenu.addSeparator();
        optionsMenu.add(myFillCheckbox);
        optionsMenu.addSeparator();
        optionsMenu.add(myClearButton);
        
        return optionsMenu;
    }

    /**
     * Sets up the thickness slider.
     * 
     * @return A menu item containg a thickness slider.
     */
    private JMenuItem setupThicknessSubmenu() {
        
        final JMenu thicknessSubmenu = new JMenu("Thickness");
        final JSlider thicknessSlider = new JSlider(SwingConstants.HORIZONTAL, 
                0, 15, DEFAULT_THICKNESS);
         
        thicknessSlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        thicknessSlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.setPaintTicks(true);
        
        thicknessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myThickness = thicknessSlider.getValue();
            }
        });
        
        
        thicknessSubmenu.add(thicknessSlider);
        
        return thicknessSubmenu;
    }

    /**
     * Sets up the draw color button.
     * 
     * @return The draw color button.
     */
    private JMenuItem setupDrawColorButton() {
        
        final String buttonName = "Draw Color...";
        final JMenuItem colorButton = new JMenuItem(buttonName);
        
        colorButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                
                myDrawColor = JColorChooser.showDialog(null, buttonName, myDrawColor);
            }
        });
        
        return colorButton;
    }
    
    /**
     * Sets up the fill color button.
     * 
     * @return The fill color button.
     */
    private JMenuItem setupFillColorButton() {
        
        final String buttonName = "Fill Color...";
        final JMenuItem colorButton = new JMenuItem(buttonName);
        
        colorButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                
                myFillColor = JColorChooser.showDialog(null, buttonName, myFillColor);
            }
        });
        
        return colorButton;
    }
    
    private JCheckBoxMenuItem setupFillCheckbox() {
        
        final String buttonName = "Fill";
        final JCheckBoxMenuItem fillCheckbox = new JCheckBoxMenuItem(buttonName);
        
        fillCheckbox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myFillMode = fillCheckbox.isSelected();
            }
        });
        
        return fillCheckbox;
    }
    
    /**
     * Sets up the clear button. 
     * 
     * @return The clear button.
     */
    private JMenuItem setupClearItem() {
        final JMenuItem clearButton = new JMenuItem("Clear");
        
        clearButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawnShapes.clear();
                setBackground(Color.WHITE);
                myShapeIsBeingDrawn = false;
                repaint();
            }
        });
        
        return clearButton;
    }
    
    /**
     * Sets up the tools menu containg the paint tools.
     * 
     * @return The tools menu.
     */
    private JMenu setupToolsMenu() {
        
        final JMenu paintToolsMenu = new JMenu("Tools");
        final ButtonGroup group = new ButtonGroup();
        
        for (final PaintToolAction action : myAvailableActions) {
            createMenuButton(paintToolsMenu, group, action);
        }
        
        
        return paintToolsMenu;
    }
    
    /**
     * Creates a new menu button for a paint tool.
     * 
     * @param thePaintToolsMenu The menu to place the paint tool button in.
     * @param theGroup The button group to place the button in.
     * @param theAction The paint action to be attached to the button.
     */
    private void createMenuButton(final JMenu thePaintToolsMenu, final ButtonGroup theGroup, 
            final Action theAction) {
        
        final JRadioButtonMenuItem createdButton = new JRadioButtonMenuItem(theAction);
        
        theGroup.add(createdButton);
        thePaintToolsMenu.add(createdButton);
    }
    

    /**
     * Sets up the help menu which contains the about button.
     * 
     * @return The help menu.
     */
    private JMenu setupHelpMenu() {

        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutButton = setupAboutButton();
        
        helpMenu.add(aboutButton);
        
        return helpMenu;
    }
    
    /**
     * Sets up the about button which pops up a dialog box.
     * 
     * @return The about button.
     */
    private JMenuItem setupAboutButton() {
        
        final JMenuItem aboutButton = new JMenuItem("About...");

//        final ImageIcon profilePictureIcon = new ImageIcon(getClass().getClassLoader().getResource(PROFILE_PICTURE_PATH));
        final ImageIcon profilePictureIcon = new ImageIcon(getClass().getResource(PROFILE_PICTURE_PATH));

        aboutButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                
                JOptionPane.showMessageDialog(null, "Nathan Hinthorne \nAutumn 2022",
                        "TCSS 305 Paint", JOptionPane.INFORMATION_MESSAGE, 
                        profilePictureIcon);
            }
        });
        
        return aboutButton;
    }
    
    /**
     * Sets up the toolbar which contains each of the paint tools.
     */
    private void setupToolBar() {
        
        myToolBar.setOrientation(JToolBar.HORIZONTAL);
        
        // add paint tools to toolbar
        final ButtonGroup group = new ButtonGroup();
        
        for (final PaintToolAction action : myAvailableActions) {
            createToolButton(group, action);
        }
    }

    /**
     * Creates a new tool button for a paint tool.
     * 
     * @param theGroup The button group to place the button in.
     * @param theAction The paint action to be attached to the button.
     */
    private void createToolButton(final ButtonGroup theGroup, final Action theAction) {
        
        final JToggleButton toggleButton = new JToggleButton(theAction);
        final PaintToolAction paintToolAction = (PaintToolAction) theAction;
        paintToolAction.setActionsList(new ArrayList<PaintToolAction>(myAvailableActions));
        
        theGroup.add(toggleButton);
        
        if (theAction.equals(myAvailableActions.get(0))) {
         // cause the line button to be auto selected
            toggleButton.doClick();
        }
        
        myToolBar.add(toggleButton);
    }
    
    /**
     * Sets the current paint tool. 
     * 
     * @param theTool The PaintTool to use.
     */
    public void setCurrentTool(final PaintTool theTool) {
        this.removeMouseListener(myMouseListener);
        this.removeMouseMotionListener(myMouseListener);
        
        myCurrentTool = theTool;
        
        myMouseListener = new MyMouseListener(myCurrentTool, this, myDrawnShapes);
        this.addMouseListener(myMouseListener);
        this.addMouseMotionListener(myMouseListener);
    }

    /**
     * Sets whether a shape is being drawn at the moment.
     * 
     * @param theShapeIsBeingDrawn Whether a shape is being drawn at the moment.
     */
    public void setShapeBeingDrawn(final boolean theShapeIsBeingDrawn) {
        myShapeIsBeingDrawn = theShapeIsBeingDrawn;
    }

}
