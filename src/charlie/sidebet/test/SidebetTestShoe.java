package charlie.sidebet.test;

/**
 * A test shoe for the Sidebet.
 * 
 * @author John Paul Welsh
 */
public class SidebetTestShoe extends charlie.card.Shoe {
    
    @Override
    public void init() {
        cards.clear();
        
        // Test case #1 - 7 K 9 9 3
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(9,Card.Suit.SPADES));
        cards.add(new Card(9,Card.Suit.HEARTS));
        
        cards.add(new Card(3,Card.Suit.CLUBS));
        

        // Test case #2 - 7 K 9 8 3
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(9,Card.Suit.SPADES));
        cards.add(new Card(8,Card.Suit.DIAMONDS)); 
        
        cards.add(new Card(3,Card.Suit.CLUBS));
        
        // Test case #3 - 9 K 7 8 3
        cards.add(new Card(9,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(7,Card.Suit.SPADES));
        cards.add(new Card(8,Card.Suit.DIAMONDS)); 
        
        cards.add(new Card(3,Card.Suit.CLUBS));
        
        // Test case #4 - 7 K 9 10 3
        cards.add(new Card(7,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(9,Card.Suit.SPADES));
        cards.add(new Card(10,Card.Suit.DIAMONDS)); 
        
        cards.add(new Card(3,Card.Suit.CLUBS));
        
        // Test case #5 - 9 K 7 10 3
        cards.add(new Card(9,Card.Suit.HEARTS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(7,Card.Suit.SPADES));
        cards.add(new Card(10,Card.Suit.DIAMONDS)); 
        
        cards.add(new Card(3,Card.Suit.CLUBS));
        
        // Test case #6 - K(suit) K Q(suit) 8 
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(Card.KING,Card.Suit.HEARTS));
        
        cards.add(new Card(Card.QUEEN,Card.Suit.CLUBS));
        cards.add(new Card(8,Card.Suit.HEARTS));
        
        // Test case #7 - K(unsuit) K Q(unsuit) 8 
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        cards.add(new Card(Card.KING,Card.Suit.HEARTS));
        
        cards.add(new Card(Card.QUEEN,Card.Suit.DIAMONDS));
        cards.add(new Card(8,Card.Suit.HEARTS));
        
        // Test case #8 - 8 K 5 6 K
        cards.add(new Card(8,Card.Suit.CLUBS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(5,Card.Suit.CLUBS));
        cards.add(new Card(6,Card.Suit.CLUBS));
        
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS));
        
        // Test case #8 - 7 K 6 6 K
        cards.add(new Card(7,Card.Suit.CLUBS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(6,Card.Suit.SPADES));
        cards.add(new Card(6,Card.Suit.CLUBS));
        
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS));
        
        // Test case #8 - 6 K 7 6 K
        cards.add(new Card(6,Card.Suit.CLUBS));
        cards.add(new Card(Card.KING,Card.Suit.CLUBS));
        
        cards.add(new Card(7,Card.Suit.CLUBS));
        cards.add(new Card(6,Card.Suit.DIAMONDS));
        
        cards.add(new Card(Card.KING,Card.Suit.DIAMONDS));
    }
    
    @Override
    public boolean shuffleNeeded() {
        return false;
    }
}
