/*
 * Field
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;


public class Field {

	private static String fieldsFile;  // name of file the fields are saved in

	private int id;                          // id = position on board: 0,1,...,39
	private int[] coordinates = new int[2];  // row and column coordinates
	private FieldType type;                  // type of field

	private String name;                     // name of field
	private StreetColor color;               // color of field (iff type = street)

	private int value;                       // initial value of field
	private int cost;                        // cost to be paid on field
	private int numberOfHouses;							 // gives number of

	private int ownerID;                     //  0 : field is for sale
	private int playerCount;                 // player count on field


	public Field(String[] args) {

		id = Integer.parseInt(args[0]);
		type = FieldType.valueOf(args[1]);
		coordinates[0] = Integer.parseInt(args[2]);
		coordinates[1] = Integer.parseInt(args[3]);
		playerCount = 0;

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
			System.err.println(type + " is not a valid field type.");
			return;
		}

	}


	public static void setupFields(String filename, ArrayList<Field> ListOfFields) {

		fieldsFile = filename;

		try {
			FileReader inFile = new FileReader(fieldsFile);
			BufferedReader reader = new BufferedReader(inFile);

			String line;

			while ( (line = reader.readLine()) != null) {
				String[] splitLine = line.split("\\s+");
				Field tmpField = new Field(splitLine);
				ListOfFields.add(tmpField);
			}

			reader.close();
			inFile.close();

		} catch (Exception e) {
			System.err.println(e);
		}

	}


	public static void checkFields(ArrayList<Field> fields) {
		System.out.println();
		for (Field f : fields) {
			f.printField();
		}
	}


	public int getId() {
		return id;
	}

	public int[] getCoordinates() {
		return coordinates;
	}

	public FieldType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public StreetColor getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int val) {
		value = val;
	}

	public int getCost() {
		return cost;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int id) {
		ownerID = id;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void increasePlayerCount() {
		playerCount++;
	}

	public void decreasePlayerCount() {
		playerCount--;
	}


	public void printField() {
		System.out.println("Field No. " + id + " :  " + name);
	}


	public void analyze(Player pl, Game game, Scanner in) {

		System.out.println(pl.getName() + " landed on " + name);

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
			toJail(pl, game.board, game.listOfFields);
			break;
		case Chance:
			pickChance(pl, game);
			break;
		case Quest:
			pickQuest(pl, game);
			break;
		case Tax:
			tax(pl, game.listOfFields);
			break;
		case Factory:
			factory(pl, game.listOfPlayers, game.listOfFields, in);
			break;
		case Station:
			station(pl, game.listOfPlayers, game.listOfFields, in);
			break;
		case Street:
			street(pl, game.listOfPlayers, game.listOfFields, in);
			break;
		default:
			System.err.println("An unknown error occured during the move analysis.");
		}

	}


	public void go(Player pl) {
		pl.increaseMoney(400);
		System.out.println(pl.getName() + " receives $400!");
		System.out.println(pl.getName() + " now ownes $" + pl.getMoney());
	}


	public void parking(Player pl) {
		pl.increaseMoney(value);
		System.out.println(pl.getName() + " receives $" + value + " from " + name);
		System.out.println(pl.getName() + " now ownes $" + pl.getMoney());
		setValue(0);
	}


	public boolean validateParking(Field park) {

		if (park.getName() != "Free-Parking") {
			System.err.println("Free Parking appears to be assigned to the wrong fields ID.");
			return false;
		}
		return true;
	}


	public int checkParking(Field park) {

		if (validateParking(park)) {
			return park.getValue();
		} else {
			return -1;
		}
	}


	public void updateParking(Field park, int diff) {

		if (validateParking(park)) {
			park.setValue(park.getValue()+diff);
		}

	}


	public void jail() {
		System.out.println("... but he is only visiting.");
	}


	public void toJail(Player pl, Board board, ArrayList<Field> fields) {
		pl.updateJailCount(3);
		board.removePlayer(pl, fields);
		pl.setPosition(10);
		board.placePlayer(pl, fields);
		System.out.println(pl.getName() + " has been sent to the jail!");
	}


	public void tax(Player pl, ArrayList<Field> fields) {
		pl.decreaseMoney(cost);
		updateParking(fields.get(20), cost);
		System.out.println(pl.getName() + " had to pay $" + cost);
		System.out.println(pl.getName() + " now ownes $" + pl.getMoney());
		System.out.println("Free Parking now holds $"+checkParking(fields.get(20)));
	}


	public void pickChance(Player pl, Game game) {
		System.out.println(pl.getName() + " picked a Chance card:");
		Card.pickCard(pl, game, "Chance");
	}


	public void pickQuest(Player pl, Game game) {
		System.out.println(pl.getName() + " picked a Community Chest card:");
		Card.pickCard(pl, game, "Quest");
	}


	public void factory(Player pl, ArrayList<Player> players, ArrayList<Field> fields, Scanner in) {

		int owner = findOwner();

		if (switchOwner(owner, pl, in, fields)) {
			Player boss = new Player();
			boss = findBoss(owner, players);

			int count = countType(fields, type, boss);
			int diff = 0;

			switch (count) {
			case 1:
				diff = cost*pl.dice;
				System.out.println(boss.getName() + " owns " + count + " Factories.");
				System.out.println("Thus the cost is " + cost + "*" + pl.dice + "=$" + diff);
				transaction(diff, pl, boss);
				break;
			case 2:
				diff = cost*5/2*pl.dice;
				System.out.println(boss.getName() + " owns " + count + " Factories.");
				System.out.println("Thus the cost is " + (cost*5/2) + "*" + pl.dice + "=$" + diff);
				transaction(diff, pl, boss);
				break;
			default:
				System.err.println("There appear to be more than 2 factories on the board");
				return;
			}

		}
	}


	public void station(Player pl, ArrayList<Player> players, ArrayList<Field> fields, Scanner in) {

		int owner = findOwner();

		if (switchOwner(owner, pl, in, fields)) {
			Player boss = new Player();
			boss = findBoss(owner, players);

			int count = countType(fields, type, boss);
						if (count > 4) {
				System.err.println("There appear to be more than 4 stations on the board");
				return;
			}

			int diff = cost;
			for (int i=1; i<count; ++i) {
				diff*=2;
			}
			System.out.println(boss.getName() + " owns " + count + " Stations.");
			System.out.println("Thus the cost is $" + diff);
			transaction(diff, pl, boss);
		}
	}


	public void street(Player pl, ArrayList<Player> players, ArrayList<Field> fields, Scanner in) {

		int owner = findOwner();

		if (switchOwner(owner, pl, in, fields)) {
			Player boss = new Player();
			boss = findBoss(owner, players);

			int diff = cost;
			System.out.println(boss.getName() + " owns " + getName());
			System.out.println("The cost is $" + diff);
			transaction(diff, pl, boss);
		}
	}


	public int findOwner() {
		int owner = getOwnerID();
		if (owner > Player.getCount()) {
			System.err.println("There appears to be an unknown owner of the field " + name);
			return -1;
		}
		return owner;
	}


	public boolean switchOwner(int owner, Player pl, Scanner in, ArrayList<Field> fields) {
		boolean defaultCase = false;

		if (owner == pl.getId()) {
			System.out.println(pl.getName() + " already owns this " + type);
		} else {
			switch (owner) {
			case 0:
				if (checkBuying(pl)) {
					if(chooseBuying(in)) {
						pl.buy(this, fields);
						System.out.println(pl.getName() + " successfully bought " + getName());
						System.out.println(pl.getName() + " now owns $" + pl.getMoney());
					}
				}
				break;
			default:
				defaultCase = true;
				break;
			}
		}

		return defaultCase;
	}


	public Player findBoss(int owner, ArrayList<Player> players) {
		for (Player p : players) {
			if (p.getId() == owner) {
				return p;
			}
		}
		return null;  // owner not found
	}


	public int countType(ArrayList<Field> fields, FieldType type, Player boss) {
		int count = 0;
		for (Field f : fields) {
			if (f.getType() == type) {
				if (f.getOwnerID() == boss.getId()) {
					if (boss.checkOwnage(f.getId())) {
						count++;
					} else {
						System.err.println("There appears to be a conflict of interest in owning " + f.getName());
						return 0;
					}
				}
			}
		}
		return count;
	}


	public boolean checkBuying(Player pl) {
		System.out.println("This " + type.name() + " is still for sale!");
		int value = getValue();
		System.out.println("Its value is : $" + value);
		int money = pl.getMoney();
		System.out.println("He currently owns $" + money);
		if (money < value) {
			System.out.println("He cannot afford to buy this " + type.name());
			return false;
		}
		return true;
	}


	public boolean chooseBuying(Scanner in) {
		boolean buy = false;
		boolean makeValidChoice = false;

		while (!makeValidChoice) {
			System.out.println("Would he like to buy this " + type.name() + "? Y/N");
			char choice = in.next().charAt(0);
			switch (choice) {
			case 'y': case 'Y':
				buy = true;
				makeValidChoice = true;
				break;
			case 'n': case 'N':
				buy = false;
				makeValidChoice = true;
				break;
			default:
				System.err.println("Invalid choice, please try again.");
				break;
			}
		}

		return buy;
	}


	public void transaction(int diff, Player pl, Player boss) {
		pl.decreaseMoney(diff);
		boss.increaseMoney(diff);
		System.out.println(pl.getName() + " now owns $" + pl.getMoney());
		System.out.println(boss.getName() + " now owns $" + boss.getMoney());
	}


}
