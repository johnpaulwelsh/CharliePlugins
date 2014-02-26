package charlie.advisor;

import java.util.HashMap;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import java.util.Arrays;

/**
 * @author Katie Craven and John Paul Welsh
 */
public class BasicStrategy {
    private final HashMap<Integer, Play> justSum; // sum of cards' values
    private final HashMap<Integer, Play> isPair;  // one of the cards' values
    private final HashMap<Integer, Play> hasAce;  // non-Ace card's value
    
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
        Integer handTotal;
	Integer dealerCard;
        Integer hashVal;
        
        
        //For a hand of 5-8
	for (handTotal = 5; handTotal<9; handTotal++) {
            //for dealer upcard of 2-11(ace)
            for(dealerCard = 2; dealerCard<12; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                hashVal = Arrays.hashCode(cards);
                justSum.put(hashVal, Play.HIT);
            }
	}
	
        //For a hand of 9 with dealer upcard of 2
        Integer[] cardsTwo = {9, 2};
        hashVal = Arrays.hashCode(cardsTwo);
	justSum.put(hashVal, Play.HIT);
        
        //for a hand of 9 with dealer upcard 3-6
	for(dealerCard =3; dealerCard<7; dealerCard++) {
            Integer[] cards = {9, dealerCard};
            hashVal = Arrays.hashCode(cards);
            justSum.put(hashVal, Play.DOUBLE_DOWN);
	}
	
        //for a hand of 9 with a dealer upcard of 7-11(ace)
	for(dealerCard = 7; dealerCard<12; dealerCard++) {
            Integer[] cards = {9, dealerCard};
            hashVal = Arrays.hashCode(cards);
            justSum.put(hashVal, Play.HIT);
	}
	
        //for a hand of 10 with a dealer upcard 2-9
	for(dealerCard=2; dealerCard<10; dealerCard++) {
            Integer[] cards = {10, dealerCard};
            hashVal = Arrays.hashCode(cards);
            justSum.put(hashVal,Play.DOUBLE_DOWN);
	}
	
        //for hand of 10 with dealer upcard of 10
        cardsTwo[0] = 10;
        cardsTwo[1] = 10;
        hashVal = Arrays.hashCode(cardsTwo);
	justSum.put(hashVal, Play.HIT);
        //for a hand of 10 with a dealer upcard of 11(ace)
        cardsTwo[1] = 11;
        hashVal = Arrays.hashCode(cardsTwo);
	justSum.put(hashVal, Play.HIT);
	
        //for a hand of 11 with a dealer upcard 2-10
	for(dealerCard =2; dealerCard<11; dealerCard++) {
            Integer[] cards = {11, dealerCard};
            hashVal = Arrays.hashCode(cards);
            justSum.put(hashVal, Play.DOUBLE_DOWN);
	}
	
        //for a hand of 11 with a dealer upcard of 11(ace)
        cardsTwo[0] = 11;
        cardsTwo[1] = 11;
        hashVal = Arrays.hashCode(cardsTwo);
        justSum.put(hashVal, Play.HIT);
        
        //for a hand of 12 with a dealer upcard of 2
        cardsTwo[0] = 12;
        cardsTwo[1] = 2;
        hashVal = Arrays.hashCode(cardsTwo);
        //for a hand of 12 with a dealer upcard of 3
        justSum.put(hashVal, Play.HIT);
        cardsTwo[1] = 3;
        hashVal = Arrays.hashCode(cardsTwo);
        justSum.put(hashVal, Play.HIT);
        
        //for a hand of 12 with a dealer upcard of 4
        cardsTwo[1] = 4;
        hashVal = Arrays.hashCode(cardsTwo);
        justSum.put(hashVal, Play.STAY);
        
        //for a hand of 12 with a dealer upcard of 5
        cardsTwo[1] = 5;
        hashVal = Arrays.hashCode(cardsTwo);
        justSum.put(hashVal, Play.STAY);
        
        //for a hand of 12 with a dealer upcard of 6
        cardsTwo[1] = 6;
        hashVal = Arrays.hashCode(cardsTwo);
        justSum.put(hashVal, Play.STAY);

        //for a hand of 12 with a dealer upcard of 7-11(ace)
        for (dealerCard = 7; dealerCard < 12; dealerCard++) {
            Integer[] cards = {12, dealerCard};
            hashVal = Arrays.hashCode(cards);
            justSum.put(hashVal, Play.HIT);
        }

