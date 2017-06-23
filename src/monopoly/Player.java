package monopoly;
import java.util.ArrayList;

public class Player {

	private static int count;  // total number of players
	private int id;            // player ID: 1,2,...
	private String name;       // name of player
	private int money;         // money the player owns
	private int position;      // current position on board: 0,1,...,39
	private int jail_count;    // counter for jail time
	private boolean[] streets; // fields owned by player
	
	
	public Player(String playername) {
		
		count++;
		id = count;
		name = playername;
		money = 1500;
		position = 0;
		streets = new boolean[40];
		
	}
	
	public String get_name() {
		return name;
	}
	
	public int get_money() {
		return money;
	}
	
	public void increase_money(int diff) {
		money += diff;
	}
	
	public void decrease_money(int diff) {
		money -= diff;
	}
	
	public int get_position() {
		return position;
	}
	
	public void update_position(int num) {
		position = (position + num) % 40;
	}
	
	public boolean check_ownage(int fieldID) {
		return streets[fieldID];
	}
	
	public void print_player() {
		System.out.println("Player "+id+" :  "+name);
		System.out.println("Position :  "+position);
		System.out.println("Money :  "+money);
	}
	
	public void do_turn(Board board, ArrayList<Field> fields) {
		int num = Dice.throw_dice(2);
		board.remove_player(this, fields);
		update_position(num);
		board.place_player(this, fields);
	}
	
	public void send_to_jail() {
		position = 10;
		jail_count = 3;
	}
	
	public int check_jailtime() {
		return --jail_count;
	}
	
}
