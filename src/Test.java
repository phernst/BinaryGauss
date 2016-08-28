
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
		BSqMatrix A = new BSqMatrix(bMatTest);
		BVector b = BVector.ones(A.size());
		
		assert A.isSymmetric() : "A is not symmetric";
		
		int NUM_TRIES = 100000;
		BVector x = null;
		long sumDiffTime = 0;
		for (int i = 0; i < NUM_TRIES; i++) {
			long startTime = System.nanoTime();
			x = BGauss.solve(A, b);
			long endTime = System.nanoTime();
			sumDiffTime += endTime - startTime;
		}
		double diffTime = (double)sumDiffTime/NUM_TRIES*Math.pow(10, -9);
		
		System.out.println("Solution: " + x);
		System.out.println("Number of switches: " + x.countTrue());
		System.out.println("Time to get the solution: " + diffTime);
	}

}
