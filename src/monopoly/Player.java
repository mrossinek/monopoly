package monopoly;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private static int count;  // total number of players
	private int id;            // player ID: 1,2,...
	private String name;       // name of player
	private int money;         // money the player owns
	private int position;      // current position on board: 0,1,...,39
	private int jail_count;    // counter for jail time
	private boolean[] streets; // fields owned by player
	public int dice;


	public Player() {
		// dummy player
	}

	public Player(String playername, ArrayList<Player> players) {

		count++;
		id = count;
		name = playername;
		money = 1500;
		position = 0;
		jail_count = 0;
		streets = new boolean[40];

		players.add(this);

	}

	public static int get_count() {
		return count;
	}

	public int get_id() {
		return id;
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

	public void update_position() {
		int new_pos = (position + dice) % 40;
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

	public void buy(Field field, ArrayList<Field> fields) {
		int cost = field.get_value();
		if (money-cost < 0) {
			System.err.println(get_name()+" cannot afford to buy "+field.get_name());
			return;
		} else {
			decrease_money(cost);
		}

		int fieldID = field.get_id();
		streets[fieldID] = true;

		for (Field f : fields) {
			if (f.get_id() == fieldID) {
				f.set_ownerID(id);
			}
		}
	}

	public void print_player() {
		System.out.println();
		System.out.println("Player "+id+" :  "+name);
		System.out.println("Position :  "+position);
		System.out.println("Money    :  $"+money);
	}



	public void do_turn(Board board, ArrayList<Field> fields, ArrayList<Player> players, Scanner in) {

		System.out.println();
		System.out.println(name+"'s turn");

		System.out.println("Press ENTER to roll the dices");
		in.nextLine();

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

		dice = Dice.throw_dice(2);
		System.out.println(name+" threw a "+dice);

		board.remove_player(this, fields);
		update_position();
		board.place_player(this, fields);
		board.print_board();

		fields.get(position).analyze(this, board, fields, players, in);
		in.nextLine();

		System.out.println("Press ENTER to end your turn");
		in.nextLine();

	}

}
