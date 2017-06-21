package monopoly;

public class Player {

	private static int count; // total number of players
	private int id;           // player ID: 1,2,...
	private String name;      // name of player
	private int money;        // money the player owns
	private int position;     // current position on board: 0,1,...,39
	private int last_pos;     // last position;
	
	
	
	public Player(String playername) {
		
		count++;
		id = count;
		name = playername;
		money = 1500;
		position = 0;
		last_pos = 0;
		
	}
	
	public static void init_players() {
		count = 0;
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
	
	public int get_lastpos() {
		return last_pos;
	}
	
	public void update_position(int pos) {
		last_pos = position;
		position = pos;
	}
	
	public void print_player() {
		System.out.println("Player "+id+" :  "+name);
		System.out.println("Position :  "+position);
		System.out.println("Money :  "+money);
	}
	
}
