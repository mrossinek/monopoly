package monopoly;

public class Dice {

	public static int throw_dice() {
		
		double random = Math.random();  // generate random double between 0.0 and 1.0
		int dice = (int) (random * 6) + 1;  // convert into integer between 1 and 6
	
		return dice;
	}
	
	
	// simple fairness test of dice	
	public static void test_fairness() {
		
		System.out.println("Test dice for fairness...");
		
		int[] arr = new int[6];  // array to hold number of throws
		int num = 1000000;  // number of dice throws
		
		// throw the dice as many times
		for (int i=1; i<=num; i++) {
			int val = throw_dice();
			arr[val-1] += 1;
		}
		
		double[] perc = new double[6];  // array to hold percentages
		double sum = 0.0;

		System.out.println("Results after "+num+" dice throws:");
		System.out.println("Value  Percentage");
		
		for (int i=0; i<6; ++i) {
			perc[i] = (double) arr[i]/num*100;  // calculate percentages
			sum += perc[i];  // sum of all percentages
			System.out.println("  "+(i+1)+"    "+perc[i]);
		}

		System.out.println(" Sum:  "+sum);
		
	}
	
}
