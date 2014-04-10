package charlie.bs.section4;

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
public class Test01_22_2 {
    
    private static IAdvisor advisor;
    
    public Test01_22_2() {
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
    public void Test01_Hand_22_Up_2() {
        Hid hid = new Hid(Seat.YOU, 1.0, 1.5);
        Hand hand = new Hand(hid);
        
        // Hand comp = 8, 8
        hand.hit(new Card(8, Card.Suit.HEARTS));
        hand.hit(new Card(8, Card.Suit.SPADES));
        
        // Up card = 6
        Play result = advisor.advise(hand, new Card(4, Card.Suit.CLUBS));
        Play expectedPlay = Play.SPLIT;
        
        assertEquals(expectedPlay, result);
    }
}