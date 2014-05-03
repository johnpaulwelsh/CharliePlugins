package charlie.bot.client;

import charlie.actor.Courier;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.view.AMoneyManager;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of an IGerty automated Blackjack bot.
 * 
 * @author Katie Craven and John Paul Welsh
 */
public class Gerty implements IGerty {
    protected final Logger LOG = LoggerFactory.getLogger(Gerty.class);
    protected Courier courier;
    protected AMoneyManager moneyManager;
    protected Hid hid;
    protected Dealer dealer;
    protected Seat mine;
    protected HashMap<Hid,Hand> hands = new HashMap<>();
    protected Hid dealerHid;
    private Hand myHand;
    protected boolean myTurn = false;
    protected Card dealerUpCard;
    
    protected int shoeSize;
    protected int gamesPlayed;
    protected int numBjs, numCharlies, numWins, numBreaks, numLoses, numPushes;
    protected int trueCount, runningCount;
    protected int minsPlayed;
    protected int maxBetAmt, meanBetAmt;

    /**
     * Contructor.
     */
    public Gerty() {
    }
    
    /**
     * Initial tasks for a Gerty player to begin playing Blackjack.
     */
    @Override
    public void go() {
        int currBet = 5;

        // invoke upBet on moneyManager ($5 first time guaranteed)
        moneyManager.upBet(currBet, true);
        //     to reduce bet, clear it first and then upBet some more
        
        // send bet amount to Courier, which gives back an Hid
        this.hid = courier.bet(currBet, 0);
    }

    @Override
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics2D g) {
        // draw the following things:
        
        // Counting system
        // Shoe size
        // Running count
        // True count
        // Games played + minutes played
        // Maximum bet amount
        // Mean bet amount per game
        // BJs / Charlies / Wins / Breaks / Loses / Pushes (should add to equal games played)
    }

    /**
     * Starts a game.
     * @param hids Hand ids
     * @param shoeSize Shoe size at game start before cards dealt.
     */
    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        for(Hid hid_: hids) {
            hands.put(hid_,new Hand(hid_));
            if(hid_.getSeat() == Seat.DEALER)
                this.dealerHid = hid_;
        }
        this.shoeSize = shoeSize;
    }

    /**
     * Ends a game.
     * @param shoeSize Shoe size at game end after cards dealt.
     */
    @Override
    public void endGame(int shoeSize) {
        LOG.info("received endGame shoeSize = "+shoeSize);
        gamesPlayed++;
    }

    /**
     * Deals a card.
     * @param hid Target hand id
     * @param card Card
     * @param values Hard and soft values of a hand
     */
    @Override
    public void deal(Hid hid, Card card, int[] values) {
        LOG.info("got card = "+card+" hid = "+hid);
        
        if(card == null)
            return;
        
        if(hid.getSeat() == Seat.DEALER) {
            dealerUpCard = card;
            LOG.info("DEALER UP CARD = " + dealerUpCard.getName());
        }
        
        // Retrieve the hand
        Hand hand = hands.get(hid);
        
        // If the hand does not exist...this could happen if
        // player splits a hand which we don't yet know about.
        // In this case, we'll create the hand "on the fly", as it were.
        if(hand == null) {
            hand = new Hand(hid);
            hands.put(hid, hand);
        }
        
        // Hit the hand
        hand.hit(card);

        if(hid.getSeat() == mine && myHand.size() >= 2)
            myTurn = true;
        
        // It's not my turn if card not mine, my hand broke, or
        // this is the first round of cards in which case it's not
        // my turn!
        if(hid.getSeat() != mine || hand.isBroke() || !myTurn) {
            myTurn = false;
        } else {
            // It's my turn, a card has come my way, and I have to respond
            play(hid);
        }
    }
    
    /**
     * Responds when it is my turn.
     */
    protected void respond() {
        // Sometimes this thread gets called when dealerUpCard has not been
        // instantiated yet (it could also be that dealerUpCard is the Hole
        // Card, which we cannot read anyway). So we check to make sure we
        // only start the thread when the dealerUpCard actually has a value.
        if (dealerUpCard == null)
            return;
        new Thread(new GertyResponder(this, myHand, dealer, dealerUpCard)).start();
    }

    /**
     * Buys insurance -- NOT SUPPORTED.
     */
    @Override
    public void insure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void bust(Hid hid) {
        numBreaks++;
    }

    @Override
    public void win(Hid hid) {
        numWins++;
    }

    @Override
    public void blackjack(Hid hid) {
        numBjs++;
    }

    @Override
    public void charlie(Hid hid) {
        numCharlies++;
    }

    @Override
    public void lose(Hid hid) {
        numLoses++;
    }

    @Override
    public void push(Hid hid) {
        numPushes++;
    }

    @Override
    public void shuffling() {
        LOG.info("shuffling...");
    }

    /**
     * Handles my turn.
     * @param hid Hand id
     */
    @Override
    public void play(Hid hid) {
        // If it is not my turn, there's nothing to do
        if(hid.getSeat() != mine)
            return;
        
        // Othewise respond
        LOG.info("turn hid = "+hid); 
        
        // It's my turn and I have to respond
        respond();
        
        // Now my turn is over
        myTurn = false;
    }
}