package monopoly;
import java.util.ArrayList;
import java.util.ListIterator;

public class Game {

	public static void main(String[] args) {
		
		welcome();
		
		Dice.test_fairness();
		
		Player max = new Player("Max");
		Player flo = new Player("Flo");
		
		System.out.println();
		System.out.println();
		
		max.print_player();
		flo.print_player();
		
		System.out.println();
		System.out.println();
		
		Board board = new Board("board.txt");
		board.setup_board();
		board.print_board();
		
		System.out.println();
		System.out.println();
		
		ArrayList<Field> ListOfFields = new ArrayList<Field>();
		board.setup_fields("fields.txt", ListOfFields);
		
		ListIterator<Field> it = ListOfFields.listIterator();
		
		while (it.hasNext()) {
			Field field = it.next();
			field.print_field();
		}
		
		board.update_player_position(max, ListOfFields);

		
	}
	
	
	public static void welcome() {
		
		System.out.println();
		System.out.println("Welcome to ");
		System.out.println();
		System.out.println("  M O N O P O L Y  ");
		System.out.println();
		System.out.println();
		System.out.println();
		
	}

}
