package monopoly;

public class Game {

	public static void main(String[] args) {
		
		// Dice.test_fairness();
		
		Board board = new Board("board.txt");
		board.setup_board();
		board.print_board();
		
	}

}
