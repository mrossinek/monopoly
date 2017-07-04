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

  private CardCategory category;
  private CardType type;
  private int id;
  private String instruction;

  public Card(String inst, String tp, String cat) {
    instruction = inst;
    type = CardType.valueOf(tp);
    category = CardCategory.valueOf(cat);

    switch (category) {
    case Chance:
      id = countChance;
      countChance++;
      break;
    case Quest:
      id = countQuest;
      countQuest++;
      break;
    default:
      System.err.println("Unknown card category!");
      return;
    }

  }


  public CardCategory getCategory() {
    return this.category;
  }


  public CardType getType() {
    return this.type;
  }


  public String getInstruction() {
    return this.instruction;
  }


  public void printInstruction() {
    System.out.println(instruction);
  }


  public static void setupCards(String filename, ArrayList<Card> deck, String cat) {

    try {
      FileReader inFile = new FileReader(filename);
      BufferedReader reader = new BufferedReader(inFile);

      String line;

      while ( (line = reader.readLine()) != null) {
        // cut card category label
        String tp = line.substring(0,6);
        String inst = line.substring(7,line.length());

        Card tmpCard = new Card(inst, tp, cat);
        deck.add(tmpCard);
      }

      reader.close();
      inFile.close();

    } catch (Exception e) {
      System.err.println(e);
    }

  }

  public static void initDeck(String cat) {
    CardCategory ct = CardCategory.valueOf(cat);

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
      System.err.println("Unknown card category!");
    }
  }


  public static void shuffleDeck(String cat) {
    CardCategory ct = CardCategory.valueOf(cat);

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
      System.err.println("Unknown card category!");
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


  public static void pickCard(Player pl, Game game, String cat) {
    CardCategory ct = CardCategory.valueOf(cat);
    ArrayList<Card> deck;
    int cardID = -1;

    switch (ct) {
    case Chance:
      deck = game.listOfChance;
      cardID = chanceDeck[0];
      placeCardDown(chanceDeck);
      // printArray(chanceDeck);
      break;
    case Quest:
      deck = game.listOfQuest;
      cardID = questDeck[0];
      placeCardDown(questDeck);
      // printArray(questDeck);
      break;
    default:
      System.err.println("Unknown card category!");
      return;
    }

    if (cardID == -1) {
      System.err.println("No cards in deck!");
      return;
    } else {
      Card card = deck.get(cardID);
      card.analyzeCard(pl, game);
    }
  }


  public static void placeCardDown(int[] ar) {
    int a = ar[0];

    for (int i=0; i<ar.length-1; ++i) {
      ar[i] = ar[i+1];
    }
    ar[ar.length-1] = a;
  }


  public void analyzeCard(Player pl, Game game) {
    printInstruction();

    int value = 0;

    switch (type) {

    case GET_BA:
      value = extractValue(instruction);
      pl.increaseMoney(value);
      System.out.println(pl.getName() + " now owns $" + pl.getMoney());
      break;

  	case GET_PL:
      value = extractValue(instruction);
      pl.increaseMoney(value * (Player.getCount() - 1));
      System.out.println(pl.getName() + " now owns $" + pl.getMoney());
      for (Player pl2 : game.listOfPlayers) {
        if (pl.getId() != pl2.getId()) {
          pl2.decreaseMoney(value);
          System.out.println(pl2.getName() + " now owns $" + pl2.getMoney());
        }
      }
      break;

  	case PAY_FP:
      Field parking = game.listOfFields.get(20);
      value = extractValue(instruction);
      pl.decreaseMoney(value);
      parking.updateParking(parking, value);
      System.out.println(pl.getName() + " now owns $" + pl.getMoney());
      System.out.println("Free Parking now holds $" + parking.checkParking(parking));
      break;

  	case PAY_PL:
      value = extractValue(instruction);
      pl.decreaseMoney(value * (Player.getCount() - 1));
      System.out.println(pl.getName() + " now owns $" + pl.getMoney());
      for (Player pl2 : game.listOfPlayers) {
        if (pl.getId() != pl2.getId()) {
          pl2.increaseMoney(value);
          System.out.println(pl2.getName() + " now owns $" + pl2.getMoney());
        }
      }
      break;

  	case PAY_HH:
      System.err.println("Houses and hotels have not been implemented yet.");
      break;

  	case MOVE_Y:
      //
      break;

  	case MOVE_N:
      //
      break;

  	case FREEJC:
      pl.increaseFreeJailCard();
      System.out.println(pl.getName() + " now owns " + pl.getFreeJailCard() + " get-out-of-jail cards.");
      break;
    default:
     System.err.println("Unknown card type!");
     return;
     
    }

  }

  public static int extractValue(String t) {
    return Integer.parseInt(t.replaceAll("[^0-9]", ""));
  }



}
