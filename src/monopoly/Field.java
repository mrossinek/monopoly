package monopoly;

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
	
	
	public void analyze(Player pl) {
		
		System.out.println("Player "+pl.get_name()+" landed on "+name);
		
		switch(type){
		case Go:
			pl.increase_money(400);
			break;
		case Parking:
			pl.increase_money(value);
			value = 0;
			break;
		case Jail:
			break;
		case ToJail:
			pl.send_to_jail();
			break;
		case Chance:
			// TODO implement Chance cards
			break;
		case Quest:
			// TODO implement Quest cards
			break;
		case Tax:
			pl.decrease_money(cost);
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
	

}
