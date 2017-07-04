/*
 * Main
 *
 * Max Rossmannek
 */

package monopoly;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {

    if (args.length != 1) {
      System.err.println("Usage: java monopoly.Main ../data/[language code]/setup_[language code].txt");
      System.out.println("The parameter file has to include the following lines:");
      System.out.println(" 1  // descriptive comment");
      System.out.println(" 2  language code");
      System.out.println(" 3  path to board file");
      System.out.println(" 4  path to fields file");
      System.out.println(" 5  path to chance cards file");
      System.out.println(" 6  path to community chest cards file");
      return;
    }

    Scanner scanner = new Scanner(System.in);

    welcome();

    Game game = new Game(args[0], scanner);

    for (int i=0; i<10; ++i) {
      Card.pickCard(game.listOfPlayers.get(0), game, "Chance");
      Card.pickCard(game.listOfPlayers.get(0), game, "Quest");
    }

    /*
    System.out.println("Let's start!");
    scanner.nextLine();

    System.out.println();

    while (game.running) {

      for (Player pl : game.listOfPlayers) {

        System.out.println();
        System.out.println(pl.getName() + "'s turn");

        int paschCount = 0;
        boolean pasch = false;

        do {
          pasch = pl.throwDice(scanner);

          if (pasch) {
            paschCount++;

            if (paschCount == 3) {
              System.out.println(pl.getName() + " threw 3 Pasch in a row!");
              pl.sendToJail(game);
              break;
            }
          }
          pl.doTurn(game, scanner);
        } while (pasch);

        if (pl.getMoney() < 0) {
          System.out.println(pl.getName() + " went bankrupt!");
          game.running = false;
        } else {
          System.out.println("Press ENTER to end your turn");
          scanner.nextLine();
        }

      }

    }
    */


  }

  public static void welcome() {
		System.out.println();
		System.out.println("Welcome to ");
		System.out.println();
		System.out.println("  M O N O P O L Y  ");
		System.out.println();
		System.out.println();
	}

}
