package charlie.bs.section2;

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
public class Test01_5_2 {
    
    private static IAdvisor advisor;
    
    public Test01_5_2() {
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
    public void Test01_Hand_5_Up_2() {
        Hid hid = new Hid(Seat.YOU, 1.0, 1.5);
        Hand hand = new Hand(hid);
        
        // Hand total = 6
        hand.hit(new Card(2, Card.Suit.HEARTS));
        hand.hit(new Card(4, Card.Suit.HEARTS));
        
        // Up card = 2
        Play result = advisor.advise(hand, new Card(2, Card.Suit.SPADES));
        Play expectedPlay = Play.HIT;
        
        assertEquals(expectedPlay, result);
    }
}