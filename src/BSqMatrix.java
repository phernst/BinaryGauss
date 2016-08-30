
public class BSqMatrix {
	private final int mSize;
	private boolean[][] mEntries;
	
	private BSqMatrix(int n) {
		mSize = n;
		mEntries = new boolean[n][n];
	}
	
	public BSqMatrix(boolean[][] m) {
		for (int i = 0; i < m.length; i++) {
			assert m.length == m[i].length : "m must be a square matrix";
		}
		mSize = m.length;
		mEntries = m;
	}
	
	public BSqMatrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			assert m.length == m[i].length : "m must be a square matrix";
		}
		mSize = m.length;
		mEntries = new boolean[m.length][m.length];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				mEntries[i][j] = m[i][j] != 0;
			}
		}
	}
	
	public static BSqMatrix zeros(int n) {
		return new BSqMatrix(n);
	}
	
	public static BSqMatrix ones(int n) {
		BSqMatrix result = new BSqMatrix(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result.mEntries[i][j] = true;
			}
		}
		return result;
	}
	
	public static BSqMatrix eye(int n) {
		BSqMatrix result = new BSqMatrix(n);
		for (int i = 0; i < n; i++) {
			result.mEntries[i][i] = true;
		}
		return result;
	}
	
	public int size() {
		return mSize;
	}
	
	public BSqMatrix deepCopy() {
		BSqMatrix result = new BSqMatrix(mSize);
		for (int i = 0; i < mSize; i++) {
			for (int j = 0; j< mSize; j++) {
				result.mEntries[i][j] = mEntries[i][j];
			}
		}
		return result;
	}
	
	public BVector row(int i) {
		assert i >= 0 && i < mSize : "i is out of the matrix bounds";
		return new BVector(mEntries[i]);
	}
	
	public void setRow(int i, BVector v) {
		assert mSize == v.size() : "v must have the same dimensions as this";
		assert i >= 0 && i < mSize : "i is out of the matrix bounds";
		for (int j = 0; j < mSize; j++) {
			mEntries[i][j] = v.get(j);
		}
	}
	
	public BVector col(int i) {
		assert i >= 0 && i < mSize : "i is out of the matrix bounds";
		BVector result = BVector.zeros(mSize);
		for (int j = 0; j < mSize; j++) {
			result.set(j, mEntries[j][i]);
		}
		return result;
	}
	
	public boolean get(int i, int j) {
		assert i >= 0 && i < mSize : "i is out of the matrix bounds";
		assert j >= 0 && j < mSize : "j is out of the matrix bounds";
		return mEntries[i][j];
	}
	
	public BSqMatrix swapRows(int i, int j) {
		assert i >= 0 && i < mSize : "i is out of the matrix bounds";
		assert j >= 0 && j < mSize : "j is out of the matrix bounds";
		BSqMatrix result = deepCopy();
		BVector temp = result.row(i).deepCopy();
		result.setRow(i, result.row(j));
		result.setRow(j, temp);
		return result;
	}
	
	public boolean isUpperMatrix() {
		boolean result = true;
		for (int i = 0; i < mSize - 1; i++) {
			for (int j = i + 1; j < mSize; j++) {
				if (mEntries[j][i]) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
	
	public boolean isSymmetric() {
		boolean result = true;
		for (int i = 0; i < mSize; i++) {
			for (int j = i + 1; j < mSize; j++) {
				if (mEntries[i][j] != mEntries[j][i]) {
					result = false;
					break;
				}
			}
			if (!result) {
				break;
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "[";
		for (int i = 0; i < mSize - 1; i++) {
			for (int j = 0; j < mSize - 1; j++) {
				result += (mEntries[i][j] ? "1" : "0") + ", ";
			}
			result += (mEntries[i][mSize - 1] ? "1" : "0") + ",\n ";
		}
		for (int j = 0; j < mSize - 1; j++) {
			result += (mEntries[mSize - 1][j] ? "1" : "0") + ", ";
		}
		result += (mEntries[mSize - 1][mSize - 1] ? "1" : "0") + "]";
		return result;
	}
	
	public boolean equals(BSqMatrix m) {
		if (mSize != m.mSize) {
			return false;
		}
		boolean result = true;
		for (int i = 0; i < mSize; i++) {
			for (int j = 0; j < mSize; j++) {
				if (mEntries[i][j] != m.mEntries[i][j]) {
					result = false;
					break;
				}
			}
			if (!result) {
				break;
			}
		}
		return result;
	}
}
