package monopoly;
import java.util.ArrayList;
import java.util.Scanner;

public class Field {
	
	private int id;                          // id = position on board: 0,1,...,39
	private int[] coordinates = new int[2];  // row and column coordinates
	private FieldType type;                  // type of field
	
	private String name;                     // name of field
	private StreetColor color;               // color of field (only if type street)
	
	private int value;                       // initial value of field
	private int cost;                        // cost to be paid on field
	
	private int ownerID;                    //  0 : field is for sale
	private int player_count;               // player count on field
	
	
	public Field(String[] args) {
		
		id = Integer.parseInt(args[0]);
		type = FieldType.valueOf(args[1]);
		coordinates[0] = Integer.parseInt(args[2]);
		coordinates[1] = Integer.parseInt(args[3]);
		player_count = 0;
		
		switch(type) {
		case Go:
			name = "Go";
			break;
		case Parking:
			name = "Free-Parking";
			value = 0;
			break;
		case Jail:
			name = "Jail";
			break;
		case ToJail:
			name = "Go-To-Jail";
			break;
		case Chance:
			name = "Chance";
			break;
		case Quest:
			name = "Community-Chest";
			break;
		case Tax:
			cost = Integer.parseInt(args[4]);
			name = args[5];
			break;
		case Factory:
			value = Integer.parseInt(args[4]);
			name = args[5];
			cost = 4;
			ownerID = 0;
			break;
		case Station:
			value = Integer.parseInt(args[4]);
			name = args[5];
			cost = 25;
			ownerID = 0;
			break;
		case Street:
			value = Integer.parseInt(args[4]);
			cost = Integer.parseInt(args[5]);
			color = StreetColor.valueOf(args[6]);
			name = args[7];
			ownerID = 0;
			break;
		default:
			System.err.println(type+" is not a valid field type.");
		}
		
	}
	
	public int get_id() {
		return id;
	}
	
	public int[] get_coordinates() {
		return coordinates;
	}
	
	public FieldType get_type() {
		return type;
	}
	
	public String get_name() {
		return name;
	}
	
	public StreetColor get_color() {
		return color;
	}
	
	public int get_value() {
		return value;
	}
	
	public void set_value(int val) {
		value = val;
	}
	
	public int get_cost() {
		return cost;
	}
	
	public int get_ownerID() {
		return ownerID;
	}
	
	public void set_ownerID(int id) {
		ownerID = id;
	}
	
	public int get_player_count() {
		return player_count;
	}
	
	public void increase_player_count() {
		player_count++;
	}
	
	public void decrease_player_count() {
		player_count--;
	}
	
	
	public void print_field() {
		System.out.println("Field No. "+id+" :  "+name);
	}
	
	
	public void analyze(Player pl, Board board, ArrayList<Field> fields, ArrayList<Player> players, Scanner in) {
		
		System.out.println(pl.get_name()+" landed on "+name);
		
		switch(type){
		case Go:
			go(pl);
			break;
		case Parking:
			parking(pl);
			break;
		case Jail:
			jail();
			break;
		case ToJail:
			to_jail(pl, board, fields);
			break;
		case Chance:
			pick_chance();
			break;
		case Quest:
			pick_quest();
			break;
		case Tax:
			tax(pl, fields);
			break;
		case Factory:
			factory(pl, players, fields, in);
			break;
		case Station:
			// TODO
			break;
		case Street:
			// TODO
			break;
		default:
			System.err.println("An unknown error occured during the move analysis.");
		}
		
	}
	
	public void go(Player pl) {
		pl.increase_money(400);
		System.out.println(pl.get_name()+" receives $400!");
		System.out.println(pl.get_name()+" now ownes $"+pl.get_money());
	}
	
	public void parking(Player pl) {
		pl.increase_money(value);
		System.out.println(pl.get_name()+" receives $"+value+" from "+name);
		System.out.println(pl.get_name()+" now ownes $"+pl.get_money());
		set_value(0);
	}
	
	public boolean validate_parking(Field park) {
		
		if (park.get_name() != "Free-Parking") {
			System.err.println("Free Parking appears to be assigned to the wrong fields ID.");
			return false;
		}
		return true;
	}
	
