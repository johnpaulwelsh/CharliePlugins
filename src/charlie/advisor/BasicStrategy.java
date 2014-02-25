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
        Integer handTotal; //total for hand
	Integer dealerCard;
        	
	for (handTotal = 5; handTotal<9; handTotal++) {
            for(dealerCard = 2; dealerCard<12; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                justSum.put(cards, Play.HIT);
            }
	}
	
        Integer[] cardsTwo = {9, 2};
	justSum.put(cardsTwo, Play.HIT);
        
	for(dealerCard =3; dealerCard<7; dealerCard++) {
            Integer[] cards = {9, dealerCard};
            justSum.put(cards, Play.DOUBLE_DOWN);
	}
	
	for(dealerCard = 7; dealerCard<12; dealerCard++) {
            Integer[] cards = {9, dealerCard};
            justSum.put(cards, Play.HIT);
	}
	
	for(dealerCard=2; dealerCard<10; dealerCard++) {
            Integer[] cards = {10, dealerCard};
            justSum.put(cards,Play.DOUBLE_DOWN);
	}
	
        cardsTwo[0] = 10;
        cardsTwo[1] = 10;
	justSum.put(cardsTwo, Play.HIT);
        cardsTwo[1] = 11;
	justSum.put(cardsTwo, Play.HIT);
	
	for(dealerCard =2; dealerCard<11; dealerCard++) {
            Integer[] cards = {11, dealerCard};       
            justSum.put(cards, Play.DOUBLE_DOWN);
	}
	
        cardsTwo[0] = 11;
        cardsTwo[1] = 11;
        justSum.put(cardsTwo, Play.HIT);
        cardsTwo[0] = 12;
        cardsTwo[1] = 2;
        justSum.put(cardsTwo, Play.HIT);
        cardsTwo[1] = 3;
        justSum.put(cardsTwo, Play.HIT);
        cardsTwo[1] = 4;
        justSum.put(cardsTwo, Play.STAY);
        cardsTwo[1] = 5;
        justSum.put(cardsTwo, Play.STAY);
        cardsTwo[1] = 6;
        justSum.put(cardsTwo, Play.STAY);

        for (dealerCard = 7; dealerCard < 12; dealerCard++) {
            Integer[] cards = {12, dealerCard};
            justSum.put(cards, Play.HIT);
        }

        for (handTotal = 13; handTotal < 17; handTotal++) {
            for (dealerCard = 2; dealerCard < 7; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                justSum.put(cards, Play.STAY);
            }

            for (dealerCard = 7; dealerCard < 12; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                justSum.put(cards, Play.HIT);
            }
        }

        for (handTotal = 17; handTotal < 22; handTotal++) {
            for (dealerCard = 2; dealerCard < 12; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                justSum.put(cards, Play.STAY);
            }
        }
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
            isPair.put(cb, Play.DOUBLE_DOWN);
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
        //this is acting on the assumption that the comparisons
        //to ACE will be with the card that is not an ace, and 
        //instead we have an array of [notAceCard, dealerCard]
        //doesnt include AA
        
        Integer notAce;
        Integer dealerCard;
        Integer[] bundleT = new Integer[2];
        
        //for A2-A3
        for (notAce = 2; notAce<4; notAce++)
        {
            //dealer 2-4
            for(dealerCard=2; dealerCard<5;dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.HIT);
            }
            
            //dealer 5-6
            for(dealerCard= 5; dealerCard<7; dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.DOUBLE_DOWN);
            }
            
            //dealer 7-A(11)
            for(dealerCard= 7; dealerCard<12; dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.HIT);
            }
        }
        
        //for A4-A5
        for (notAce=4; notAce<6; notAce++)
        {
            //dealer hand 2+3
            for(dealerCard =2; dealerCard<4;dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.HIT);
            }
            
            //dealer hand 4-6
            for(dealerCard = 4; dealerCard<7; dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.DOUBLE_DOWN);
            }
            
            //hand 7-A(11)
            for(dealerCard=7; dealerCard<11; dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.HIT);
            }
        }
        
        //A6 dealer 2
        
        bundleT[0] = 6;
        bundleT[1] = 2;
        hasAce.put(bundleT, Play.HIT);
        
        //A6 dealer 3-6
        for(dealerCard=3; dealerCard<7;dealerCard++)
        {
            Integer[] bundle = {6, dealerCard};
            hasAce.put(bundle, Play.DOUBLE_DOWN);
        }
        
        //A6 dealer 7-A(11)
        for(dealerCard=7; dealerCard<12; dealerCard++)
        {
            Integer[] bundle = {6, dealerCard};
            hasAce.put(bundle, Play.HIT);
        }
        
        //A7, dealer2
        bundleT[0] = 7;
        bundleT[1] = 2;
        hasAce.put(bundleT, Play.STAY);
        
        //A7, dealer3-6
        for(dealerCard=3; dealerCard<7;dealerCard++)
        {
            Integer[] bundle = {7, dealerCard};
            hasAce.put(bundle, Play.DOUBLE_DOWN);
        }
        
        //A7, dealer7-8
        for(dealerCard = 7;dealerCard<9; dealerCard++)
        {
            Integer[] bundle = {7, dealerCard};
            hasAce.put(bundle, Play.DOUBLE_DOWN);
        }
        
        //A7, dealer 9-A(11)
        for (dealerCard=9; dealerCard<12;dealerCard++)
        {
            Integer[] bundle = {7, dealerCard};
            hasAce.put(bundle, Play.HIT);
        }
            
        //A8-A10
        for (notAce = 8; notAce<11;notAce++)
        {
            for(dealerCard=2; dealerCard<12; dealerCard++)
            {
                Integer[] bundle = {notAce, dealerCard};
                hasAce.put(bundle, Play.STAY);
            }
        }
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
        Play advisedPlay;
        Integer[] bundle = new Integer[2];
        
        // If there are two cards in the hand
        if (myHand.size() == 2) {
            
            /*
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
                bundle[0] = myHand.getValue();
                bundle[1] = upCard.value();
                advisedPlay = justSum.get(bundle);
            }
            */
            // Check if the cards are a pair (includes A,A)
            if (myHand.isPair()) {
                bundle[0] = myHand.getCard(0).value();
                bundle[1] = upCard.value();
                advisedPlay = isPair.get(bundle);
            
            // Otherwise, use the sum of the cards
            } else {
                bundle[0] = (Integer)myHand.getValue();
                bundle[1] = (Integer)upCard.value();
                advisedPlay = justSum.get(bundle);
            }
            
        // Otherwise, use the sum of the cards
        } else {
            bundle[0] = (Integer)myHand.getValue();
            bundle[1] = (Integer)upCard.value();
            advisedPlay = justSum.get(bundle);
        }
        
        return advisedPlay;
    }
}