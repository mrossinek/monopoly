/*
 * Game
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;


public class Game {

	public boolean running;
	public Language language;
	public Board board;
	public ArrayList<Field> listOfFields;
	public ArrayList<Card> listOfChance;
	public ArrayList<Card> listOfQuest;
	public ArrayList<Player> listOfPlayers;

	public String boardFile;
	public String fieldsFile;
	public String chanceFile;
	public String questFile;


	public Game(String filename, Scanner in) {

		running = true;

		try {
			FileReader paramFile = new FileReader(filename);
			BufferedReader reader = new BufferedReader(paramFile);

			reader.readLine();  // discard comment line

			language = Language.valueOf(reader.readLine());

			boardFile = reader.readLine();
			fieldsFile = reader.readLine();
			chanceFile = reader.readLine();
			questFile = reader.readLine();

			reader.close();
			paramFile.close();

		} catch (Exception e) {
			System.err.println(e);
		}

		Dice.testFairness();

    System.out.println();
    System.out.println();

    System.out.println("Setting up board...");
    board = new Board(boardFile);
    board.setupBoard();
    board.printBoard();

    System.out.println();

    System.out.println("Initializing fields...");
    listOfFields = new ArrayList<Field>();
    Field.setupFields(fieldsFile, listOfFields);
    Field.checkFields(listOfFields);

    System.out.println();

    System.out.println("Loading chance cards...");
    listOfChance = new ArrayList<Card>();
    Card.setupCards(chanceFile, listOfChance, "Chance");
    System.out.println("Shuffling chance cards...");
    Card.initDeck("Chance");
    Card.shuffleDeck("Chance");

    System.out.println("Loading community chest cards...");
    listOfQuest = new ArrayList<Card>();
    Card.setupCards(questFile, listOfQuest, "Quest");
    System.out.println("Shuffling community chest cards...");
    Card.initDeck("Quest");
    Card.shuffleDeck("Quest");

    System.out.println();
    System.out.println();

    System.out.println("How many players are going to play?");

    int numberOfPlayers = 0;
    boolean makeValidChoice = false;

    while (!makeValidChoice) {
      numberOfPlayers = in.nextInt();
      if (numberOfPlayers <= 0) {
        System.out.println("Invalid choice, please try again.");
      } else {
        makeValidChoice = true;
      }
    }

    System.out.println();

    listOfPlayers = new ArrayList<Player>();

    for (int i=1; i<=numberOfPlayers; ++i) {
      System.out.println("Please provide the name of player " + i);
      String name = in.next();
      Player player = new Player(name, listOfPlayers);
      board.placePlayer(player, listOfFields);
      System.out.println();
    }

    System.out.println();
    System.out.println();
    System.out.println();

	}


}
