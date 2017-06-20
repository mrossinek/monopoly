package monopoly;
import java.io.FileReader;
import java.io.BufferedReader;

public class Board {
	
	private char[][] board;
	private int rows;
	private int cols;
	private String file;
	
	public Board(String filename) {
		
		file = filename;
		
		try {
			FileReader inFile = new FileReader(file);
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
			
		}
		
	}
	
	
	public void setup_board() {
		
		try {
			FileReader inFile = new FileReader(file);
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
	
}
