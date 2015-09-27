package canfield;

import static org.junit.Assert.*;
import org.junit.Test;

/** Tests of the Game class.
 *  @author Tim Chan
 */

public class GameTest {

    /** Example. */
    @Test
    public void testInitialScore() {
        Game g = new Game();
        g.deal();
        assertEquals(5, g.getScore());
    }

    /** Unit test for undo function. */
    @Test
    public void testUndo() {
    	Game g = new Game();
    	g.seed(5);
    	g.deal();
    	Card c1 = Card.CA;
    	Card c2 = Card.CA;
    	assertEquals(Card.CA, Card.CA);
    	Pile p1 = new Pile(Card.CA, Card.C2);
    	Pile p2 = new Pile(Card.CA, Card.C2);
    	assertEquals(true, p1.equals(p2));
    }

}
