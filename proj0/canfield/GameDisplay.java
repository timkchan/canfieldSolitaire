package canfield;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;

/**
 * A widget that displays a Pinball playfield.
 *
 * @author P. N. Hilfinger
 */
class GameDisplay extends Pad {

    /** Color of display field. */
    private static final Color BACKGROUND_COLOR = Color.white;

    /** Blue deck = 0 or Red deck = 1. */
    private static int deck = 0;

    /* Coordinates and lengths in pixels unless otherwise stated. */

    /** Preferred dimensions of the playing surface. */
    static final int BOARD_WIDTH = 1024, BOARD_HEIGHT = 700;

    /** Displayed dimensions of a card image. */
    static final int CARD_WIDTH = 90, CARD_HEIGHT = 125;

    /** Displayed dimensions of Freeface. */
    static final int FF_WIDTH = 50, FF_HEIGHT = 50;

    /** Displayed dimensions of Freeface background. */
    static final int FFBG_WIDTH = FF_WIDTH + 10,
            FFBG_HEIGHT = FF_HEIGHT + 10;

    /** Separation b/w Foundation, Tableau. */
    private static final int SPACINGX = 110, SPACINGY = 150;

    /** Separation b/w vertically overlapped cards. */
    private static final int SPACING_VERTICAL = CARD_HEIGHT / 5;

    /** Displayed location of F1. */
    static final int F1X = 450, F1Y = 100;
    /** Displayed location of F2. */
    static final int F2X = F1X + SPACINGX * 1, F2Y = F1Y;
    /** Displayed location of F3. */
    static final int F3X = F1X + SPACINGX * 2, F3Y = F1Y;
    /** Displayed location of F4. */
    static final int F4X = F1X + SPACINGX * 3, F4Y = F1Y;

    /** Displayed location of T1. */
    static final int T1X = F1X, T1Y = F1Y + SPACINGY;
    /** Displayed location of T2. */
    static final int T2X = F2X, T2Y = F1Y + SPACINGY;
    /** Displayed location of T3. */
    static final int T3X = F3X, T3Y = F1Y + SPACINGY;
    /** Displayed location of T4. */
    static final int T4X = F4X, T4Y = F1Y + SPACINGY;

    /** Displayed location of Reserve. */
    static final int RX = 100, RY = T1Y;

    /** Displayed location of Stock. */
    static final int SX = 100, SY = T1Y + SPACINGY;

    /** Displayed location of Waste. */
    static final int WX = 100 + SPACINGX, WY = T1Y + SPACINGY;

    /** Direction of free face. */
    private static String faceDir = "freefaceL.png";

    /** Set direction of free face to left. */
    static void setFaceDirL() {
        faceDir = "freefaceL.png";
    }

    /** Set direction of free face to right. */
    static void setFaceDirR() {
        faceDir = "freefaceR.png";
    }

    /** A graphical representation of GAME. */
    public GameDisplay(Game game) {
        _game = game;
        setPreferredSize(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /** Return an Image read from the resource named NAME. */
    private Image getImage(String name) {
        InputStream in =
                getClass().getResourceAsStream("/canfield/resources/" + name);
        try {
            return ImageIO.read(in);
        } catch (IOException excp) {
            return null;
        }
    }

    /** Return an Image of CARD. */
    private Image getCardImage(Card card) {
        return getImage("playing-cards/" + card + ".png");
    }

    /** Return an Image of the back of a card. */
    private Image getBackImage() {
        if (deck == 0) {
            return getImage("playing-cards/blue-back.png");
        } else {
            return getImage("playing-cards/red-back.png");
        }
    }

    /** Draw CARD at X, Y on G. */
    private void paintCard(Graphics2D g, Card card, int x, int y) {
        if (card != null) {
            g.drawImage(getCardImage(card),
                    x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    /** Draw card back at X, Y on G. */
    private void paintBack(Graphics2D g, int x, int y) {
        g.drawImage(getBackImage(), x, y, CARD_WIDTH, CARD_HEIGHT, null);
    }

    /** Change colour of the deck. */
    void setColour() {
        deck = 1 - deck;
    }

    /** Draw Foundation.
     *
     * @param g
     *      Graphic2D object
     */
    void drawFounsation(Graphics2D g) {
        paintCard(g, _game.getFoundation().get(0).top(), F1X, F1Y);
        paintCard(g, _game.getFoundation().get(1).top(), F2X, F2Y);
        paintCard(g, _game.getFoundation().get(2).top(), F3X, F3Y);
        paintCard(g, _game.getFoundation().get(3).top(), F4X, F4Y);
    }

    /** Draw Tableau.
     *
     * @param g
     *      Graphic2D object
     */
    void drawTableau(Graphics2D g) {
        for (int i = 0; i < _game.getTableau().get(0).size(); i++) {
            paintCard(g, _game.getTableau().get(0).get(
                    _game.getTableau().get(0).size() - i - 1),
                        T1X, T1Y + SPACING_VERTICAL * i);
        }
        for (int i = 0; i < _game.getTableau().get(1).size(); i++) {
            paintCard(g, _game.getTableau().get(1).get(
                    _game.getTableau().get(1).size() - i - 1),
                        T2X, T2Y + SPACING_VERTICAL * i);
        }
        for (int i = 0; i < _game.getTableau().get(2).size(); i++) {
            paintCard(g, _game.getTableau().get(2).get(
                    _game.getTableau().get(2).size() - i - 1),
                        T3X, T3Y + SPACING_VERTICAL * i);
        }
        for (int i = 0; i < _game.getTableau().get(3).size(); i++) {
            paintCard(g, _game.getTableau().get(3).get(
                    _game.getTableau().get(3).size() - i - 1),
                        T4X, T4Y + SPACING_VERTICAL * i);
        }
    }

    /** Draw Reserve.
     *
     * @param g
     *      Graphic2D object
     */
    void drawReserve(Graphics2D g) {
        paintCard(g, _game.topReserve(), RX, RY);
    }

    /** Draw Stock.
     *
     * @param g
     *      Graphic2D object
     */
    void drawStock(Graphics2D g) {
        if (_game.getStock().size() != 0) {
            paintBack(g, SX, SY);
        }
    }

    /** Draw Waste.
     *
     * @param g
     *      Graphic2D object
     */
    void drawWaste(Graphics2D g) {
        paintCard(g, _game.topWaste(), WX, WY);
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        Rectangle b = g.getClipBounds();
        g.fillRect(0, 0, b.width, b.height);
        g.drawImage(getImage("bg.png"), 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);
        g.drawImage(getImage("ffbg.png"),
                (BOARD_WIDTH - FFBG_WIDTH) / 2, 2 * 10,
                    FFBG_HEIGHT, FFBG_WIDTH, null);
        g.drawImage(getImage(faceDir), (BOARD_WIDTH - FF_WIDTH) / 2, 2 * 10 + 5,
                FF_HEIGHT, FF_WIDTH, null);
        drawFounsation(g);
        drawTableau(g);
        drawReserve(g);
        drawStock(g);
        drawWaste(g);
    }

    /** Game I am displaying. */
    private final Game _game;

}
