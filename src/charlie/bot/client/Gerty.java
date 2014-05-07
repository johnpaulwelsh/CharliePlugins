package charlie.bot.client;

import charlie.actor.Courier;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.view.AMoneyManager;
import java.awt.Color;
import java.awt.Font;
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
    protected Seat mine;
    protected HashMap<Hid,Hand> hands = new HashMap<>();
    protected Hid dealerHid;
    private Hand myHand;
    private Hand dealerHand;
    protected boolean myTurn = false;
    protected Card dealerUpCard;
    
    protected static boolean STAYED;
    
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
        // to reduce bet, clear it first and then upBet some more
        
        // send bet amount to Courier, which gives back an Hid
        this.mine = Seat.YOU;
        this.hid = courier.bet(currBet, 0);
        this.myHand = new Hand(this.hid);
        STAYED = false;
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
        g.setFont(new Font("ARIAL", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        int xx = 10;
        
        g.drawString("Card Counting System: Hi-Low", xx, 40);
        g.drawString("Shoe Size: "+shoeSize, xx, 60);
        g.drawString("Running Count: "+runningCount, xx, 80);
        g.drawString("True Count: "+trueCount, xx, 100);
        g.drawString("Games Played: "+gamesPlayed, xx, 120);
        g.drawString("Minutes Played: ???", xx, 140);
        g.drawString("Maximum bet amount: " +maxBetAmt, xx, 160);
        g.drawString("Mean bet amount: ???", xx, 180);
        g.drawString("Blackjacks: "+numBjs, xx,200);
        g.drawString("Charles: "+numCharlies, xx, 220);
        g.drawString("Wins: "+numWins, xx, 240);
        g.drawString("Breaks: "+numBreaks, xx, 260);
        g.drawString("Loses: "+numLoses, xx, 280);
        g.drawString("Pushes: "+numPushes, xx, 300);
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
            if(hid_.getSeat() == Seat.DEALER) {
                this.dealerHid = hid_;
            }
        }
        this.shoeSize = shoeSize;
        STAYED = false;
    }

    /**
     * Ends a game.
     * @param shoeSize Shoe size at game end after cards dealt.
     */
    @Override
    public void endGame(int shoeSize) {
        myTurn = false;
        STAYED = true;
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
        
        if(STAYED || card == null)
            return;
        
        if(hid.getSeat() == Seat.DEALER) {
            dealerUpCard = card;
        }
        
        // Retrieve the hand
        Hand hand = hands.get(hid);
        
        if (hid.getSeat() == Seat.DEALER) {
            dealerHand = hand;
        }
        
        // If the hand does not exist...this could happen if
        // player splits a hand which we don't yet know about.
        // In this case, we'll create the hand "on the fly", as it were.
        if(hand == null) {
            hand = new Hand(hid);
            hands.put(hid, hand);
        }
        
        // Hit the hand
        hand.hit(card);
        
        // If the card being dealt is mine, and this is during the initial deal,
        // add the card to my hand. Otherwise, it's my turn so move onto the
        // parts where I can make a play
        if(hid.getSeat() == mine) {
            if (myHand.size() < 2)
                myHand.hit(card);
            else
                myTurn = true;
        }
        
        if (dealerHand != null && dealerHand.size() >= 2 &&
                (dealerHand.isBlackjack() || dealerHand.isBroke() || dealerHand.isCharlie()))
            myTurn = false;
        
        if (myHand.isBlackjack() || myHand.isCharlie())
            myTurn = false;
        
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
        new Thread(new GertyResponder(this, myHand, courier, dealerUpCard)).start();
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
        STAYED = true;
    }

    @Override
    public void win(Hid hid) {
        numWins++;
        STAYED = true;
    }

    @Override
    public void blackjack(Hid hid) {
        numBjs++;
        STAYED = true;
    }

    @Override
    public void charlie(Hid hid) {
        numCharlies++;
        STAYED = true;
    }

    @Override
    public void lose(Hid hid) {
        numLoses++;
        STAYED = true;
    }

    @Override
    public void push(Hid hid) {
        numPushes++;
        STAYED = true;
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
        if(hid.getSeat() != mine) {
            return;
        }
        // Othewise respond
        LOG.info("turn hid = "+hid);
        
        // It's my turn and I have to respond
        respond();
        
        // Now my turn is over
        myTurn = false;
    }
}