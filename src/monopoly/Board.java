package monopoly;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.ListIterator;

public class Board {

	private char[][] board;     // board 2d-array
	private int rows;           // number of rows on board
	private int cols;           // number of columns on board
	private String board_file;  // name of file the board is saved in
	private String fields_file; // name of file the fields are saved in


	public Board(String filename) {

		board_file = filename;

		try {
			FileReader inFile = new FileReader(board_file);
			BufferedReader reader = new BufferedReader(inFile);

			char[] tmp = reader.readLine().toCharArray();
			cols = tmp.length;

			rows=1;

			while (reader.readLine() != null) {
				rows++;
			}

			reader.close();
			inFile.close();


			board = new char[rows][cols];


		} catch (Exception e) {
			System.err.println(e);
		}

	}


	public void setup_board() {

		try {
			FileReader inFile = new FileReader(board_file);
			BufferedReader reader = new BufferedReader(inFile);

			int i=0;

			while (i<rows) {
				char[] tmp = reader.readLine().toCharArray();
				for (int j=0; j<cols; ++j) {
					board[i][j] = tmp[j];
				}
				i++;
			}

			reader.close();
			inFile.close();

		} catch (Exception e) {
			System.err.println(e);
		}

	}


	public void print_board() {
		System.out.println();

		for (int m=0; m<rows; ++m) {
			for (int n=0; n<cols; ++n) {
				System.out.print(board[m][n]);
			}
			System.out.println();
		}

	}


	public void setup_fields(String filename, ArrayList<Field> ListOfFields) {

		fields_file = filename;

		try {
			FileReader inFile = new FileReader(fields_file);
			BufferedReader reader = new BufferedReader(inFile);

			String line;

			while ( (line = reader.readLine()) != null) {

				String[] splitLine = line.split("\\s+");

				Field tmpField = new Field(splitLine);
				ListOfFields.add(tmpField);

			}
			reader.close();
			inFile.close();

		} catch (Exception e) {
			System.err.println(e);
		}

	}


	public void check_fields(ArrayList<Field> fields) {
		System.out.println();
		ListIterator<Field> it = fields.listIterator();

		while (it.hasNext()) {
			Field field = it.next();
			field.print_field();
		}
	}

	public void place_player(Player pl, ArrayList<Field> fields) {
		int pos = pl.get_position();
		Field field = fields.get(pos);
		int[] coords = field.get_coordinates();
		int count = field.get_player_count();
		switch (count) {
		case 0:
			board[coords[0]][coords[1]] = pl.get_name().charAt(0);
			break;
		default:
			board[coords[0]][coords[1]] = 'X';
		}
		field.increase_player_count();
		// System.out.println("Placing player "+pl.get_name()+" on field "+field.get_name()+"  (#players: "+field.get_player_count()+")");
	}


	public void remove_player(Player pl, ArrayList<Field> fields) {
		int pos = pl.get_position();
		Field field = fields.get(pos);
		int[] coords = field.get_coordinates();
		int count = field.get_player_count();
		switch (count) {
		case 0:
			System.err.println("There appears to be no player on this field:");
			return;
		case 1:
			board[coords[0]][coords[1]] = ' ';
			field.decrease_player_count();
			break;
		default:
			field.decrease_player_count();
		}
		// System.out.println("Removing player "+pl.get_name()+" from field "+field.get_name()+"  (#players: "+field.get_player_count()+")");
	}


}
