/* Courtney Hunt JAVA 1B Assignment 8 Part 1 Foothill College
 * 
 * This program adds 10 randomly chosen cards to a LinkedList collection via the
 * insert() method, ordering them as is dictated in the Card class. It also
 * adds a duplicate of the same card at the same time. It then removes 5 of
 * the cards via the remove() method, also removing any duplicates, which may
 * be more than just 2 given the randomness of the generator method. Then, it
 * utilizes the removeAll() method to clear the list with a message should
 * the method return false, letting the client know the card wasn't listed.
 */

import java.util.*;

public class Foothill
{
   public static void main(String[] args) throws Exception
   {

      // create a LinkedList Collection & its Iterator
      LinkedList<Card> myList = new LinkedList<Card>();
      ListIterator<Card> iter;

      // temporary Card
      Card tempCard;

      // insert 10 cards, each card added twice to the list
      for (int i = 0; i < 10; i++)
      {
         tempCard = generateRandomCard();
         insert(myList, tempCard);
         insert(myList, tempCard);
      }

      // show initial card list
      createLine(80, "-");
      System.out.println("Inserted Cards:");
      createLine(80, "-");
      for (iter = myList.listIterator(); iter.hasNext();)
         System.out.println(iter.next().toString());

      // remove half the cards (5)
      for (int i = 0; i < 5; i++)
      {
         tempCard = myList.peekFirst();

         int k = 0;
         for (boolean bool = true; bool == true; k++)
         {
            bool = remove(myList, tempCard);
         }
         createLine(80, "-");
         System.out.println("remove(" + tempCard.toString()
         + ") " + (k - 1) + " times!");
         createLine(80, "-");
         for (iter = myList.listIterator(); iter.hasNext();)
            System.out.println(iter.next().toString());
      }

      // removeAll() remaining cards in list
      for (int i = 0; i < 5; i++)
      {
         tempCard = myList.peekFirst();

         if (removeAll(myList, tempCard))
         {
            createLine(80, "-");
            System.out.println("removeAll(" + tempCard.toString() + "):");
            createLine(80, "-");
            for (iter = myList.listIterator(); iter.hasNext();)
               System.out.println(iter.next().toString());
         }
         else if (!removeAll(myList, tempCard) && tempCard != null)
            System.out.println(tempCard + " was not in the list.");
      }
   }

   // Global method definitions --------------------------
   static void insert(LinkedList<Card> myList, Card x)
   {
      ListIterator<Card> iter;
      Card tempCard;

      for (iter = myList.listIterator(); iter.hasNext(); )
      {
         tempCard = iter.next();
         if (x.compareTo(tempCard) <= 0)
         {
            iter.previous(); // back up one
            break;
         }
      }
      iter.add(x);
   }

   static boolean remove(LinkedList<Card> myList, Card x)
   {
      ListIterator<Card> iter;

      for (iter = myList.listIterator(); iter.hasNext(); )
      {
         if (iter.next().compareTo(x) == 0)
         {
            iter.remove();
            return true;   // we found, we removed, we return
         }
      }
      return false;
   }

   static boolean removeAll(LinkedList<Card> myList, Card x)
   {
      ListIterator<Card> iter;
      boolean bool = false;

      for (iter = myList.listIterator(); iter.hasNext();)
         if (iter.next().compareTo(x) == 0)
         {
            iter.remove();
            bool = true;
         }

      return bool;
   }

   static Card generateRandomCard()
   {
      Card.Suit suit;
      char val;

      int suitSelector, valSelector;

      // get random suit and value
      suitSelector = (int) (Math.random() * 4);
      valSelector = (int) (Math.random() * 13);

      // pick suit
      suit = Card.Suit.values()[suitSelector];

      // pick value
      valSelector++;   // put in range 1-14
      switch(valSelector)
      {
      case 1:
         val = 'A';
         break;
      case 10:
         val = 'T';
         break;
      case 11:
         val = 'J';
         break;
      case 12:
         val = 'Q';
         break;
      case 13:
         val = 'K';
         break;
      case 14:
         val = 'X';
         break;
      default:
         val = (char)('0' + valSelector);   // simple way to turn n into 'n'   
      }

      return new Card(val, suit);
   }

   // helps beautify the console output
   private static void createLine(int length, String shape)
   {
      for (int counter = 0; counter < length; counter += 1)
      {
         System.out.print(shape);
      }
   }
}

//Card class -------------------------------------------------------------------
class Card implements Comparable<Card>
{   
   // type and constants
   public enum Suit { clubs, diamonds, hearts, spades }

   // for ordering
   public static char[] valueRanks = { '2', '3', '4', '5', '6', '7', '8', '9', 
         'T', 'J', 'Q', 'K', 'A'};
   static Suit[] suitRanks = {Suit.clubs, Suit.diamonds, Suit.hearts, 
         Suit.spades};
   static int numValsInOrderingArray = 13;

