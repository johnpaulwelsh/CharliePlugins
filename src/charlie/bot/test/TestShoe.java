package charlie.bot.test;

import charlie.card.Shoe;
import java.util.Random;

/**
 * Extension of the Shoe class from Charlie, used for testing
 * the Bot plugin.
 * @author John Paul Welsh
 */
public class TestShoe extends Shoe {
    
    @Override
    public void init() {
        ran = new Random(1);
        load();
        shuffle();
    }
}
