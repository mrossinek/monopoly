package monopoly;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {

		if (args.length != 2) {
			System.err.println("Usage: java monopoly.Game [board file] [fields file]");
			return;
		}

		Scanner scanner = new Scanner(System.in);

		Game.welcome();

		Dice.test_fairness();

		ArrayList<Player> ListOfPlayers = new ArrayList<Player>();
		Player max = new Player("Max", ListOfPlayers);
		Player flo = new Player("Flo", ListOfPlayers);

		System.out.println();

		max.print_player();
		flo.print_player();

		System.out.println();


		String board_file = args[0];
		Board board = new Board(board_file);
		board.setup_board();
		board.print_board();

		System.out.println();

		String fields_file = args[1];
		ArrayList<Field> ListOfFields = new ArrayList<Field>();
		board.setup_fields(fields_file, ListOfFields);
		board.check_fields(ListOfFields);

		System.out.println();

		board.place_player(max, ListOfFields);
		board.place_player(flo, ListOfFields);

		while (true) {
			flo.do_turn(board, ListOfFields, ListOfPlayers, scanner);
			max.do_turn(board, ListOfFields, ListOfPlayers, scanner);
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
