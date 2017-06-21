package monopoly;

public class Field {

	public int id;             // id = position on board: 0,1,...,39
	public FieldType type;     // type of field
	
	public String name;
	public StreetColor color;
	
	public int value;
	public int cost;
	public int reward;

	
	public Field(FieldType t, int pos) {
		
		type = t;
		id = pos;
		
	}
	

}
