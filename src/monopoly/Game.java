package monopoly;
import java.util.ArrayList;

public class Game {

	public static void main(String[] args) {
		
		Game.welcome();
		
		Dice.test_fairness();
		
		Player max = new Player("Max");
		Player flo = new Player("Flo");
		
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
		
		int turns = 10;
		
		while (turns>0) {
			max.do_turn(board, ListOfFields);
			board.print_board();
			flo.do_turn(board, ListOfFields);
			board.print_board();
			turns--;
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
