package canfield;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of the Game class.
 *
 * @author Tim Chan
 */

public class GameTest {

    /** Example. */
    @Test
    public void testInitialScore() {
        Game g = new Game();
        g.deal();
        assertEquals(5, g.getScore());
    }

    /** Unit test for equals function for Pile class. */
    @Test
    public void testPileEquals() {
        Pile p1 = new Pile(Card.CA, Card.C2);
        Pile p2 = new Pile(Card.CA, Card.C2);
        Pile p3 = new Pile(Card.CA, Card.CA);
        assertEquals(p1, p2);
        assertEquals(false, p2.equals(p3));
    }

    /** Unit test for undo function. */
    @Test
    public void testUndo() {
        Game g = new Game();
        g.seed(5);
        g.deal();

        Game initState = new Game();
        initState.copyFrom(g);

        g.stockToWaste();
        g.undo();
        assertEquals(g.getStock(), initState.getStock());

        g.undo();
        assertEquals(g.getStock(), initState.getStock());

        g.tableauToFoundation(4);
        g.undo();
        assertEquals(g.getStock(), initState.getStock());

        for (int i = 0; i < 5; i++) {
            g.stockToWaste();
        }
        for (int i = 0; i < 7; i++) {
            g.undo();
        }
        assertEquals(g.getStock(), initState.getStock());
    }
}
