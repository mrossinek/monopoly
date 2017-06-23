package monopoly;

public class Dice {

	public static int throw_dice(int total) {
		
		double random = 0.0;
		int dice = 0;
		
		while (total > 0) {
			random = Math.random();  // generate random double between 0.0 and 1.0
			dice += (int) (random * 6) + 1;  // convert into integer between 1 and 6
			total--;
		}
	
		return dice;
	}
	
	
	// simple fairness test of dice	
	public static void test_fairness() {
		
		System.out.println("Test dice for fairness...");
		
		int[] arr = new int[6];  // array to hold number of throws
		int num = 1000000;  // number of dice throws
		
		// throw the dice as many times
		for (int i=1; i<=num; i++) {
			int val = throw_dice(1);
			arr[val-1] += 1;
		}
		
		double[] perc = new double[6];  // array to hold percentages
		double sum = 0.0;
		double ave = 0.0;
		double std = 0.0;

		System.out.println("Results after "+num+" dice throws:");
		System.out.println("Value  Percentage");
		
		for (int i=0; i<6; ++i) {
			perc[i] = (double) arr[i]/num*100;  // calculate percentages
			sum += perc[i];                     // sum of all percentages
			ave += perc[i]/6.;                  // calculate the average
			System.out.println("  "+(i+1)+"    "+perc[i]);
		}
		
		for (int i=0; i<6; ++i) {
			std += Math.pow(perc[i]-ave, 2);
		}

		System.out.println(" Sum:  "+sum);
		System.out.println(" Ave:  "+ave);
		System.out.println(" StD:  "+std);
		
		if (std < 0.05) {
			System.out.println("The dice appears to be working fine!");
		} else {
			System.err.println("The random generator did not succeed.");
			System.err.println("Aborting...");
			System.exit(0);
		}
		
	}
	
}
