package charlie.advisor;

import charlie.card.Hand;
import charlie.card.Card;

/**
 * @author John Paul Welsh
 */
public class HandCardBundle {
    private Hand h;
    private Card c;

    public HandCardBundle(Hand h, Card c) {
        this.h = h;
        this.c = c;
    }
    
    public Hand getHand() {
        return h;
    }
    
    public Card getCard() {
        return c;
    }
}