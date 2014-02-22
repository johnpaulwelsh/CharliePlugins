package charlie.advisor;

import java.util.HashMap;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * @author Katie Craven and John Paul Welsh
 */
public class BasicStrategy {
    /*
    if cards == 2
        check if pair (isPair) includes A,A
        check if has Ace does not include A,A
        if neither, use top half
    else
        use top half
    */
    private HashMap<Integer, Play> justSum; // sum of cards' values
    private HashMap<Integer, Play> isPair;  // one of the cards' values
    private HashMap<Integer, Play> hasAce;  // non-Ace card's value
    
    public BasicStrategy() {
        justSum = new HashMap();
        isPair = new HashMap();
        hasAce = new HashMap();
        
        populateJustSum();
        populateIsPair();
        populateHasAce();
    }
    
    private void populateJustSum() {
        
    }
    
    private void populateIsPair() {
        
    }
    
    private void populateHasAce() {
            
    }
    
    public Play getPlay(Hand h, Card c) {
        HandCardBundle hcb = new HandCardBundle(h, c);

        // look for hcb as the key in the correct HashMap and return its mapped value
        return null;
    }
}