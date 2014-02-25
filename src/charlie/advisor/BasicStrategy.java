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
    private HashMap<Integer[], Play> justSum; // sum of cards' values
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
        int handTotal;
	int dealerCard;
        //Integer[] cards = new Integer[2];
		
	for (handTotal = 5; handTotal<9; handTotal++)
	{
		for(dealerCard = 2; dealerCard<12; dealerCard++)
		{
                    Integer[] cards = {handTotal, dealerCard};
                    justSum.put(cards, Play.HIT);
                }
	}
	
        Integer[] cardsTwo = {9, 2};
	justSum.put(cardsTwo, Play.HIT);
	
	for(dealerCard =3; dealerCard<7; dealerCard++)
	{
                Integer[] cards = {9, dealerCard};
		justSum.put(cards, Play.DOUBLE_DOWN);               
	}
	
	for(dealerCard = 7; dealerCard<12; dealerCard++)
	{
               Integer[] cards = {9, dealerCard};
               justSum.put(cards, Play.HIT);
	}
	
	for(dealerCard=2; dealerCard<10; dealerCard++)
	{
            Integer[] cards = {10, dealerCard};
            justSum.put(cards,Play.DOUBLE_DOWN);
	}
	
        cardsTwo[0] = 10;
        cardsTwo[1] = 10;
	justSum.put(cardsTwo, Play.HIT);
        cardsTwo[1] = 11;
	justSum.put(cardsTwo, Play.HIT);
	
	for(dealerCard =2; dealerCard<11; dealerCard++)
	{
            Integer[] cards = {11, dealerCard};       
            justSum.put(cards, Play.DOUBLE_DOWN);
                
	}
	
        cardsTwo[0] = 11;
        cardsTwo[1] = 11;
	justSum.put(cardsTwo, Play.HIT);
	cardsTwo[0] = 12;
        cardsTwo[1] = 2;
	justSum.put(cardsTwo, Play.HIT);
        cardsTwo[1]= 3;
	justSum.put(cardsTwo, Play.HIT);
        cardsTwo[1]= 4;
	justSum.put(cardsTwo, Play.STAY);
        cardsTwo[1]=5;
	justSum.put(cardsTwo, Play.STAY);
        cardsTwo[1] = 6;
	justSum.put(cardsTwo, Play.STAY);
	
	for(dealerCard =7; dealerCard<12; dealerCard++)
	{
            Integer[] cards = {12, dealerCard};
            justSum.put(cards, Play.HIT);
	}
	
	for(handTotal=13; handTotal<17; handTotal++)
	{
		for(dealerCard= 2; dealerCard<7; dealerCard++)
		{
                    Integer[] cards = {handTotal, dealerCard};
                    justSum.put(cards, Play.STAY);
		}
		
		for(dealerCard = 7; dealerCard<12; dealerCard++)
		{
                    Integer[] cards = {handTotal, dealerCard};
                    justSum.put(cards, Play.HIT);
		}
	}
	
	for(handTotal= 17; handTotal<22; handTotal++)
	{
		for(dealerCard=2; dealerCard<12; dealerCard++)
		{
                    Integer[] cards = {handTotal, dealerCard};
                    justSum.put(cards, Play.STAY);
		}
	}
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