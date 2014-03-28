package charlie.bot.server;

import charlie.advisor.Advisor;
import charlie.card.Hand;
import charlie.dealer.Dealer;
import charlie.plugin.IBot;
import charlie.util.Play;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Katie Craven and John Paul Welsh
 */
public class Responder implements Runnable {

    private final IBot b9;
    private final Hand myHand;
    private final Dealer dealer;
    private final Hand dealerHand;
    private final Logger LOG = LoggerFactory.getLogger(Responder.class);
    
    public Responder(IBot b9, Hand myHand, Dealer dealer, Hand dealerHand) {
        this.b9 = b9;
        this.myHand = myHand;
        this.dealer = dealer;
        this.dealerHand = dealerHand;
    }
    
    @Override
    public void run() {
        Advisor adv = new Advisor();
        LOG.info("Dealer's upcard is " + dealerHand.getCard(0));
        LOG.info("THIS IS MY HAND =" +myHand);
        Play aPlay = adv.advise(myHand, dealerHand.getCard(0));
        switch(aPlay) {
            case HIT:
                dealer.hit(b9, myHand.getHid());
                LOG.info("YO DIS HIT");
                LOG.info("THIS IS MY HAND =" +myHand.getValue());
                break;
            case DOUBLE_DOWN: 
                dealer.doubleDown(b9, myHand.getHid());
                LOG.info("YO DIS DD");
                break;
            case STAY:
                dealer.stay(b9, myHand.getHid());
                LOG.info("YO DIS STAY");
                LOG.info("THIS IS MY HAND =" +myHand.getValue());
                break;
            case SPLIT:
                //for now, we just hit
                dealer.hit(b9, myHand.getHid());
                LOG.info("YO DIS SPLIT");
                break;
            default:
                LOG.info("YO DIS ????");
                break;
        }
    }
}