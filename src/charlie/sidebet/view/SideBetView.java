/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package charlie.sidebet.view;

import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;

import charlie.view.sprite.ChipButton;
import charlie.audio.SoundFactory;
import charlie.audio.Effect;
import charlie.util.Cashier;
import charlie.view.sprite.Chip;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.awt.Image;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the side bet view.
 * 
 * @author Ron Coleman, Ph.D., Katie Craven, and John Paul Welsh
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = LoggerFactory.getLogger(SideBetView.class);
    
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    protected List<Chip> chipz = new ArrayList<>();
    Image buttonIM;
    protected Random ran = new Random();
    protected Random ranT = new Random();
    List<Image> buttonImage = new ArrayList<>();
    List<Integer> yRand = new ArrayList<>();
    
    //some colors for win and lose
    protected Color loseColorBg = new Color(250,58,5);
    protected Color loseColorFg = Color.WHITE;
    protected Color winColorFg = Color.BLACK;
    protected Color winColorBg = new Color(116,255,4);
    
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected BasicStroke stroke = new BasicStroke(3);
    
    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);   

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected AMoneyManager moneyManager;
    protected int checker = 0;

    public SideBetView() {
        LOG.info("side bet view constructed");
    }
    
    /**
     * Sets the money manager.
     * @param moneyManager 
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }
    
    /**
     * Registers a click for the side bet.
     * @param touchx X coordinate
     * @param touchy Y coordinate
     */
    @Override
    public void click(int touchx, int touchy) {
        int oldAmt = amt;
        
        // Test if any chip button has been pressed.
        for(ChipButton button: buttons) {
            if(button.isPressed(touchx, touchy)) {
                amt += button.getAmt();
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
                // PLAY SOUND
        
                buttonIM = button.getImage();
   
                buttonImage.add(buttonIM);
                int yr = ranT.nextInt((3- -3) + 1) + -3;
                yRand.add(yr);
                      
                SoundFactory.play(Effect.CHIPS_IN);
            } 
        }
        
        // Test if the sidebet value area (red dashed circle) has been pressed.
        if (touchx > (X-DIAMETER/2) && touchx < (X+DIAMETER/2) &&
            touchy > (Y-DIAMETER/2) && touchy < (Y+DIAMETER/2)) {
            if(oldAmt == amt) {
                amt = 0;
                SoundFactory.play(Effect.CHIPS_OUT);
                buttonImage.clear();
                LOG.info("B. side bet amount cleared");
            }
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();
        checker = 0;
        if(bet == 0)
            return;

        LOG.info("side bet outcome = "+bet);
        
        if(bet>1){
            checker = 1;
        }else{
            checker = -1;
        }
        
        // Update the bankroll
        moneyManager.increase(bet);
        
        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
        checker = 0;
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() {
    }

    /**
     * Renders the view
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        
        // Draw the sidebet instructions and payouts
        g.setFont(new Font("ARIAL", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        g.drawString("SUPER 7 pays 3:1", X+50, Y-28);
        g.drawString("ROYAL MATCH pays 25:1", X+50, Y-10);
        g.drawString("EXACTLY 13 pays 1:1", X+50, Y+8);
        
        // Draw the chips for the sidebet
        // Draw the at-stake place on the table
        g.setColor(Color.RED); 
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);
        
        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        
        if (amt<10){
            g.drawString(""+amt, X-5, Y+5);
        }else if(amt<100)
        {
            g.drawString(""+amt, X-10, Y+5);
        }else if (amt<1000){
            g.drawString(""+amt, X-15, Y+5);
        }else
        {
            g.drawString(""+amt, X-20, Y+5);
        }
        
        int XX = 450;
        int YY = 200;
        for(int i=0; i<buttonImage.size(); i++) {
            g.drawImage(buttonImage.get(i), XX, YY, null);
            XX = XX+10;
            YY = 200+ yRand.get(i);
        }
        
        // Draw WIN or LOSE over the sidebet chips
        if (checker ==1) {
            g.setColor(winColorBg);    
            g.fillRoundRect(X+50, Y+50, 60, 30, 5, 5);
            g.setColor(winColorFg);
            g.drawString("Win", X+63, Y+70);
            
        } else if (checker == -1) {
            g.setColor(loseColorBg);    
            g.fillRoundRect(X+50, Y+50, 60, 30, 5, 5);
            g.setColor(loseColorFg);
            g.drawString("Lose", X+59, Y+70);
        }

    }
}