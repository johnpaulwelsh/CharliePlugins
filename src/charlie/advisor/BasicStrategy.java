package charlie.advisor;

import java.util.HashMap;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;

/**
 * @author Katie Craven and John Paul Welsh
 */
public class BasicStrategy {
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
    
    // John Paul
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
    
    /**
     * Private method used in getPlay to determine whether a Hand object is
     * holding an Ace. This will not interfere with the A,A pair hand because
     * that is covered by a separate HashMap.
     * @param hand the hand being checked for an Ace
     * @return the index where the Ace was found, -1 if not found
     */
    private int hasAce(Hand hand) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.getCard(i).isAce()) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Method that calls upon the capabilities of the Basic Blackjack Strategy
     * and advises the player on the best move to make. Called by Advisor.java
     * @param myHand the player's hand
     * @param upCard the dealer's face-up card
     * @return the suggestion for Play
     */
    public Play getPlay(Hand myHand, Card upCard) {
        Play advisedPlay = Play.NONE;
        Integer[] bundle = new Integer[2];
        
        // If there are two cards in the hand
        if (myHand.size() == 2) {
            
            // Check if the cards are a pair (includes A,A)
            if (myHand.isPair()) {
                bundle[0] = myHand.getCard(0).value();
                bundle[1] = upCard.value();
                advisedPlay = isPair.get(bundle);
            
            // Check whether the player's hand has an Ace
            } else if (hasAce(myHand) != -1) {
                bundle[0] = myHand.getCard(hasAce(myHand)).value();
                bundle[1] = upCard.value();
                advisedPlay = hasAce.get(bundle);
            
            // Otherwise, use the sum of the cards
            } else {
                Integer firstValue = myHand.getCard(0).value();
                Integer secondValue = myHand.getCard(1).value();
                bundle[0] = firstValue + secondValue;
                bundle[1] = upCard.value();
                advisedPlay = justSum.get(bundle);
            }
            
        // Otherwise, use the sum of the cards
        } else {
            Integer firstValue = myHand.getCard(0).value();
            Integer secondValue = myHand.getCard(1).value();
            bundle[0] = firstValue + secondValue;
            bundle[1] = upCard.value();
            advisedPlay = justSum.get(bundle);
        }

        return advisedPlay;
    }
}