/*
 * Cards
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;


public class Card {

  public static int countChance;  // public counter of chance cards
  public static int countQuest;   // public counter of community chest cards
  public static int[] chanceDeck; // public deck of chance cards
  public static int[] questDeck;  // public deck of community chest cards

  private CardType type;
  private int id;
  private String instruction;

  public Card(String inst, String t) {
    instruction = inst;
    type = CardType.valueOf(t);

    switch (type) {
    case Chance:
      id = countChance;
      countChance++;
      break;
    case Quest:
      id = countQuest;
      countQuest++;
      break;
    default:
      System.err.println("Unknown card type!");
      return;
    }

  }


  public CardType getType() {
    return this.type;
  }


  public void getInstruction() {
    System.out.println(instruction);
  }


  public static void setupCards(String filename, ArrayList<Card> deck, String type) {

    try {
      FileReader inFile = new FileReader(filename);
      BufferedReader reader = new BufferedReader(inFile);

      String line;

      while ( (line = reader.readLine()) != null) {
        Card tmpCard = new Card(line, type);
        deck.add(tmpCard);
      }

      reader.close();
      inFile.close();

    } catch (Exception e) {
      System.err.println(e);
    }

  }

  public static void initDeck(String t) {
    CardType ct = CardType.valueOf(t);

    switch (ct) {
    case Chance:
      chanceDeck = new int[countChance];
      for (int i=0; i<countChance; ++i) {
        chanceDeck[i] = i;
      }
      // printArray(chanceDeck);
      break;
    case Quest:
      questDeck = new int[countQuest];
      for (int i=0; i<countQuest; ++i) {
        questDeck[i] = i;
      }
      // printArray(questDeck);
      break;
    default:
      System.err.println("Unknown card type!");
    }
  }


  public static void shuffleDeck(String t) {
    CardType ct = CardType.valueOf(t);

    switch (ct) {
    case Chance:
      shuffleArray(chanceDeck);
      // printArray(chanceDeck);
      break;
    case Quest:
      shuffleArray(questDeck);
      // printArray(questDeck);
      break;
    default:
      System.err.println("Unknown card type!");
    }
  }


  public static void shuffleArray(int[] ar) {
    // Fisher-Yates shuffle
    for (int i=ar.length-1; i>0; --i) {
      int j = (int) (Math.random() * ar.length);
      // simply swap positions i and j
      int a = ar[j];
      ar[j] = ar[i];
      ar[i] = a;
    }
  }


  public static void printArray(int[] ar) {
    for (int i=0; i<ar.length; ++i) {
      System.out.print(" " + ar[i]);
    }
    System.out.println();
  }


  public static void pickCard(ArrayList<Card> deck, String t) {
    CardType ct = CardType.valueOf(t);
    int cardID = -1;

    switch (ct) {
    case Chance:
      cardID = chanceDeck[0];
      placeCardDown(chanceDeck);
      // printArray(chanceDeck);
      break;
    case Quest:
      cardID = questDeck[0];
      placeCardDown(questDeck);
      // printArray(questDeck);
      break;
    default:
      System.err.println("Unknown card type!");
      return;
    }

    if (cardID == -1) {
      System.err.println("No cards in deck!");
      return;
    } else {
      deck.get(cardID).getInstruction();
    }
  }


  public static void placeCardDown(int[] ar) {
    int a = ar[0];

    for (int i=0; i<ar.length-1; ++i) {
      ar[i] = ar[i+1];
    }
    ar[ar.length-1] = a;
  }



}
