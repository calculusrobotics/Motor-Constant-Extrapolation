public class Vector {
	private final double[] ENTRIES;
	private final int DIM;
	
	
	
	public Vector(double[] entries) {
		ENTRIES = entries;
		DIM = entries.length;
	}
	
	public Vector(Vector v) {
		DIM = v.getDimension();
		ENTRIES = new double[DIM];
		
		for (int i = 0; i < ENTRIES.length; i++) {
			ENTRIES[i] = v.get(i);
		}
	}
	
	
	
	public double get(int i) {
		return ENTRIES[i];
	}
	
	public void set(int i, double val) {
		ENTRIES[i] = val;
	}
	
	public int getDimension() {
		return DIM;
	}
	
	public Vector multiply(double c) {
		double[] entries = new double[ENTRIES.length];
		
		for (int i = 0; i < ENTRIES.length; i++) {
			entries[i] = ENTRIES[i] * c;
		}
		
		return new Vector(entries);
	}
	
	public void multiply_modify(double c) {
		for (int i = 0; i < ENTRIES.length; i++) {
			ENTRIES[i] *= c;
		}
	}
	
	public Vector divide(double c) {
		return multiply(1/c);
	}
	
	public void divide_modify(double c) {
		multiply_modify(1/c);
	}
	
	public Vector add(Vector v) {
		if (DIM != v.getDimension()) {
			return null;
		}
		
		double[] entries = new double[ENTRIES.length];
		
		for (int i = 0; i < ENTRIES.length; i++) {
			entries[i] = ENTRIES[i] + v.get(i);
		}
		
		return new Vector(entries);
	}
	
	public double getAverage() {
		double sum = 0;
		
		for (int i = 0; i < ENTRIES.length; i++) {
			sum += ENTRIES[i];
		}
		
		return sum / DIM;
	}
	
	public void add_modify(Vector v) {
		if (DIM != v.getDimension()) {
			return;
		}
		
		for (int i = 0; i < ENTRIES.length; i++) {
			ENTRIES[i] += v.get(i);
		}
	}
	
	public String toString() {
		String ret = "";
		
		for (int i = 0; i < DIM; i++) {
			ret += ENTRIES[i] + "   ";
		}
		
		return ret;
	}
}