        //for a hand of 13
        for (handTotal = 13; handTotal < 17; handTotal++) {
            //for a dealer upcard of 2-6
            for (dealerCard = 2; dealerCard < 7; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                hashVal = Arrays.hashCode(cards);
                justSum.put(hashVal, Play.STAY);
            }

            //for a dealer upcard of 7-11(ace)
            for (dealerCard = 7; dealerCard < 12; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                hashVal = Arrays.hashCode(cards);
                justSum.put(hashVal, Play.HIT);
            }
        }

        //for a hand of 17+ 
        for (handTotal = 17; handTotal < 22; handTotal++) {
            //for dealer upcard 2-11(ace)
            for (dealerCard = 2; dealerCard < 12; dealerCard++) {
                Integer[] cards = {handTotal, dealerCard};
                hashVal = Arrays.hashCode(cards);
                justSum.put(hashVal, Play.STAY);
            }
        }
    }
    
    // John Paul
    private void populateIsPair() {
        Integer[] cardBundle = new Integer[2];
        Integer hashVal;
        Integer up;
        
        // case for As
        for (up = 1; up <= 10; up++) {
            Integer[] cb = {1, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        
        // case for 2s
        cardBundle[0] = 2;
        cardBundle[1] = 1;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 2; up <= 7; up++) {
            Integer[] cb = {2, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        for (up = 8; up <= 10; up++) {
            Integer[] cb = {2, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.HIT);
        }
        
        // case for 3s
        cardBundle[0] = 2;
        cardBundle[1] = 1;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 2; up <= 7; up++) {
            Integer[] cb = {3, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        for (up = 8; up <= 10; up++) {
            Integer[] cb = {3, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.HIT);
        }
        
        // case for 4s
        for (up = 1; up <= 4; up++) {
            Integer[] cb = {4, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.HIT);
        }
        for (up = 5; up <= 6; up++) {
            Integer[] cb = {4, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        for (up = 7; up <= 10; up++) {
            Integer[] cb = {4, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.HIT);
        }

        // case for 5s
        cardBundle[0] = 5;
        cardBundle[1] = 1;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        cardBundle[1] = 10;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 2; up <= 9; up++) {
            Integer[] cb = {5, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.DOUBLE_DOWN);
        }
        
        // case for 6s
        cardBundle[0] = 6;
        cardBundle[1] = 1;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 2; up <= 6; up++) {
            Integer[] cb = {6, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        for (up = 7; up <= 10; up++) {
            Integer[] cb = {6, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.HIT);
        }
        
        // case for 7s
        cardBundle[0] = 7;
        cardBundle[1] = 1;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 2; up <= 7; up++) {
            Integer[] cb = {7, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        for (up = 8; up <= 10; up++) {
            Integer[] cb = {7, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.HIT);
        }

        // case for 8s
        for (up = 1; up <= 10; up++) {
            Integer[] cb = {8, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        
        // case for 9s
        cardBundle[0] = 9;
        cardBundle[1] = 1;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 2; up <= 6; up++) {
            Integer[] cb = {9, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        cardBundle[1] = 7;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        for (up = 8; up <= 9; up++) {
            Integer[] cb = {9, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.SPLIT);
        }
        cardBundle[1] = 10;
        hashVal = Arrays.hashCode(cardBundle);
        isPair.put(hashVal, Play.HIT);
        
        // case for 10s
        for (up = 1; up <= 10; up++) {
            Integer[] cb = {10, up};
            hashVal = Arrays.hashCode(cb);
            isPair.put(hashVal, Play.STAY);
        }
    }
    
    // Katie
    private void populateHasAce() {
        /*
        This is acting on the assumption that the comparisons
        to ACE will be with the card that is not an ace, and 
        instead we have an array of [notAceCard, dealerCard].
        Doesn't include AA.
        */
        
        Integer notAce;
        Integer dealerCard;
        Integer[] bundleT = new Integer[2];
        Integer hashVal;

        //for A2-A3
        for (notAce = 2; notAce < 4; notAce++) {
            //dealer 2-4
            for (dealerCard = 2; dealerCard < 5; dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.HIT);
            }

            //dealer 5-6
            for (dealerCard = 5; dealerCard < 7; dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.DOUBLE_DOWN);
            }

            //dealer 7-A(11)
            for (dealerCard = 7; dealerCard < 12; dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.HIT);
            }
        }
        
        //for A4-A5
        for (notAce=4; notAce<6; notAce++) {
            //dealer hand 2+3
            for(dealerCard =2; dealerCard<4;dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.HIT);
            }
            
            //dealer hand 4-6
            for(dealerCard = 4; dealerCard<7; dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.DOUBLE_DOWN);
            }
            
            //hand 7-A(11)
            for(dealerCard=7; dealerCard<11; dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.HIT);
            }
        }
        
        //A6 dealer 2
        bundleT[0] = 6;
        bundleT[1] = 2;
        hashVal = Arrays.hashCode(bundleT);
        hasAce.put(hashVal, Play.HIT);
        
        //A6 dealer 3-6
        for(dealerCard=3; dealerCard<7;dealerCard++) {
            Integer[] bundle = {6, dealerCard};
            hashVal = Arrays.hashCode(bundle);
            hasAce.put(hashVal, Play.DOUBLE_DOWN);
        }
        
        //A6 dealer 7-A(11)
        for(dealerCard=7; dealerCard<12; dealerCard++) {
            Integer[] bundle = {6, dealerCard};
            hashVal = Arrays.hashCode(bundle);
            hasAce.put(hashVal, Play.HIT);
        }
        
        //A7, dealer2
        bundleT[0] = 7;
        bundleT[1] = 2;
        hashVal = Arrays.hashCode(bundleT);
        hasAce.put(hashVal, Play.STAY);
        
        //A7, dealer3-6
        for(dealerCard=3; dealerCard<7;dealerCard++) {
            Integer[] bundle = {7, dealerCard};
            hashVal = Arrays.hashCode(bundle);
            hasAce.put(hashVal, Play.DOUBLE_DOWN);
        }
        
        //A7, dealer7-8
        for(dealerCard = 7;dealerCard<9; dealerCard++) {
            Integer[] bundle = {7, dealerCard};
            hashVal = Arrays.hashCode(bundle);
            hasAce.put(hashVal, Play.DOUBLE_DOWN);
        }
        
        //A7, dealer 9-A(11)
        for (dealerCard=9; dealerCard<12;dealerCard++) {
            Integer[] bundle = {7, dealerCard};
            hashVal = Arrays.hashCode(bundle);
            hasAce.put(hashVal, Play.HIT);
        }
            
        //A8-A10
        for (notAce = 8; notAce<11;notAce++) {
            for(dealerCard=2; dealerCard<12; dealerCard++) {
                Integer[] bundle = {notAce, dealerCard};
                hashVal = Arrays.hashCode(bundle);
                hasAce.put(hashVal, Play.STAY);
            }
        }
    }
    
    /**
     * Private method used in getPlay to determine whether a Hand object is
     * holding an Ace. This will not interfere with the A,A pair hand because
     * that is covered by a separate HashMap.
     * @param hand the hand being checked for an Ace
     * @return the index where the non-Ace card is, -1 if there is no Ace
     */
    private int hasAce(Hand hand) {
        // If the first card is an Ace, return the other card's index
        if (hand.getCard(0).isAce())
            return 1;
        // If the second card is an Ace, return the other card's index
        else if (hand.getCard(1).isAce())
            return 0;
        // Neither card is an Ace, so we return -1
        else
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
        //Play advisedPlay;
        Integer[] bundle = new Integer[2];
        Integer hashValz;
        
        // If there are two cards in the hand
        if (myHand.size() <= 2) {
            
            // Check if the cards are a pair (includes A,A)
            if (myHand.isPair()) {
                bundle[0] = myHand.getCard(0).value();
                bundle[1] = upCard.value();
                hashValz = Arrays.hashCode(bundle);
                return isPair.get(hashValz);
            
            // Check whether the player's hand has an Ace
            } else if (hasAce(myHand) != -1) {
                bundle[0] = myHand.getCard(hasAce(myHand)).value();
                bundle[1] = upCard.value();
                //return Play.DOUBLE_DOWN;
                hashValz = Arrays.hashCode(bundle);
                return hasAce.get(hashValz);
            
            // Otherwise, use the sum of the cards
            } else {
                bundle[0] = myHand.getValue();
                bundle[1] = upCard.value();
                //Integer[] hard = {17, 10};
                hashValz = Arrays.hashCode(bundle);
                return justSum.get(hashValz);
                //return justSum.get(bundle);
            }
            
        // Otherwise, use the sum of the cards
        } else {
            bundle[0] = (Integer)myHand.getValue();
            bundle[1] = (Integer)upCard.value();
            hashValz = Arrays.hashCode(bundle);
            return justSum.get(hashValz);   
        }
    }
}