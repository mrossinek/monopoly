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
		jail_count = 0;
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
	
	public void set_position(int pos) {
		position = pos;
	}
	
	public void update_position(int num) {
		int new_pos = (position + num) % 40;
		if (new_pos < position && new_pos != 0) {  // player crossed Go
			increase_money(200);
			System.out.println(name+" crossed Go and received $200!");
			System.out.println(name+" now ownes $"+money);
		}
		position = new_pos;
	}
	
	public void update_jail_count(int diff) {
		jail_count += diff;
	}
	
	public boolean check_ownage(int fieldID) {
		return streets[fieldID];
	}
	
	public void print_player() {
		System.out.println();
		System.out.println("Player "+id+" :  "+name);
		System.out.println("Position :  "+position);
		System.out.println("Money    :  $"+money);
	}
	
	public void do_turn(Board board, ArrayList<Field> fields) {
		System.out.println();
		System.out.println(name+"'s turn");
		
		if (jail_count != 0) {
			update_jail_count(-1);
			if (jail_count == 0) {
				System.out.println(name+" has been released from jail.");
				return;
			}
			System.out.println(name+" is in jail!");
			System.out.println("Turns left in jail: "+jail_count);
			return;
		}
		
		int num = Dice.throw_dice(2);
		System.out.println(name+" threw a "+num);
		
		board.remove_player(this, fields);
		update_position(num);
		board.place_player(this, fields);
		
		Field field = fields.get(position);
		field.analyze(this, board, fields);
		
		
	}
	
	public void send_to_jail(Board board, ArrayList<Field> fields) {
		update_jail_count(3);
		board.remove_player(this, fields);
		set_position(10);
		board.place_player(this, fields);
		System.out.println(name+" has been sent to the jail!");
	}
	
}