   // private data
   private char value;
   private Suit suit;
   boolean errorFlag;

   // 4 overloaded constructors
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   public Card(char value)
   {
      this(value, Suit.spades);
   }
   public Card()
   {
      this('A', Suit.spades);
   }
   // copy constructor
   public Card(Card card)
   {
      this(card.value, card.suit);
   }

   // mutators
   public boolean set(char value, Suit suit)
   {
      char upVal;            // for upcasing char

      // convert to uppercase to simplify
      upVal = Character.toUpperCase(value);

      if ( !isValid(upVal, suit))
      {
         errorFlag = true;
         return false;
      }

      // else implied
      errorFlag = false;
      this.value = upVal;
      this.suit = suit;
      return true;
   }

   // accessors
   public char getVal()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   public boolean equals(Card card)
   {
      if (this.value != card.value)
         return false;
      if (this.suit != card.suit)
         return false;
      if (this.errorFlag != card.errorFlag)
         return false;
      return true;
   }

   // stringizer
   public String toString()
   {
      String retVal;

      if (errorFlag)
         return "** illegal **";

      // else implied

      retVal =  String.valueOf(value);
      retVal += " of ";
      retVal += String.valueOf(suit);

      return retVal;
   }

   // helper
   private boolean isValid(char value, Suit suit)
   {
      char upVal;

      // convert to uppercase to simplify (need #include <cctype>)
      upVal = Character.toUpperCase(value);

      // check for validity
      if (
            upVal == 'A' || upVal == 'K'
            || upVal == 'Q' || upVal == 'J'
            || upVal == 'T' 
            || (upVal >= '2' && upVal <= '9')
            )
         return true;
      else
         return false;
   }

   // sort member methods
   public int compareTo(Card otherCard)
   {
      if (this.value == otherCard.value)
         return ( getSuitRank(this.suit) - getSuitRank(otherCard.suit) );

      return ( 
            getValueRank(this.value) 
            - getValueRank(otherCard.value) 
            );
   }

   //helpers for compareTo()
   public static void setRankingOrder(char[] valueOrder, Suit[] suitOrder,
         int numValsInOrderingArray)
   {
      int k;

      if (numValsInOrderingArray < 0 || numValsInOrderingArray > 13)
         return;

      Card.numValsInOrderingArray = numValsInOrderingArray;

      for (k = 0; k < numValsInOrderingArray; k++)
         Card.valueRanks[k] = valueOrder[k];

      for (k = 0; k < 4; k++)
         Card.suitRanks[k] = suitOrder[k];
   }

   public static int getSuitRank(Suit st)
   {
      int k;

      for (k = 0; k < 4; k++) 
         if (suitRanks[k] == st)
            return k;

      // should not happen
      return 0;
   }

   public  static int getValueRank(char val)
   {
      int k;

      for (k = 0; k < numValsInOrderingArray; k++) 
         if (valueRanks[k] == val)
            return k;

      // should not happen
      return 0;
   }
}

/* Sample Run-------------------------------------------------------------------
--------------------------------------------------------------------------------
Inserted Cards:
--------------------------------------------------------------------------------
2 of clubs
2 of clubs
3 of clubs
3 of clubs
5 of spades
5 of spades
8 of diamonds
8 of diamonds
T of diamonds
T of diamonds
T of diamonds
T of diamonds
T of spades
T of spades
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
remove(2 of clubs) 2 times!
--------------------------------------------------------------------------------
3 of clubs
3 of clubs
5 of spades
5 of spades
8 of diamonds
8 of diamonds
T of diamonds
T of diamonds
T of diamonds
T of diamonds
T of spades
T of spades
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
remove(3 of clubs) 2 times!
--------------------------------------------------------------------------------
5 of spades
5 of spades
8 of diamonds
8 of diamonds
T of diamonds
T of diamonds
T of diamonds
T of diamonds
T of spades
T of spades
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
remove(5 of spades) 2 times!
--------------------------------------------------------------------------------
8 of diamonds
8 of diamonds
T of diamonds
T of diamonds
T of diamonds
T of diamonds
T of spades
T of spades
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
remove(8 of diamonds) 2 times!
--------------------------------------------------------------------------------
T of diamonds
T of diamonds
T of diamonds
T of diamonds
T of spades
T of spades
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
remove(T of diamonds) 4 times!
--------------------------------------------------------------------------------
T of spades
T of spades
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
removeAll(T of spades):
--------------------------------------------------------------------------------
J of clubs
J of clubs
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
removeAll(J of clubs):
--------------------------------------------------------------------------------
Q of clubs
Q of clubs
K of spades
K of spades
--------------------------------------------------------------------------------
removeAll(Q of clubs):
--------------------------------------------------------------------------------
K of spades
K of spades
--------------------------------------------------------------------------------
removeAll(K of spades):
--------------------------------------------------------------------------------
------------------------------------------------------------------------------*/
