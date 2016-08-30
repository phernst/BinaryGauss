import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		int[][] bMatTest = {
				{1,1,1,0,1,1,1,0,0,0,0,0},
				{1,1,0,0,0,0,0,1,1,1,0,0},
				{1,0,1,1,1,1,1,0,1,1,1,0},
				{0,0,1,1,1,1,1,0,0,1,0,0},
				{1,0,1,1,1,1,1,0,1,0,1,1},
				{1,0,1,1,1,1,1,1,0,1,1,1},
				{1,0,1,1,1,1,1,1,0,0,1,1},
				{0,1,0,0,0,1,1,1,1,0,1,1},
				{0,1,1,0,1,0,0,1,1,0,1,1},
				{0,1,1,1,0,1,0,0,0,1,0,1},
				{0,0,1,0,1,1,1,1,1,0,1,0},
				{0,0,0,0,1,1,1,1,1,1,0,1}
			};
		/*int[][] bMatTest = {
				{1,0,1,0},
				{0,1,0,1},
				{0,0,0,0},
				{0,0,0,0}
			};*/
		
		BSqMatrix A = new BSqMatrix(bMatTest);
		BVector b = BVector.ones(A.size());
		
		assert A.isSymmetric() : "A is not symmetric";
		
		int NUM_TRIES = 100000;
		ArrayList<BVector> x = null;
		long sumDiffTime = 0;
		for (int i = 0; i < NUM_TRIES; i++) {
			long startTime = System.nanoTime();
			x = BGauss.solve(A, b);
			long endTime = System.nanoTime();
			sumDiffTime += endTime - startTime;
		}
		double diffTime = (double)sumDiffTime/NUM_TRIES*Math.pow(10, -9);
		
		System.out.println("Number of solutions: " + x.size());
		System.out.println();
		for (int i = 0; i < x.size(); i++) {
			System.out.println("Solution " + i + ": " + x.get(i));
			System.out.println("Number of positive entries: " + x.get(i).countTrue());
			System.out.println();
		}
		System.out.println("Time to get the solution: " + diffTime);
	}

}
