package charlie.bot.client;

import charlie.actor.Courier;
import charlie.advisor.Advisor;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.util.Play;
import charlie.view.AMoneyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

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
    protected Advisor adv = new Advisor();
    protected long startTime = System.currentTimeMillis( );
    protected int betSums = 0;

    
    protected static boolean STAYED;
    protected static boolean ENDGAME;
    
    protected static final int MIN_BET = 5;
    
    protected DecimalFormat df = new DecimalFormat("#.##");
    protected double shoeSize;
    protected int gamesPlayed = 0;
    protected int numBjs, numCharlies, numWins, numBreaks, numLoses, numPushes;
    protected int trueCount = 0, runningCount = 0;
    protected int minsPlayed;
    protected int maxBetAmt, meanBetAmt = 0;

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
        LOG.info("===GO===");
        
        // Determine
        int currBet;
        if (gamesPlayed == 0)
            currBet = MIN_BET;
        else {
            //currBet = (Math.max(1, 1 + trueCount) * 5);
            currBet = MIN_BET;
            LOG.info("CURRENT BET = "+currBet);
        }
        
        betSums = betSums+currBet;
        
        if(currBet > maxBetAmt){
            maxBetAmt = currBet;
        }
        
        moneyManager.clearBet();
        
        try {
            Thread.sleep(500);

            int bet = currBet;
            int tempBet = bet;
            while(tempBet!= 0){
                if(tempBet >= 100){
                    moneyManager.upBet(100, true);
                    tempBet = tempBet - 100;
                } else if(tempBet >= 25){
                    moneyManager.upBet(25, true);
                    tempBet = tempBet - 25;
                }else{
                    moneyManager.upBet(5, true);
                    tempBet = tempBet - 5;
                }
            }
            
        } catch (InterruptedException ex) {
            
        }
        
        // send bet amount to Courier, which gives back an Hid
        this.mine = Seat.YOU;
        this.hid = courier.bet(currBet, 0);
        this.myHand = new Hand(this.hid);
        LOG.info("===END GO===");
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
        
        long endTime = System.currentTimeMillis( );
        long diff = endTime - startTime;
        int minutes = (int) ((diff/1000)/60);
        int seconds = (int) ((diff/1000) - (minutes*60));
        String sec = String.format("%02d", seconds);
        
        if(gamesPlayed > 0){
            meanBetAmt = betSums/gamesPlayed;
        }
        
        g.drawString("System: Hi-Lo", xx, 40);
        g.drawString("Shoe Size: "+df.format(shoeSize), xx, 60);
        g.drawString("Running Count: "+runningCount, xx, 80);
        g.drawString("True Count: "+trueCount, xx, 100);
        g.drawString("Games Played: "+gamesPlayed, xx, 120);
        g.drawString("Mins Played: "+minutes+":"+sec, xx, 140);
        g.drawString("Max Bet Amt: " +maxBetAmt, xx, 160);
        g.drawString("Mean Bet Amt: "+meanBetAmt, xx, 180);
        g.drawString("Blackjacks: "+numBjs, xx,200);
        g.drawString("Charlies: "+numCharlies, xx, 220);
        g.drawString("Wins: "+numWins, xx, 240);
        g.drawString("Breaks/Busts: "+numBreaks, xx, 260);
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
        this.shoeSize = (double) (shoeSize / 52.0);
        
        STAYED = false;
        ENDGAME = false;
        
        LOG.info("----------START GAME");
    }

    /**
     * Ends a game.
     * @param shoeSize Shoe size at game end after cards dealt.
     */
    @Override
    public void endGame(int shoeSize) {
        LOG.info("END GAME----------");

        myTurn = false;
        STAYED = true;
        ENDGAME = true;
        dealerHid = null;

        trueCount = (int) (runningCount / (shoeSize / 52.0));
                
        //LOG.info("received endGame shoeSize = "+shoeSize);
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
        
        // Block for applying hi-lo strategy to card being dealt
        if (card.value() != null) {
            if (card.isAce())
                runningCount -= 1;
            else if (card.value() > 9)
                runningCount -= 1;
            else if (card.value() < 7)
                runningCount += 1;
            else
                runningCount += 0;
        }
        
        if (ENDGAME)
            return;
        
        LOG.info("got card = "+card+" hid = "+hid);
        
        if(card == null)
            return;
        
        Hand h = hands.get(hid);
        
        if(hid.getSeat() == Seat.DEALER) {
            dealerUpCard = card;
            dealerHand = h;
        }
        
        // If the card being dealt is mine, and this is during the initial deal,
        // add the card to my hand. Otherwise, it's my turn so move onto the
        // parts where I can make a play
        if(hid.getSeat() == Seat.YOU) {
            //myHand = h;
            
            if (myHand.size() < 2)
                myHand.hit(card);
            else
                myTurn = true;
        }
        
        // If the hand does not exist...this could happen if
        // player splits a hand which we don't yet know about.
        // In this case, we'll create the hand "on the fly", as it were.
        if(h == null) {
            h = new Hand(hid);
            hands.put(hid, h);
        }

        if (dealerHand != null &&
                (dealerHand.isBlackjack() || dealerHand.isBroke() ||
                 dealerHand.isCharlie()   || dealerHand.getValue() == 21)) {
            myTurn = false;
            STAYED = true;
        }
        
        if (myHand != null && 
                (myHand.isBlackjack() || myHand.isCharlie() ||
                 myHand.isBroke()     || myHand.getValue() == 21)) {
            myTurn = false;
            STAYED = true;
        }
        
        if (myTurn && !STAYED) {
            play(hid);
        }
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
        ENDGAME = true;
    }

    @Override
    public void win(Hid hid) {
        numWins++;
        STAYED = true;
        ENDGAME = true;
    }

    @Override
    public void blackjack(Hid hid) {
        numBjs++;
        STAYED = true;
        ENDGAME = true;
    }

    @Override
    public void charlie(Hid hid) {
        numCharlies++;
        STAYED = true;
        ENDGAME = true;
    }

    @Override
    public void lose(Hid hid) {
        numLoses++;
        STAYED = true;
        ENDGAME = true;
    }

    @Override
    public void push(Hid hid) {
        numPushes++;
        STAYED = true;
        ENDGAME = true;
    }

    @Override
    public void shuffling() {
        LOG.info("shuffling...");
        runningCount = 0;
        trueCount = 0;
        this.shoeSize = 1;
    }

    /**
     * Handles my turn.
     * @param hid Hand id
     */
    @Override
    public void play(Hid hid) {
        if (ENDGAME)
            return;
        
        try {
            //Delay = a random number between 1000 and 3000
            int delay = 1000 + (int)(Math.random() * ((3000 - 1000) + 1));
            Thread.sleep(delay);
            
            respond();
            
            Thread.sleep(500);
            
            myTurn = false;

        } catch (InterruptedException ex) {
            LOG.info("Thread Error: " + ex);
        } finally {
            // nothing
        }
    }
    
    /**
     * Responds when it is my turn.
     */
    protected void respond() {
        if (ENDGAME)
            return;
        
        // Sometimes this thread gets called when dealerUpCard has not been
        // instantiated yet (it could also be that dealerUpCard is the Hole
        // Card, which we cannot read anyway). So we check to make sure we
        // only start the thread when the dealerUpCard actually has a value.
        if (myHand == null || dealerUpCard == null)
            return;
        
        LOG.info("MY HAND IS "+myHand.toString());
        
        Play aPlay = adv.advise(myHand, dealerUpCard);
        
        switch(aPlay) {
            case HIT:
                courier.hit(hid);
                break;
            case DOUBLE_DOWN:
                // We cannot double down with more than 2 cards,
                // so if the BS tells us to DD, we will hit instead
                // since it's essentially the same action.
                if (myHand.size() > 2) {
                    courier.hit(hid);
                } else {
                    courier.dubble(hid);
                    Gerty.STAYED = true;
                }
                break;
            case STAY:
                courier.stay(hid);
                Gerty.STAYED = true;
                break;
            case SPLIT:
                if (myHand.getValue() >= 17) {
                    courier.stay(hid);
                    Gerty.STAYED = true;
                    
                } else if (myHand.getValue() <= 10) {
                    courier.hit(hid);
                    
                } else if (myHand.getValue() == 11) {
                    courier.dubble(hid);
                    Gerty.STAYED = true;
                    
                } else {
                    // instead of approximating with the assumption
                    // that the dealer's hole card is 10, we make
                    // the bot hit here as a way to make it less than
                    // a perfect player. So if given SPLIT, it will hit
                    // on any value for its own hand that is between
                    // 12 and 16.
                    courier.hit(hid);
                }
                break;
            default:
                LOG.info("????");
                break;
        }
        
        LOG.info("ADVISED PLAY IS "+aPlay.toString());
    }
}