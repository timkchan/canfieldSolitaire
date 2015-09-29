package canfield;

import ucb.gui.TopLevel;
import ucb.gui.LayoutSpec;

import java.awt.event.MouseEvent;

/**
 * A top-level GUI for Canfield solitaire.
 *
 * @author Tim Chan
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

        addLabel("Base: 0", "base", new LayoutSpec("y", 1, "x", 0));
        addLabel("Score: 0", "score", new LayoutSpec("y", 1, "x", 1));
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
        setLabel("base", String.format("Base: %1$3s", _game.getBase()));
        setLabel("score", String.format("Score: %1$6s", _game.getScore()));
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
        _game.deal();
        updateCounts();
        _display.repaint();
    }

    /** Return True if the cursor is on a card at locX, locY.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     *  @param locX
     *      X-coordinate of card relative to the frame.
     *  @param locY
     *      Y-coordinate of card relative to the frame.
     */
    public boolean onCard(int cursorX, int cursorY, int locX, int locY) {
        return (locX <= cursorX && cursorX <= locX + 90)
                && (locY <= cursorY && cursorY <= locY + 125);
    }

    /** Return True if the cursor is on the column.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     *  @param locX
     *      X-coordinate of card relative to the frame.
     *  @param locY
     *      Y-coordinate of card relative to the frame.
     */
    public boolean onColumn(int cursorX, int cursorY, int locX, int locY) {
        return (locX <= cursorX && cursorX <= locX + 90) && (locY <= cursorY);
    }

    /** Return True if the cursor is on Stock.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public boolean onStock(int cursorX, int cursorY) {
        return onCard(cursorX, cursorY, GameDisplay.SX, GameDisplay.SY);
    }

    /** Return True if the cursor is on Reserve.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public boolean onReserve(int cursorX, int cursorY) {
        return onCard(cursorX, cursorY, GameDisplay.RX, GameDisplay.RY);
    }

    /** Return True if the cursor is on Waste.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public boolean onWaste(int cursorX, int cursorY) {
        return onCard(cursorX, cursorY, GameDisplay.WX, GameDisplay.WY);
    }

    /** Return True if the cursor is on any Foundation.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public boolean onFoundation(int cursorX, int cursorY) {
        return onCard(cursorX, cursorY, GameDisplay.F1X, GameDisplay.F1Y)
                || onCard(cursorX, cursorY, GameDisplay.F2X, GameDisplay.F2Y)
                || onCard(cursorX, cursorY, GameDisplay.F3X, GameDisplay.F3Y)
                || onCard(cursorX, cursorY, GameDisplay.F4X, GameDisplay.F4Y);
    }

    /** Return True if the cursor is on any Tableau (Column).
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public boolean onTableau(int cursorX, int cursorY) {
        return onColumn(cursorX, cursorY, GameDisplay.T1X, GameDisplay.T1Y)
                || onColumn(cursorX, cursorY, GameDisplay.T2X, GameDisplay.T2Y)
                || onColumn(cursorX, cursorY, GameDisplay.T3X, GameDisplay.T3Y)
                || onColumn(cursorX, cursorY, GameDisplay.T4X, GameDisplay.T4Y);
    }

    /** Return which the cursor is on T1/2/3/4.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public int onWhichT(int cursorX, int cursorY) {
        if (onColumn(cursorX, cursorY, GameDisplay.T1X, GameDisplay.T1Y)) {
            return 1;
        } else if (onColumn(cursorX, cursorY, GameDisplay.T2X, GameDisplay.T2Y)) {
            return 2;
        } else if (onColumn(cursorX, cursorY, GameDisplay.T3X, GameDisplay.T3Y)) {
            return 3;
        } else if (onColumn(cursorX, cursorY, GameDisplay.T4X, GameDisplay.T4Y)) {
            return 4;
        }
        return 0;
    }

    /** Return which the cursor is on F1/2/3/4.
     *  @param cursorX
     *      X-coordinate of mouse relative to the frame.
     *  @param cursorY
     *      Y-coordinate of mouse relative to the frame.
     */
    public int onWhichF(int cursorX, int cursorY) {
        if (onCard(cursorX, cursorY, GameDisplay.F1X, GameDisplay.F1Y)) {
            return 1;
        } else if (onCard(cursorX, cursorY, GameDisplay.F2X, GameDisplay.F2Y)) {
            return 2;
        } else if (onCard(cursorX, cursorY, GameDisplay.F3X, GameDisplay.F3Y)) {
            return 3;
        } else if (onCard(cursorX, cursorY, GameDisplay.F4X, GameDisplay.F4Y)) {
            return 4;
        }
        return 0;
    }

    /** Action in response to mouse-moving event EVENT. */
    public synchronized void mouseMoved(MouseEvent event) {
        pressedX = event.getX();
        if (event.getX() <= GameDisplay.BOARD_WIDTH / 2) {
            GameDisplay.faceDir = "freefaceL.png";
        } else {
            GameDisplay.faceDir = "freefaceR.png";
        }
        _display.repaint();
    }

    /** Action in response to mouse-clicking event EVENT. */
    public synchronized void mouseClicked(MouseEvent event) {
        /* c. */
        if (onStock(event.getX(), event.getY())) {
            _game.stockToWaste();
        }
        updateCounts();
        _display.repaint();
    }

    /* Location where mouse is clicked. */
    int pressedX = 0, pressedY = 0;

    /** Called when the mouse has been pressed. */
    public synchronized void mousePressed(MouseEvent event) {
        pressedX = event.getX();
        pressedY = event.getY();
    }

    /** Action in response to mouse-released event EVENT. */
    public synchronized void mouseReleased(MouseEvent event) {

        /* rf. */
        if (onReserve(pressedX, pressedY) && onFoundation(event.getX(), event.getY())) {
            _game.reserveToFoundation();
        }

        /* wf. */
        if (onWaste(pressedX, pressedY) && onFoundation(event.getX(), event.getY())) {
            _game.wasteToFoundation();
        }

        /* tf. */
        if (onTableau(pressedX, pressedY) && onFoundation(event.getX(), event.getY())) {
            _game.tableauToFoundation(onWhichT(pressedX, pressedY));
        }

        /* rt. */
        if (onReserve(pressedX, pressedY) && onTableau(event.getX(), event.getY())) {
            _game.reserveToTableau(onWhichT(event.getX(), event.getY()));
        }

        /* wt. */
        if (onWaste(pressedX, pressedY) && onTableau(event.getX(), event.getY())) {
            _game.wasteToTableau(onWhichT(event.getX(), event.getY()));
        }

        /* tt. */
        if (onTableau(pressedX, pressedY) && onTableau(event.getX(), event.getY())) {
            _game.tableauToTableau(onWhichT(pressedX, pressedY),
                    onWhichT(event.getX(), event.getY()));
        }

        /* ft. */
        if (onFoundation(pressedX, pressedY) && onTableau(event.getX(), event.getY())) {
            _game.foundationToTableau(onWhichF(pressedX, pressedY),
                    onWhichT(event.getX(), event.getY()));
        }

        updateCounts();
        _display.repaint();
    }

    /** Action in response to mouse-dragging event EVENT. */
    public synchronized void mouseDragged(MouseEvent event) {
        // FIXME
        _display.repaint(); // Not needed if picture does not change.
    }

    /** The board widget. */
    private final GameDisplay _display;

    /** The game I am consulting. */
    private final Game _game;

}
