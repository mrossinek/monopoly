/*
 * Board
 *
 * 28/06/2017
 *
 * Max Rossmannek
 */

package monopoly;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;


public class Board {


	private char[][] board;     // board 2d-array
	private int rows;           // number of rows on board
	private int cols;           // number of columns on board
	private String boardFile;   // name of file the board is saved in
	private String fieldsFile;  // name of file the fields are saved in


	public Board(String filename) {

		boardFile = filename;

		try {
			FileReader inFile = new FileReader(boardFile);
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


	public void setupBoard() {

		try {
			FileReader inFile = new FileReader(boardFile);
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


	public void printBoard() {
		System.out.println();

		for (int m=0; m<rows; ++m) {
			for (int n=0; n<cols; ++n) {
				System.out.print(board[m][n]);
			}
			System.out.println();
		}

	}


	public void setupFields(String filename, ArrayList<Field> ListOfFields) {

		fieldsFile = filename;

		try {
			FileReader inFile = new FileReader(fieldsFile);
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


	public void checkFields(ArrayList<Field> fields) {
		System.out.println();
		for (Field f : fields) {
			f.printField();
		}
	}

	public void placePlayer(Player pl, ArrayList<Field> fields) {
		int pos = pl.getPosition();
		Field field = fields.get(pos);
		int[] coords = field.getCoordinates();
		int count = field.getPlayerCount();

		switch (count) {
		case 0:
			board[coords[0]][coords[1]] = pl.getName().charAt(0);
			break;
		default:
			board[coords[0]][coords[1]] = 'X';
		}

		field.increasePlayerCount();

		// System.out.println("Placing player "+pl.get_name()+" on field "+field.get_name()+"  (#players: "+field.get_player_count()+")");
	}


	public void removePlayer(Player pl, ArrayList<Field> fields) {
		int pos = pl.getPosition();
		Field field = fields.get(pos);
		int[] coords = field.getCoordinates();
		int count = field.getPlayerCount();

		switch (count) {
		case 0:
			System.err.println("There appears to be no player on this field:");
			return;
		case 1:
			board[coords[0]][coords[1]] = ' ';
			field.decreasePlayerCount();
			break;
		default:
			field.decreasePlayerCount();
		}

		// System.out.println("Removing player "+pl.get_name()+" from field "+field.get_name()+"  (#players: "+field.get_player_count()+")");
	}


}
