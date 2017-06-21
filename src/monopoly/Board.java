package monopoly;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Board {
	
	private char[][] board;     // board 2d-array
	private int rows;           // number of rows on board
	private int cols;           // number of columns on board
	private String board_file;        // name of file the board is saved in
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
	
	
	public void update_player_position(Player pl, ArrayList<Field> fields) {
		
		int last_pos = pl.get_lastpos();
		int position = pl.get_position();
		
		Field last_field = fields.get(last_pos);
		Field new_field = fields.get(position);
		
		int[] last_coords = last_field.get_coordinates();
		int[] new_coords = new_field.get_coordinates();
		
		board[last_coords[0]][last_coords[1]] = ' ';
		board[new_coords[0]][new_coords[1]] = 'X';
		
		new_field.analyze(pl);
		
	}

	
}
