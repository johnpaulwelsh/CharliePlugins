package charlie.bot.test;

import charlie.card.Shoe;
import java.util.Random;

/**
 * This class implements a test scenario with the Gerty plugin.
 * 
 * @author Katie Craven and John Paul Welsh
 */
public class Shoe01 extends Shoe {   
    @Override
    public void init() {
        super.ran = new Random(42);
        super.numDecks = 1;
        super.load();
        super.shuffle();
    }
}