/*
 * Player
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.Scanner;
import java.util.ArrayList;


public class Player {


	private static int count;  // total number of players
	private int id;            // player ID: 1,2,...
	private String name;       // name of player
	private int money;         // money the player owns
	private int position;      // current position on board: 0,1,...,39
	private int jailCount;     // counter for jail time
	private int freeJailCard;  // counter of get-out-of-jail cards
	private boolean[] streets; // fields owned by player
	public int dice;           // current dice throw
	public boolean pasch;      // current pasch


	public Player() {
		/* empty constructor */
	}


	public Player(String playername, ArrayList<Player> players) {
		count++;
		id = count;
		name = playername;
		money = 1500;
		position = 0;
		jailCount = 0;
		freeJailCard = 0;
		streets = new boolean[40];  // initializes to false

		players.add(this);
	}


	public static int getCount() {
		return count;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public int getMoney() {
		return money;
	}


	public void increaseMoney(int diff) {
		money += diff;
	}


	public void decreaseMoney(int diff) {
		money -= diff;
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int pos) {
		position = pos;
	}


	public void updatePosition() {
		int new_pos = (position + dice) % 40;

		if (new_pos < position && new_pos != 0) {  // player crossed Go
			increaseMoney(200);
			System.out.println(name+" crossed Go and received $200!");
			System.out.println(name+" now ownes $" + money);
		}

		position = new_pos;
	}


	public int getJailCount() {
		return jailCount;
	}


	public void updateJailCount(int diff) {
		jailCount += diff;
	}


	public int getFreeJailCard() {
		return freeJailCard;
	}

	public void decreaseFreeJailCard() {
		freeJailCard--;
	}

	public void increaseFreeJailCard() {
		freeJailCard++;
	}


	public boolean checkOwnage(int fieldID) {
		return streets[fieldID];
	}


	public void printPlayer() {
		System.out.println();
		System.out.println("Player " + id + " :  " + name);
		System.out.println("Position :  " + position);
		System.out.println("Money    :  $" + money);
	}


	public void sendToJail(Game game) {
		game.listOfFields.get(position).toJail(this, game.board, game.listOfFields);
	}


	public void buy(Field field, ArrayList<Field> fields) {
		int cost = field.getValue();
		int fieldID = field.getId();

		if (money-cost < 0) {
			System.err.println(getName() + " cannot afford to buy " + field.getName());
			return;
		} else {
			decreaseMoney(cost);
		}

		streets[fieldID] = true;

		for (Field f : fields) {
			if (f.getId() == fieldID) {
				f.setOwnerID(id);
			}
		}
	}


	public boolean throwDice(Scanner in) {
		System.out.println();
		System.out.println("Press ENTER to roll the dices");
		in.nextLine();

		int[] diceThrows = Dice.throwDiceTwice();
		dice = diceThrows[0] + diceThrows[1];
		pasch = (diceThrows[0] == diceThrows[1]);

		if (pasch) {
			System.out.println(name + " threw a Pasch of " + (dice/2));
		} else {
			System.out.println(name + " threw a " + dice);
		}

		return pasch;
	}


	public void doTurn(Game game, Scanner in) {

		if (jailCount != 0) {
			if (freeJailCard > 0) {
				System.out.println(name + " used his get-out-of-jail card!");
				updateJailCount(-jailCount);
				decreaseFreeJailCard();
			} else {
				if (pasch) {
					System.out.println("The Pasch released " + name + " out of jail early!");
					updateJailCount(-jailCount);
				} else {
					updateJailCount(-1);
					if (jailCount == 0) {
						System.out.println(name + " has been released from jail regularly.");
						return;
					} else {
						System.out.println(name + " remains in jail!");
						System.out.println("Turns left in jail: " + jailCount);
						return;
					}
				}
			}
		}

		game.board.removePlayer(this, game.listOfFields);
		updatePosition();
		game.board.placePlayer(this, game.listOfFields);
		game.board.printBoard();

		game.listOfFields.get(position).analyze(this, game, in);
		in.nextLine();

	}


}
