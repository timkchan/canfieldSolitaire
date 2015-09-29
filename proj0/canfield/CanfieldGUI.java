package canfield;

import ucb.gui.TopLevel;
import ucb.gui.LayoutSpec;

import java.awt.event.MouseEvent;

/** A top-level GUI for Canfield solitaire.
 *  @author Tim Chan
 */
class CanfieldGUI extends TopLevel {

    /** A new window with given TITLE and displaying GAME. */
    CanfieldGUI(String title, Game game) {
        super(title, true);
        _game = game;
        
        addMenuButton("Data->Undo", "undo");
        addMenuButton("Data->Restart", "restart");
        addMenuButton("Data->Quit", "quit");
        
        addMenuButton("View->Change Deck Colour", "changeDeck");
        
        /*
        addLabel("Sorry, no graphical interface yet",
                 new LayoutSpec("y", 0, "x", 0));
        addButton("Quit", "quit", new LayoutSpec("y", 0, "x", 1));
        */

        _display = new GameDisplay(game);
        add(_display, new LayoutSpec("y", 2, "width", 2));
        _display.setMouseHandler("click", this, "mouseClicked");
        _display.setMouseHandler("release", this, "mouseReleased");
        _display.setMouseHandler("drag", this, "mouseDragged");

        display(true);
    }

    /** Response to "Quit" menu item.. */
    public void quit(String dummy) {
        if (showOptions("Really quit?", "Quit?", "question",
                        "Yes", "Yes", "No") == 0) {
            System.exit(1);
        }
    }
    
    /** Response to "changeDeck" menu item.. */
    public void changeDeck(String dummy) {
        _display.setColour();
        _display.repaint();
    }
    
    /** Response to "Undo" menu item.. */
    public void undo(String dummy) {
        _game.undo();
        _display.repaint();
    }

    /** Response to "restart" menu item.. */
    public void restart(String dummy) {
        //To-do
    }
    
    /** Action in response to mouse-clicking event EVENT. */
    public synchronized void mouseClicked(MouseEvent event) {
        event.getSource();
        _game.stockToWaste();
        _display.repaint();
    }
 
    /** Action in response to mouse-released event EVENT. */
    public synchronized void mouseReleased(MouseEvent event) {
        // FIXME
        _display.repaint();
    }

    /** Action in response to mouse-dragging event EVENT. */
    public synchronized void mouseDragged(MouseEvent event) {
        // FIXME
        _display.repaint();  // Not needed if picture does not change.
    }

    /** The board widget. */
    private final GameDisplay _display;

    /** The game I am consulting. */
    private final Game _game;

}
