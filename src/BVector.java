
public class BVector {
	private int mSize;
	private boolean[] mEntries;
	
	private BVector(int n) {
		mSize = n;
		mEntries = new boolean[n];
	}
	
	public BVector(boolean[] b) {
		mEntries = b;
		mSize = b.length;
	}
	
	public static BVector ones(int n) {
		BVector result = new BVector(n);
		for (int i = 0; i < n; i++) {
			result.mEntries[i] = true;
		}
		return result;
	}
	
	public static BVector zeros(int n) {
		return new BVector(n);
	}
	
	public int size() {
		return mSize;
	}
	
	public boolean get(int i) {
		assert i >= 0 && i < mSize : "i is out of the vector bounds";
		return mEntries[i];
	}
	
	public void set(int i, boolean b) {
		assert i >= 0 && i < mSize : "i is out of the vector bounds";
		mEntries[i] = b;
	}
	
	public BVector deepCopy() {
		BVector result = new BVector(mSize);
		for (int i = 0; i < mSize; i++) {
			result.mEntries[i] = mEntries[i];
		}
		return result;
	}
	
	public BVector swap(int i, int j) {
		assert i >= 0 && i < mSize : "i is out of the vector bounds";
		assert j >= 0 && j < mSize : "j is out of the vector bounds";
		BVector result = deepCopy();
		boolean temp = result.mEntries[i];
		result.mEntries[i] = result.mEntries[j];
		result.mEntries[j] = temp;
		return result;
	}
	
	public BVector xor(BVector v) {
		assert mSize == v.mSize : "v must have the same dimension as this";
		BVector result = deepCopy();
		for (int i = 0; i < mSize; i++) {
			result.mEntries[i] ^= v.mEntries[i];
		}
		return result;
	}
	
	public int countTrue() {
		int result = 0;
		for (int i = 0; i < mSize; result += mEntries[i++] ? 1 : 0);
		return result;
	}
	
	@Override
	public String toString() {
		String result = "[";
		for (int i = 0; i < mSize - 1; i++) {
			result += (mEntries[i] ? "1" : "0") + ", ";
		}
		result += (mEntries[mSize - 1] ? "1" : "0") + "]";
		return result;
	}
	
	public boolean equals(BVector v) {
		if (mSize != v.mSize) {
			return false;
		}
		boolean result = true;
		for (int i = 0; i < mSize; i++) {
			if (mEntries[i] != v.mEntries[i]) {
				result = false;
				break;
			}
		}
		return result;
	}

}
