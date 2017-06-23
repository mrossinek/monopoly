package monopoly;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		
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
		
		Board board = new Board("board.txt");
		board.setup_board();
		board.print_board();
		
		System.out.println();
		
		ArrayList<Field> ListOfFields = new ArrayList<Field>();
		board.setup_fields("fields.txt", ListOfFields);
		board.check_fields(ListOfFields);
		
		System.out.println();
		
		board.place_player(max, ListOfFields);
		board.place_player(flo, ListOfFields);
		
		// int turns = 10;
		
		//while (turns>0) {
		//	max.do_turn(board, ListOfFields, ListOfPlayers, scanner);
		//	board.print_board();
		//	flo.do_turn(board, ListOfFields, ListOfPlayers, scanner);
		//	board.print_board();
		//	turns--;
		//}
		
		max.dice = 12;
		// flo.buy(12, ListOfFields);
		flo.buy(28, ListOfFields);
		max.set_position(12);
		ListOfFields.get(12).analyze(max, board, ListOfFields, ListOfPlayers, scanner);
		
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
