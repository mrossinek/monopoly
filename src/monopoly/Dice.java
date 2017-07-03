/*
 * Dice
 *
 * Max Rossmannek
 */

package monopoly;


public class Dice {

	public static int throwDiceOnce() {
		return (int) (Math.random() * 6) + 1;
	}

	public static int[] throwDiceTwice() {
		int[] values = new int[2];

		for (int i=0; i<2; ++i) {
			values[i] = (int) (Math.random() * 6) + 1;
		}

		return values;
	}


	public static void testFairness() {
		int total = 1000000;
		int[] numbers = new int[6];
		double[] perc = new double[6];
		double sum = 0.0;
		double ave = 0.0;
		double std = 0.0;

		System.out.println("Test dice for fairness...");

		for (int i=1; i<=total; i++) {
			int val = throwDiceOnce();
			numbers[val-1] += 1;
		}

		System.out.println("Results after " + total + " dice throws:");
		System.out.println("Value  Percentage");

		for (int i=0; i<6; ++i) {
			perc[i] = (double) numbers[i]/total*100;
			sum += perc[i];
			ave += perc[i]/6.;
			System.out.println("  "+(i+1)+"    "+perc[i]);
		}

		for (int i=0; i<6; ++i) {
			std += Math.pow(perc[i]-ave, 2);
		}

		System.out.println(" Sum:  " + sum);
		System.out.println(" Ave:  " + ave);
		System.out.println(" StD:  " + std);

		if (std < 0.05) {
			/* standard deviation is lower than 5% */
			System.out.println("The dice appears to be working fine!");
		} else {
			System.err.println("The random generator did not succeed.");
			System.err.println("Aborting...");
			System.exit(0);
		}

	}


}
