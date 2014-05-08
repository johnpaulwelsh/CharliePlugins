package charlie.bot.client;

import charlie.actor.Courier;
import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread to handle a response from the IGerty.
 * @author Katie Craven and John Paul Welsh
 */
public class GertyResponder implements Runnable {
    private final Hand myHand;
    private final Card dealerUpCard;
    private final Courier courier;
    private final Logger LOG = LoggerFactory.getLogger(GertyResponder.class);
    
    public GertyResponder(Hand myHand, Courier courier, Card dealerUpCard) {
        this.myHand = myHand;
        this.courier = courier;
        this.dealerUpCard = dealerUpCard;
    }
    
    @Override
    public void run() {
//        try {
//            //Delay = a random number between 1000 and 3000
//            int delay = 1000 + (int)(Math.random() * ((3000 - 1000) + 1));
//            Thread.sleep(delay);
//        } catch (InterruptedException ex) {
//            LOG.info("Thread Error: " + ex);
//        }

        Advisor adv = new Advisor();
        Play aPlay = adv.advise(myHand, dealerUpCard);
        
        switch(aPlay) {
            case HIT:
                LOG.info("MY HAND HID = " + myHand.getHid());
                courier.hit(myHand.getHid());
                break;
            case DOUBLE_DOWN:
                // We cannot double down with more than 2 cards,
                // so if the BS tells us to DD, we will hit instead
                // since it's essentially the same action.
                if (myHand.size() > 2) {
                    courier.hit(myHand.getHid());
                } else {
                    courier.dubble(myHand.getHid());
                    Gerty.STAYED = true;
                }
                break;
            case STAY:
                courier.stay(myHand.getHid());
                Gerty.STAYED = true;
                break;
            case SPLIT:
                if (myHand.getValue() >= 17) {
                    courier.stay(myHand.getHid());
                    Gerty.STAYED = true;
                    
                } else if (myHand.getValue() <= 10) {
                    courier.hit(myHand.getHid());
                    
                } else if (myHand.getValue() == 11) {
                    courier.dubble(myHand.getHid());
                    Gerty.STAYED = true;
                    
                } else {
                    // instead of approximating with the assumption
                    // that the dealer's hole card is 10, we make
                    // the bot hit here as a way to make it less than
                    // a perfect player. So if given SPLIT, it will hit
                    // on any value for its own hand that is between
                    // 12 and 16.
                    courier.hit(myHand.getHid());
                }
                break;
            default:
                LOG.info("????");
                break;
        }        
    }
}