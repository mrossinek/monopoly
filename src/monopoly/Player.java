package monopoly;

public class Player {

	public static int count; // total number of players
	public int id;           // player ID: 1,2,...
	public String name;      // name of player
	public int money;        // money the player owns
	public int position;     // current position on board: 0,1,...,39
	
	
	public Player(String playername) {
		
		count++;
		id = count;
		name = playername;
		money = 1500;
		position = 0;
		
	}
	
	public static void InitPlayers() {
		count = 0;
	}
	
	public int GetPosition() {
		return position;
	}
	
	public void SetPosition(int pos) {
		position = pos;
	}
	
	public int GetMoney() {
		return money;
	}
	
	public void IncreaseMoney(int diff) {
		money += diff;
	}
	
	public void DecreaseMoney(int diff) {
		money -= diff;
	}
	
	public void PrintPlayer() {
		System.out.println(id+" :  "+name);
		System.out.println("Position :  "+position);
		System.out.println("Money :  "+money);
	}
	
}
