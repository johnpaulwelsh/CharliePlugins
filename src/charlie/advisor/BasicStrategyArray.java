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
    private HashMap<Array, Play> justSum; // sum of cards' values
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
        int handTotal
		int dealerCard;
		
		for (handTotal = 5; handTotal<9; handTotal++)
		{
			for(dealerCard = 2; dealerCard<12; dealerCard++)
			{
				justSum.put([handTotal, dealerCard], HIT);
			}
		}
		
		justSum.put([9, 2], HIT);
		
		for(dealerCard =3; dealerCard<7; dealerCard++)
		{
			justSum.put([9, dealerCard], DOUBLE_DOWN);
		}
		
		for(dealerCard = 7; dealerCard<12; dealerCard++)
		{
			justSum.put([9, dealerCard], HIT);
		}
		
		for(dealerCard=2; dealerCard<10; dealerCard++)
		{
			justSum.put([10,dealerCard],DOUBLE_DOWN);
		}
		
		justSum.put([10,10], HIT);
		justSum.put([10,11], HIT);
		
		for(dealerCard =2; dealerCard<11; dealercard++)
		{
			justSum.put([11,dealerCard], DOUBLE_DOWN);
		}
		
		justSum.put([11,11], HIT);
		
		justSum.put([12,2], HIT);
		justSum.put([12,3], HIT);
		justSum.put([12,4], STAY);
		justSum.put([12,5], STAY);
		justSum.put([12,6], STAY);
		
		for(dealerCard =7; dealerCard<12; dealerCard++)
		{
			justSum.put([12,dealerCard], HIT);
		}
		
		for(handTotal=13; handTotal<17; handTotal++)
		{
			for(dealerCard= 2; dealerCard<7; dealerCard++)
			{
				justSum.put([handTotal, dealerCard], STAY);
			}
			
			for(dealerCard = 7; dealerCard<12; dealerCard++)
			{
				justSum.put([handTotal, dealerCard], HIT);
			}
		}
		
		for(handTotal= 17; handTotal<22; handTotal++)
		{
			for(dealerCard=2; dealerCard<12; dealerCard++)
			{
				justSum.put([handTotal dealerCard] STAY);
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