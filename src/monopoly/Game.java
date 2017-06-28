/*
 * Game
 *
 * 28/06/2017
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.ArrayList;
import java.util.Scanner;


public class Game {


	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage: java monopoly.Game [board file] [fields file]");
			return;
		}

		String boardFile = args[0];
		String fieldsFile = args[1];
		Scanner scanner = new Scanner(System.in);


		Game.welcome();

		Dice.testFairness();


		ArrayList<Player> listOfPlayers = new ArrayList<Player>();
		Player max = new Player("Max", listOfPlayers);
		Player flo = new Player("Flo", listOfPlayers);

		System.out.println();

		max.printPlayer();
		flo.printPlayer();

		System.out.println();


		Board board = new Board(boardFile);
		board.setupBoard();
		board.printBoard();

		System.out.println();


		ArrayList<Field> listOfFields = new ArrayList<Field>();
		board.setupFields(fieldsFile, listOfFields);
		board.checkFields(listOfFields);

		System.out.println();


		board.placePlayer(max, listOfFields);
		board.placePlayer(flo, listOfFields);


		while (true) {
			flo.doTurn(board, listOfFields, listOfPlayers, scanner);
			max.doTurn(board, listOfFields, listOfPlayers, scanner);
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
