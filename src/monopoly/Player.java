package monopoly;

public class Player {

	private static int count; // total number of players
	private int id;           // player ID: 1,2,...
	private String name;      // name of player
	private int money;        // money the player owns
	private int position;     // current position on board: 0,1,...,39
	private int last_pos;     // last position;
	private int jail_count;   // counter for jail time
	
	
	
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
	
	public void update_position(int num) {
		last_pos = position;
		position = (position + num) % 40;
	}
	
	public void print_player() {
		System.out.println("Player "+id+" :  "+name);
		System.out.println("Position :  "+position);
		System.out.println("Money :  "+money);
	}
	
	public void do_turn() {
		
		int num = Dice.throw_dice(2);
		update_position(num);
		
	}
	
	public void send_to_jail() {
		
		last_pos = position;
		position = 10;
		jail_count = 3;
		
	}
	
}
