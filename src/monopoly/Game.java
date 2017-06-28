/*
 * Game
 *
 * 28/06/2017
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.Scanner;
import java.util.ArrayList;


public class Game {

	public boolean running;

	public Game() {
		running = true;
	}


	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage: java monopoly.Game [board file] [fields file]");
			return;
		}

		String boardFile = args[0];
		String fieldsFile = args[1];
		Scanner scanner = new Scanner(System.in);

		Game game = new Game();
		Game.welcome();

		Dice.testFairness();

		System.out.println();
		System.out.println();

		System.out.println("Setting up board...");
		Board board = new Board(boardFile);
		board.setupBoard();
		board.printBoard();

		System.out.println();

		System.out.println("Initializing fields...");
		ArrayList<Field> listOfFields = new ArrayList<Field>();
		board.setupFields(fieldsFile, listOfFields);
		board.checkFields(listOfFields);

		System.out.println();
		System.out.println();

		System.out.println("How many players are going to play?");

		int numberOfPlayers = 0;
		boolean makeValidChoice = false;

		while (!makeValidChoice) {
			numberOfPlayers = scanner.nextInt();
			if (numberOfPlayers <= 0) {
				System.out.println("Invalid choice, please try again.");
			} else {
				makeValidChoice = true;
			}
		}

		System.out.println();

		ArrayList<Player> listOfPlayers = new ArrayList<Player>();

		for (int i=1; i<=numberOfPlayers; ++i) {
			System.out.println("Please provide the name of player " + i);
			String name = scanner.next();
			Player player = new Player(name, listOfPlayers);
			board.placePlayer(player, listOfFields);
			System.out.println();
		}

		System.out.println();
		System.out.println("The following players have been initialized:");


		for (Player pl : listOfPlayers) {
			pl.printPlayer();
		}

		System.out.println();
		System.out.println();
		System.out.println();

		System.out.println("Let's start!");
		scanner.nextLine();

		System.out.println();

		while (game.running) {

			for (Player pl : listOfPlayers) {

				System.out.println();
				System.out.println(pl.getName() + "'s turn");

				int paschCount = 0;
				boolean pasch = false;

				do {
					pasch = pl.throwDice(scanner);

					if (pasch) {
						paschCount++;

						if (paschCount == 3) {
							System.out.println(pl.getName() + " threw 3 Pasch in a row!");
							pl.sendToJail(board, listOfFields);
							break;
						}
					}
					pl.doTurn(board, listOfFields, listOfPlayers, scanner);
				} while (pasch);

				if (pl.getMoney() < 0) {
					System.out.println(pl.getName() + " went bankrupt!");
					game.running = false;
				} else {
					System.out.println("Press ENTER to end your turn");
					scanner.nextLine();
				}

			}

		}


	}


	public static void welcome() {
		System.out.println();
		System.out.println("Welcome to ");
		System.out.println();
		System.out.println("  M O N O P O L Y  ");
		System.out.println();
		System.out.println();
	}


}
