package charlie.bot.server;

import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.dealer.Dealer;
import charlie.plugin.IBot;
import charlie.util.Play;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread to handle a response from the IBot.
 * @author Katie Craven and John Paul Welsh
 */
public class Responder implements Runnable {
    private final IBot b9;
    private final Hand myHand;
    private final Dealer dealer;
    private final Card dealerUpCard;
    private final Logger LOG = LoggerFactory.getLogger(Responder.class);
    
    public Responder(IBot b9, Hand myHand, Dealer dealer, Card dealerUpCard) {
        this.b9 = b9;
        this.myHand = myHand;
        this.dealer = dealer;
        this.dealerUpCard = dealerUpCard;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
          LOG.info("Thread Error: " + ex);
        }

        Advisor adv = new Advisor();        
        
        Play aPlay = adv.advise(myHand, dealerUpCard);
        switch(aPlay) {
            case HIT:
                dealer.hit(b9, myHand.getHid());
                break;
            case DOUBLE_DOWN:
                // We cannot double down with more than 2 cards,
                // so if the BS tells us to DD, we will hit instead
                // since it's essentially the same action.
                if (myHand.size() > 2) {
                    dealer.hit(b9, myHand.getHid());
                } else {
                    dealer.doubleDown(b9, myHand.getHid());
                }
                break;
            case STAY:
                dealer.stay(b9, myHand.getHid());
                break;
            case SPLIT:
                if (myHand.getValue() >= 17) {
                    dealer.stay(b9,  myHand.getHid());
                } else if (myHand.getValue() <= 10) {
                    dealer.hit(b9, myHand.getHid());
                } else if (myHand.getValue() == 11) {
                    dealer.doubleDown(b9, myHand.getHid());
                } else {
                    // instead of approximating with the assumption
                    // that the dealer's hole card is 10, we make
                    // the bot hit here as a way to make it less than
                    // a perfect player. So if given SPLIT, it will hit
                    // on any value for its own hand that is between
                    // 12 and 16.
                    dealer.hit(b9, myHand.getHid());
                }
                break;
            default:
                LOG.info("????");
                break;
        }        
    }
}