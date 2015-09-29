package canfield;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;

/** A widget that displays a Pinball playfield.
 *  @author P. N. Hilfinger
 */
class GameDisplay extends Pad {

    /** Color of display field. */
    private static final Color BACKGROUND_COLOR = Color.white;
    
    /** Blue deck = 0 or Red deck = 1*/
    private static int deck = 0;

    /* Coordinates and lengths in pixels unless otherwise stated. */

    /** Preferred dimensions of the playing surface. */
    private static final int BOARD_WIDTH = 1024, BOARD_HEIGHT = 700;

    /** Displayed dimensions of a card image. */
    private static final int CARD_WIDTH = 90, CARD_HEIGHT = 125;
    
    /** Separation b/w Foundation, Tableau.*/
    private static final int SPACING_X = 110, SPACING_Y = 150;
    
    /** Displayed location of Foundation.*/
    private static final int F1_X = 450, F1_Y = 100;
    private static final int F2_X = F1_X + SPACING_X * 1;
    private static final int F2_Y = F1_Y;
    private static final int F3_X = F1_X + SPACING_X * 2;
    private static final int F3_Y = F1_Y;
    private static final int F4_X = F1_X + SPACING_X * 3;
    private static final int F4_Y = F1_Y;
    
    /** Displayed location of Tableau.*/
    private static final int T1_X = F1_X;
    private static final int T1_Y = F1_Y + SPACING_Y;
    private static final int T2_X = F2_X;
    private static final int T2_Y = F1_Y + SPACING_Y;
    private static final int T3_X = F3_X;
    private static final int T3_Y = F1_Y + SPACING_Y;
    private static final int T4_X = F4_X;
    private static final int T4_Y = F1_Y + SPACING_Y;
    
    /** Displayed location of Reserve.*/
    private static final int R_X = 100;
    private static final int R_Y = T1_Y;
    
    /** Displayed location of Stock.*/
    private static final int S_X = 100;
    private static final int S_Y = T1_Y + SPACING_Y;
    
    /** Displayed location of Waste.*/
    private static final int W_X = 100 + SPACING_X;
    private static final int W_Y = T1_Y + SPACING_Y;

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
            g.drawImage(getCardImage(card), x, y,
                        CARD_WIDTH, CARD_HEIGHT, null);
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
    
    /**Draw Foundation. */
    void drawFounsation(Graphics2D g) {
        paintCard(g, _game.getFoundation().get(0).bottom(), F1_X, F1_Y);
        paintCard(g, _game.getFoundation().get(1).bottom(), F2_X, F2_Y);
        paintCard(g, _game.getFoundation().get(2).bottom(), F3_X, F3_Y);
        paintCard(g, _game.getFoundation().get(3).bottom(), F4_X, F4_Y);
    }
    
    /**Draw Tableau. */
    void drawTableau(Graphics2D g) {
        paintCard(g, _game.getTableau().get(0).bottom(), T1_X, T1_Y);
        paintCard(g, _game.getTableau().get(1).bottom(), T2_X, T2_Y);
        paintCard(g, _game.getTableau().get(2).bottom(), T3_X, T3_Y);
        paintCard(g, _game.getTableau().get(3).bottom(), T4_X, T4_Y);
    }
    
    /**Draw Reserve. */
    void drawReserve(Graphics2D g) {
        paintCard(g, _game.topReserve(), R_X, R_Y);
    }
    
    /**Draw Stock. */
    void drawStock(Graphics2D g) {
        if (_game.getStock().size() != 0) {
            paintBack(g, S_X, S_Y);
        }
    }
    
    /**Draw Waste. */
    void drawWaste(Graphics2D g) {
        paintCard(g, _game.topWaste(), W_X, W_Y);
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        Rectangle b = g.getClipBounds();
        g.fillRect(0, 0, b.width, b.height);
        // FIXME
        g.drawImage(getImage("bg.png"), 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null);
        drawFounsation(g);
        drawTableau(g);
        drawReserve(g);
        drawStock(g);
        drawWaste(g);

    }

    /** Game I am displaying. */
    private final Game _game;

}
