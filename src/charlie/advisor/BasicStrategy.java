package charlie.advisor;

import java.util.HashMap;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * @author Katie Craven and John Paul Welsh
 */
public class BasicStrategy {
    //private HashMap<HandCardBundle, Play> justSum; // sum of cards' values
    //private HashMap<HandCardBundle, Play> isPair;  // one of the cards' values
    //private HashMap<HandCardBundle, Play> hasAce;  // non-Ace card's value
    
    private HashMap<Integer[], Play> justSum; // sum of cards' values
    private HashMap<Integer[], Play> isPair;  // one of the cards' values
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
        Integer[] cardBundle = new Integer[2];
        
        // case for As
        for (int up = 1; up <= 10; up++) {
            Integer[] cb = {1, up};
            isPair.put(cb, Play.SPLIT);
        }
        
        // case for 2s
        cardBundle[0] = 2;
        cardBundle[1] = 1;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 2; up <= 7; up++) {
            Integer[] cb = {2, up};
            isPair.put(cb, Play.SPLIT);
        }
        for (int up = 8; up <= 10; up++) {
            Integer[] cb = {2, up};
            isPair.put(cb, Play.HIT);
        }
        
        // case for 3s
        cardBundle[0] = 2;
        cardBundle[1] = 1;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 2; up <= 7; up++) {
            Integer[] cb = {3, up};
            isPair.put(cb, Play.SPLIT);
        }
        for (int up = 8; up <= 10; up++) {
            Integer[] cb = {3, up};
            isPair.put(cb, Play.HIT);
        }
        
        // case for 4s
        for (int up = 1; up <= 4; up++) {
            Integer[] cb = {4, up};
            isPair.put(cb, Play.HIT);
        }
        for (int up = 5; up <= 6; up++) {
            Integer[] cb = {4, up};
            isPair.put(cb, Play.SPLIT);
        }
        for (int up = 7; up <= 10; up++) {
            Integer[] cb = {3, up};
            isPair.put(cb, Play.HIT);
        }

        // case for 5s
        cardBundle[0] = 5;
        cardBundle[1] = 1;
        isPair.put(cardBundle, Play.HIT);
        cardBundle[1] = 10;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 2; up <= 9; up++) {
            Integer[] cb = {5, up};
            isPair.put(cb, Play.SPLIT);
        }
        
        // case for 6s
        cardBundle[0] = 6;
        cardBundle[1] = 1;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 2; up <= 6; up++) {
            Integer[] cb = {6, up};
            isPair.put(cb, Play.SPLIT);
        }
        for (int up = 7; up <= 10; up++) {
            Integer[] cb = {6, up};
            isPair.put(cb, Play.HIT);
        }
        
        // case for 7s
        cardBundle[0] = 7;
        cardBundle[1] = 1;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 2; up <= 7; up++) {
            Integer[] cb = {7, up};
            isPair.put(cb, Play.SPLIT);
        }
        for (int up = 8; up <= 10; up++) {
            Integer[] cb = {7, up};
            isPair.put(cb, Play.HIT);
        }

        // case for 8s
        for (int up = 1; up <= 10; up++) {
            Integer[] cb = {8, up};
            isPair.put(cb, Play.SPLIT);
        }
        
        // case for 9s
        cardBundle[0] = 9;
        cardBundle[1] = 1;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 2; up <= 6; up++) {
            Integer[] cb = {9, up};
            isPair.put(cb, Play.SPLIT);
        }
        cardBundle[1] = 7;
        isPair.put(cardBundle, Play.HIT);
        for (int up = 8; up <= 9; up++) {
            Integer[] cb = {9, up};
            isPair.put(cb, Play.SPLIT);
        }
        cardBundle[1] = 10;
        isPair.put(cardBundle, Play.HIT);
        
        // case for 10s
        for (int up = 1; up <= 10; up++) {
            Integer[] cb = {10, up};
            isPair.put(cb, Play.STAY);
        }
    }
    
    // Both
    private void populateHasAce() {
            
    }
    
    public Play getPlay(Hand myHand, Card upCard) {
        //HandCardBundle hcb = new HandCardBundle(myHand, upCard);
        Play advisedPlay;
        Integer[] bundle = new Integer[2];
        
        // If there are two cards in the hand
        if (myHand.size() == 2) {
            if (myHand.isPair()) { // Check if the cards are a pair (includes A,A)
                bundle[0] = myHand.getCard(0).value();
                bundle[1] = upCard.value();
                advisedPlay = isPair.get(bundle);
            } else if (myHand.isPair()) { // ACTUALLY IS HAS ACE

            } else { // Otherwise use sum of the cards
                //advisedPlay = justSum.get();
            }
        } else { // Use sum of the cards
            advisedPlay = justSum.get(hcb);
        }

        return advisedPlay;
    }
}