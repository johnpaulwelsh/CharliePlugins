/**
 * Our implementation of a Charlie blackjack advisor plugin.
 */

package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

/**
 * @author Katie Craven and John Paul Welsh
 */
public class Advisor implements IAdvisor {
    
    @Override
    public Play advise(Hand myHand, Card upCard) {
        BasicStrategy bs;
        bs = new BasicStrategy();        
        return bs.getPlay(myHand, upCard);
    }
}