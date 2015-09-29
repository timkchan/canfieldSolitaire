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
        
        addLabel("Base: 0", "base",
                new LayoutSpec("y", 1, "x", 0));
        addLabel("Score: 0", "score",
                new LayoutSpec("y", 1, "x", 1));
        updateCounts();
        
        _display = new GameDisplay(game);
        add(_display, new LayoutSpec("y", 2, "width", 2));
        _display.setMouseHandler("click", this, "mouseClicked");
        _display.setMouseHandler("release", this, "mouseReleased");
        _display.setMouseHandler("drag", this, "mouseDragged");
        _display.setMouseHandler("press", this, "mousePressed"); 
        _display.setMouseHandler("move", this, "mouseMoved");

        display(true);
    }

    /** Response to "Quit" menu item.. */
    public void quit(String dummy) {
        if (showOptions("Really quit?", "Quit?", "question",
                        "Yes", "Yes", "No") == 0) {
            System.exit(1);
        }
    }
    
    /** Display number of points and lines. */
    void updateCounts() {
        setLabel("base",
                 String.format("Base: %1$3s", _game.getBase()));
        setLabel("score",
                 String.format("Score: %1$6s", _game.getScore()));
    }
    
    /** Response to "changeDeck" menu item.. */
    public void changeDeck(String dummy) {
        _display.setColour();
        _display.repaint();
    }
    
    /** Response to "Undo" menu item.. */
    public void undo(String dummy) {
        _game.undo();
        updateCounts();
        _display.repaint();
    }

    /** Response to "restart" menu item.. */
    public void restart(String dummy) {
        //To-do
    }
    
    /** Return True if the cursor is on a card at loc_x, loc_y */
    public boolean onCard(int cursor_x, int cursor_y, int loc_x, int loc_y) {
        return (loc_x <= cursor_x && cursor_x <= loc_x + 90)
                && (loc_y <= cursor_y && cursor_y <= loc_y + 125);
    }
    
    /** Return True if the cursor is on the column */
    public boolean onColumn(int cursor_x, int cursor_y, int loc_x, int loc_y) {
        return (loc_x <= cursor_x && cursor_x <= loc_x + 90)
                && (loc_y <= cursor_y);
    }
    
    /** Return True if the cursor is on Stock */
    public boolean onStock(int cursor_x, int cursor_y) {
        return onCard(cursor_x, cursor_y, _display.S_X, _display.S_Y);
    }
    
    /** Return True if the cursor is on Reserve */
    public boolean onReserve(int cursor_x, int cursor_y) {
        return onCard(cursor_x, cursor_y, _display.R_X, _display.R_Y);
    }
    
    /** Return True if the cursor is on Waste */
    public boolean onWaste(int cursor_x, int cursor_y) {
        return onCard(cursor_x, cursor_y, _display.W_X, _display.W_Y);
    }
    
    /** Return True if the cursor is on any Foundation */
    public boolean onFoundation(int cursor_x, int cursor_y) {
        return onCard(cursor_x, cursor_y, _display.F1_X, _display.F1_Y)
                || onCard(cursor_x, cursor_y, _display.F2_X, _display.F2_Y)
                || onCard(cursor_x, cursor_y, _display.F3_X, _display.F3_Y)
                || onCard(cursor_x, cursor_y, _display.F4_X, _display.F4_Y);
    }
    
    /** Return True if the cursor is on any Tableau (Column)*/
    public boolean onTableau(int cursor_x, int cursor_y) {
        return onColumn(cursor_x, cursor_y, _display.T1_X, _display.T1_Y)
                || onColumn(cursor_x, cursor_y, _display.T2_X, _display.T2_Y)
                || onColumn(cursor_x, cursor_y, _display.T3_X, _display.T3_Y)
                || onColumn(cursor_x, cursor_y, _display.T4_X, _display.T4_Y);
    }
    
    /** Return which the cursor is on T1/2/3/4 */
    public int onWhichT (int cursor_x, int cursor_y) {
        if (onColumn(cursor_x, cursor_y, _display.T1_X, _display.T1_Y)) {
            return 1;
        } else if (onColumn(cursor_x, cursor_y, _display.T2_X, _display.T2_Y)) {
            return 2;
        } else if (onColumn(cursor_x, cursor_y, _display.T3_X, _display.T3_Y)) {
            return 3;
        } else if (onColumn(cursor_x, cursor_y, _display.T4_X, _display.T4_Y)) {
            return 4;
        }
        return 0;
    }
    
    /** Return which the cursor is on F1/2/3/4 */
    public int onWhichF (int cursor_x, int cursor_y) {
        if (onCard(cursor_x, cursor_y, _display.F1_X, _display.F1_Y)) {
            return 1;
        } else if (onCard(cursor_x, cursor_y, _display.F2_X, _display.F2_Y)) {
            return 2;
        } else if (onCard(cursor_x, cursor_y, _display.F3_X, _display.F3_Y)) {
            return 3;
        } else if (onCard(cursor_x, cursor_y, _display.F4_X, _display.F4_Y)) {
            return 4;
        }
        return 0;
    }

    /** Action in response to mouse-moving event EVENT. */
    public synchronized void mouseMoved (MouseEvent event) {
        pressed_x = event.getX();
        if (event.getX() <= _display.BOARD_WIDTH / 2) {
            _display.face_dir = "freefaceL.png";
        } else {
            _display.face_dir = "freefaceR.png";
        }
        _display.repaint();
    }
    
    /** Action in response to mouse-clicking event EVENT. */
    public synchronized void mouseClicked(MouseEvent event) {
        /*c. */
        if (onStock(event.getX(), event.getY())) {
            _game.stockToWaste(); 
        }
        updateCounts();
        _display.repaint();
    }
 
    /* Location where mouse is clicked. */
    int pressed_x = 0, pressed_y = 0;
    
    /** Called when the mouse has been pressed. */
    public synchronized void mousePressed (MouseEvent event) {
        pressed_x = event.getX();
        pressed_y = event.getY();
    }
    
    /** Action in response to mouse-released event EVENT. */
    public synchronized void mouseReleased(MouseEvent event) {
        
        /*rf. */
        if (onReserve(pressed_x, pressed_y)
                && onFoundation(event.getX(), event.getY())) {
            _game.reserveToFoundation();
        }
        
        /*wf. */
        if (onWaste(pressed_x, pressed_y)
                && onFoundation(event.getX(), event.getY())) {
            _game.wasteToFoundation();
        }
        
        /*tf. */
        if (onTableau(pressed_x, pressed_y)
                && onFoundation(event.getX(), event.getY())) {
            _game.tableauToFoundation(onWhichT(pressed_x, pressed_y));
        }
        
        /*rt. */
        if (onReserve(pressed_x, pressed_y)
                && onTableau(event.getX(), event.getY())) {
            _game.reserveToTableau(onWhichT(event.getX(), event.getY()));
        }
        
        /*wt. */
        if (onWaste(pressed_x, pressed_y)
                && onTableau(event.getX(), event.getY())) {
            _game.wasteToTableau(onWhichT(event.getX(), event.getY()));
        }
        
        /*tt. */
        if (onTableau(pressed_x, pressed_y)
                && onTableau(event.getX(), event.getY())) {
            _game.tableauToTableau(onWhichT(pressed_x, pressed_y), onWhichT(event.getX(), event.getY()));
        }
       
        /*ft. */
        if (onFoundation(pressed_x, pressed_y)
                && onTableau(event.getX(), event.getY())) {
            _game.foundationToTableau(onWhichF(pressed_x, pressed_y), onWhichT(event.getX(), event.getY()));
        }
        

        
        
        
        
        
        updateCounts();
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