	public int check_parking(Field park) {
		
		if (validate_parking(park)) {
			return park.get_value();
		} else {
			return -1;
		}
	}
	
	public void update_parking(Field park, int diff) {
		
		if (validate_parking(park)) {
			park.set_value(park.get_value()+diff);
		}
		
	}
	
	public void jail() {
		System.out.println("... but he is only visiting.");
	}
	
	public void to_jail(Player pl, Board board, ArrayList<Field> fields) {
		pl.update_jail_count(3);
		board.remove_player(pl, fields);
		pl.set_position(10);
		board.place_player(pl, fields);
		System.out.println(name+" has been sent to the jail!");
	}
	
	public void tax(Player pl, ArrayList<Field> fields) {
		pl.decrease_money(cost);
		update_parking(fields.get(20), cost);
		System.out.println(pl.get_name()+" had to pay $"+cost);
		System.out.println(pl.get_name()+" now ownes $"+pl.get_money());
		System.out.println("Free Parking now holds $"+check_parking(fields.get(20)));
	}
	
	public void pick_chance() {
		System.out.println(name+" picked a Chance card:");
		System.err.println("Not implemented yet.");
		// TODO implement Chance cards
	}
	
	public void pick_quest() {
		System.out.println(name+" picked a Community Chest card:");
		System.err.println("Not implemented yet.");
		// TODO implement Community Chest cards
	}
	
	public void factory(Player pl, ArrayList<Player> players, ArrayList<Field> fields, Scanner in) {		
		int owner = get_ownerID();
		
		if (owner > Player.get_count()) {
			System.err.println("There appears to be an unknown owner of the field "+this.name);
			return;
		}
		
		switch (owner) {
		case 0:
			System.out.println("This Factoriy is still for sale!");
			int value = get_value();
			System.out.println("Its value is : $"+value);
			int money = pl.get_money();
			System.out.println("You currently own $"+money);
			if (money < value) {
				System.out.println("You cannot afford to buy this Factory");
				return;
			} else {
				boolean make_valid_choice = false;
				while (!make_valid_choice) {
					System.out.println("Would you like to buy this Factory? Y/N");
					char choice = in.next().charAt(0);
					switch (choice) {
					case 'y': case 'Y':
						// TODO
						make_valid_choice = true;
						break;
					case 'n': case 'N':
						//TODO
						make_valid_choice = true;
						break;
					default:
						System.err.println("Invalid choice, please try again.");
						break;
					}
				}
			}
			
			break;
		default:
			Player boss = new Player();
			for (Player p : players) {
				if (p.get_id() == owner) {
					boss = p;
					System.out.println("Owner is "+boss.get_name());
				}
			}
			
			int factory_count = 0;
			for (Field f : fields) {
				if (f.get_type() == FieldType.valueOf("Factory")) {
					if (f.get_ownerID() == boss.get_id()) {
						if (boss.check_ownage(f.get_id())) {
							factory_count++;
						} else {
							System.err.println("There appears to be a conflict of interest in owning "+f.get_name());
							return;
						}
					}
				}
			}
			
			int diff;
			
			switch (factory_count) {
			case 1:
				diff = cost*pl.dice;
				System.out.println(boss.get_name()+" owns "+factory_count+" Factory.");
				System.out.println("Thus the cost is "+cost+"*"+pl.dice+"=$"+diff);
				pl.decrease_money(diff);
				boss.increase_money(diff);
				System.out.println(pl.get_name()+" now owns $"+pl.get_money());
				System.out.println(boss.get_name()+" now owns $"+boss.get_money());
				break;
			case 2:
				diff = cost*5/2*pl.dice;
				System.out.println(boss.get_name()+" owns "+factory_count+" Factories.");
				System.out.println("Thus the cost is "+(cost*5/2)+"*"+pl.dice+"=$"+diff);
				pl.decrease_money(diff);
				boss.increase_money(diff);
				System.out.println(pl.get_name()+" now owns $"+pl.get_money());
				System.out.println(boss.get_name()+" now owns $"+boss.get_money());
				break;
			default:
				System.err.println("There appear to be more than 2 factories on the board");
				return;
			}
			
			
			
		}
	}
	

}
