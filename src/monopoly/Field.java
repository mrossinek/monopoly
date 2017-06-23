package monopoly;
import java.util.ArrayList;

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
	
	
	public void analyze(Player pl, Board board, ArrayList<Field> fields) {
		
		System.out.println(pl.get_name()+" landed on "+name);
		
		switch(type){
		case Go:
			pl.increase_money(400);
			System.out.println(pl.get_name()+" receives $400!");
			System.out.println(pl.get_name()+" now ownes $"+pl.get_money());
			break;
		case Parking:
			pl.increase_money(value);
			System.out.println(pl.get_name()+" receives $"+value+" from "+name);
			System.out.println(pl.get_name()+" now ownes $"+pl.get_money());
			set_value(0);
			break;
		case Jail:
			System.out.println("... but he is only visiting.");
			break;
		case ToJail:
			pl.send_to_jail(board, fields);
			break;
		case Chance:
			// TODO implement Chance cards
			break;
		case Quest:
			// TODO implement Quest cards
			break;
		case Tax:
			pl.decrease_money(cost);
			update_free_parking(fields.get(20), cost);
			System.out.println(pl.get_name()+" had to pay $"+cost);
			System.out.println(pl.get_name()+" now ownes $"+pl.get_money());
			System.out.println("Free Parking now holds $"+check_free_parking(fields.get(20)));
			break;
		case Factory:
			// TODO
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
	
	
	public boolean validate_free_parking(Field park) {
		
		if (park.get_name() != "Free-Parking") {
			System.err.println("Free Parking appears to be assigned to the wrong fields ID.");
			return false;
		}
		return true;
	}
	
	
	public int check_free_parking(Field park) {
		
		if (validate_free_parking(park)) {
			return park.get_value();
		} else {
			return -1;
		}
	}
	
	public void update_free_parking(Field park, int diff) {
		
		if (validate_free_parking(park)) {
			park.set_value(park.get_value()+diff);
		}
		
	}
	

}
