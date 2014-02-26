package charlie.bs.section3;

import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IAdvisor;
import charlie.util.Play;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John Paul Welsh
 */
public class Test00_A2_6 {
    
    private static IAdvisor advisor;
    
    public Test00_A2_6() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        advisor = new Advisor();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void Hand_A2_Up_6() {
        Hid hid = new Hid(Seat.YOU, 1.0, 1.5);
        Hand hand = new Hand(hid);
        
        // Hand comp = Ace, 7
        hand.hit(new Card(1, Card.Suit.HEARTS));
        hand.hit(new Card(7, Card.Suit.HEARTS));
        
        // Up card = 3
        Play result = advisor.advise(hand, new Card(3, Card.Suit.HEARTS));
        Play expectedPlay = Play.DOUBLE_DOWN;
        
        assertEquals(expectedPlay, result);
    }
}