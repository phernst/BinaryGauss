import java.util.ArrayList;

public class BGauss {
	public static ArrayList<BVector> solve(BSqMatrix A, BVector b) {
		assert A.size() == b.size() : "A and b must have the same number of rows";
		
		ArrayList<BVector> result = new ArrayList<>();
		
		BSqMatrix U = A.deepCopy();
		BVector z = b.deepCopy();
		
		for (int i = 0; i < U.size() - 1; i++) {
			MatVec temp = swapPivot(U, z, i);
			U = temp.mat;
			z = temp.vec;
			for (int j = i + 1; j < U.size(); j++) {
				if (U.get(j, i)) {
					U.setRow(j, U.row(i).xor(U.row(j)));
					z.set(j, z.get(i) ^ z.get(j));
				}
			}
		}
		
		assert U.isUpperMatrix() : "U should be an upper matrix";
		
		BSqMatrix I = U;
		BVector x = z;
		for (int i = I.size() - 1; i > 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				if (I.get(j, i)) {
					I.setRow(j, I.row(i).xor(I.row(j)));
					x.set(j, x.get(i) ^ x.get(j));
				}
			}
		}
		
		
		// check number of solutions
		ArrayList<Integer> zeroRows = new ArrayList<>();
		BVector zeroVec = BVector.zeros(I.size());
		boolean solvable = true;
		for (int i = 0; i < I.size(); i++) {
			if (I.row(i).equals(zeroVec) && x.get(i)) {
				solvable = false;
				break;
			}
			if (I.row(i).equals(zeroVec)) {
				zeroRows.add(i);
			}
		}
		
		// unique solution
		if (solvable && zeroRows.isEmpty()) {
			result.add(x);
		}
		
		// more solutions
		else if (solvable && !zeroRows.isEmpty()) {
			assert zeroRows.size() <= 32 : "Too many free variables";
			for (int i = 0; i < (1 << zeroRows.size()); i++) {
				BVector tempX = x.deepCopy();
				for (int j = 0; j < zeroRows.size(); j++) {
					if (((i >> j) & 1) == 1) {
						BVector col = I.col(zeroRows.get(j));
						col.set(zeroRows.get(j), true);
						tempX = tempX.xor(col);
					}
				}
				result.add(tempX);
			}
		}
		
		return result;
	}
	
	private static class MatVec {
		public BSqMatrix mat;
		public BVector vec;
		public MatVec(BSqMatrix m, BVector v) {
			mat = m;
			vec = v;
		}
	}
	
	private static MatVec swapPivot(BSqMatrix A, BVector b, int i) {
		MatVec result = new MatVec(A, b);
		
		if (!A.get(i, i)) {
			for (int j = i + 1; j < A.size(); j++) {
				if (A.get(j, i)) {
					result.mat = result.mat.swapRows(i, j);
					result.vec = result.vec.swap(i, j);
					break;
				}
			}
		}
		
		return result;
	}
}
