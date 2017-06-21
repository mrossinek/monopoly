package monopoly;

public class Field {
	
	private int id;                          // id = position on board: 0,1,...,39
	private int[] coordinates = new int[2];  // row and column coordinates
	private FieldType type;                  // type of field
	
	private String name;                     // name of field
	private StreetColor color;               // color of field (only if type street)
	
	private int value;                       // initial value of field
	private int cost;                        // cost to be paid on field
	
	private boolean forsale;                    // is field for sale? 
	
	
	public Field(String[] args) {
		
		id = Integer.parseInt(args[0]);
		type = FieldType.valueOf(args[1]);
		coordinates[0] = Integer.parseInt(args[2]);
		coordinates[1] = Integer.parseInt(args[3]);
		
		switch(type) {
		case Go:
			name = "Go";
			forsale = false;
			break;
		case Parking:
			name = "Free-Parking";
			value = 0;
			forsale = false;
			break;
		case Jail:
			name = "Jail";
			forsale = false;
			break;
		case ToJail:
			name = "Go-To-Jail";
			forsale = false;
			break;
		case Chance:
			name = "Chance";
			forsale = false;
			break;
		case Quest:
			name = "Community-Chest";
			forsale = false;
			break;
		case Tax:
			cost = Integer.parseInt(args[4]);
			name = args[5];
			forsale = false;
			break;
		case Factory:
			value = Integer.parseInt(args[4]);
			name = args[5];
			cost = 4;
			forsale = true;
			break;
		case Station:
			value = Integer.parseInt(args[4]);
			name = args[5];
			cost = 25;
			forsale = true;
			break;
		case Street:
			value = Integer.parseInt(args[4]);
			cost = Integer.parseInt(args[5]);
			color = StreetColor.valueOf(args[6]);
			name = args[7];
			forsale = true;
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
	
	public boolean get_forsale() {
		return forsale;
	}
	
	
	public void print_field() {
		System.out.println("Field No. "+id+" :  "+name);
	}
	

}
