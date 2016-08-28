
public class BGauss {
	public static BVector solve(BSqMatrix A, BVector b) {
		assert A.size() == b.size() : "A and b must have the same dimensions";
		
		BSqMatrix U = A.deepCopy();
		BVector z = b.deepCopy();
		
		for (int i = 0; i < U.size() - 1; i++) {
			MatVec temp = sort(U, z, i);
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
		
		/*for (int i = 0; i < U.size(); i++) {
			if (U.get(i, i)) {
				return null;
			}
		}*/
		
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
		
		assert I.equals(BSqMatrix.eye(I.size())) : "I should be an identity matrix";
		
		return x;
	}
	
	private static class MatVec {
		public BSqMatrix mat;
		public BVector vec;
		public MatVec(BSqMatrix m, BVector v) {
			mat = m;
			vec = v;
		}
	}
	
	private static MatVec sort(BSqMatrix A, BVector b, int i) {
		if (A.get(i, i)) {
			return new MatVec(A, b);
		}
		MatVec result = new MatVec(A.deepCopy(), b.deepCopy());
		for (int j = i + 1; j < A.size(); j++) {
			if (A.get(j, i)) {
				result.mat = result.mat.swapRows(i, j);
				result.vec = result.vec.swap(i, j);
				break;
			}
		}
		return result;
	}
}
