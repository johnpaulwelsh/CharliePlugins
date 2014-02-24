package charlie.advisor;

import java.util.HashMap;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import charlie.card.Card.Suit;

/**
 * @author Katie Craven and John Paul Welsh
 */
public class BasicStrategy {
    //private HashMap<HandCardBundle, Play> justSum; // sum of cards' values
    //private HashMap<HandCardBundle, Play> isPair;  // one of the cards' values
    //private HashMap<HandCardBundle, Play> hasAce;  // non-Ace card's value
    
    private HashMap<Integer[], Play> justSum; // sum of cards' values
    private HashMap<Integer, Play> isPair;  // one of the cards' values
    private HashMap<Integer[], Play> hasAce;  // non-Ace card's value
    
    public BasicStrategy() {
        justSum = new HashMap();
        isPair = new HashMap();
        hasAce = new HashMap();
        
        populateJustSum();
        populateIsPair();
        populateHasAce();
    }
    
    // Katie
    private void populateJustSum() {
        
    }
    
    // JP
    private void populateIsPair() {
        
        isPair.put(1, Play.SPLIT);
        isPair.put(2, Play.SPLIT);
        isPair.put(3, Play.SPLIT);
        isPair.put(4, Play.SPLIT);
        isPair.put(5, Play.SPLIT);
        isPair.put(6, Play.SPLIT);
        isPair.put(7, Play.SPLIT);
        isPair.put(8, Play.SPLIT);
        isPair.put(9, Play.SPLIT);
        isPair.put(10, Play.SPLIT);
        
    }
    
    // Both
    private void populateHasAce() {
            
    }
    
    public Play getPlay(Hand myHand, Card upCard) {
        HandCardBundle hcb = new HandCardBundle(myHand, upCard);
        Play advisedPlay;
        
        // If there are two cards in the hand
        if (myHand.size() == 2) {
            if (myHand.isPair()) // Check if the cards are a pair (includes A,A)
                advisedPlay = isPair.get(hcb);
            else if (myHand.isPair()) // Check if one of the cards is an ace
                advisedPlay = isPair.get(hcb);
            else // Otherwise use sum of the cards
                advisedPlay = justSum.get(hcb);
        } else { // Use sum of the cards
            advisedPlay = justSum.get(hcb);
        }

        return advisedPlay;
    }
}