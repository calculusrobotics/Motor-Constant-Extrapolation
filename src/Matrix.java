
public class Matrix {
	private final Vector[] VALS;
	private boolean isSquare;
	private boolean isRect;
	
	public Matrix(double[][] vals) {
		VALS = new Vector[vals.length];
		isSquare = true;
		isRect = true;
		
		for (int i = 0; i < vals.length; i++) {
			if (vals[i].length != vals.length) {
				isSquare = false;
			}
			
			if (i != 0) {
				if (vals[i].length != vals[i - 1].length) {
					isRect = false;
				}
			}
			
			VALS[i] = new Vector(vals[i]);
		}
	}
	
	public Matrix(Matrix m) {
		VALS = new Vector[m.getRows()];
		for (int i = 0; i < m.getRows(); i++) {
			VALS[i] = new Vector(m.getRow(i)); // copy vectors
		}
		
		isSquare = m.isSquare();
		isRect = m.isRectangular();
	}
	
	
	
	public boolean isSquare() {
		return isSquare;
	}
	
	public boolean isRectangular() {
		return isRect;
	}
	
	public void set(int row, int col, double val) {
		VALS[row].set(col, val);
	}
	
	public void set(int row, Vector rowVec) {
		VALS[row] = rowVec;
		
		if (rowVec.getDimension() != VALS.length) {
			isSquare = false;
		}
		
		if (row != 0) {
			if (rowVec.getDimension() != VALS[row - 1].getDimension()) {
				isRect = false;
			}
		}
	}
	
	public int getRows() {
		return VALS.length;
	}
	
	public Vector getRow(int row) {
		return VALS[row];
	}
	
	public double get(int row, int col) {
		return VALS[row].get(col);
	}
	
	public double getDeterminant() {
		if (!isSquare) {
			return 0;
		}
		
		double det = 1;
		
		int rows = VALS.length;
		
		Matrix ret = new Matrix(this); // copy the matrix
		
		int nextPivotPoint = -1;
		for (int i = 0; i < rows; i++) {
			if (get(i, 0) != 0) {
				nextPivotPoint = i;
				break;
			}
		}
		
		// loop through rows
		for (int i = 0; i < rows - 1; i++) {
			if (nextPivotPoint == -1) {
				return 0;
			} else if (nextPivotPoint != i) {
				det *= -1;

				Vector row = ret.getRow(nextPivotPoint);
				ret.set(nextPivotPoint, ret.getRow(i));
				ret.set(i, row);
			}
			
			Vector pivotRow = ret.getRow(i);
			double pivot = ret.get(i, i);
			det *= pivot;
			
			nextPivotPoint = -1;
			
			for (int k = i + 1; k < rows; k++) {
				ret.getRow(k).add_modify(pivotRow.multiply(-ret.get(k, i)/pivot));
				
				if (ret.get(k, i + 1) != 0 && nextPivotPoint == -1) {
					nextPivotPoint = k;
				}
			}
		}
		
		return det * ret.get(rows - 1, rows - 1);
	}
	
	public Vector inv(Vector v) {
		if (!isSquare) {
			return null;
		}
		
		Vector ret = new Vector(v);
		Matrix m = new Matrix(this);
		
		
		
		int rows = VALS.length;
		
		int nextPivotPoint = -1;
		for (int i = 0; i < rows; i++) {
			if (get(i, 0) != 0) {
				nextPivotPoint = i;
				break;
			}
		}
		
		// put into row echelon
		// loop through rows
		for (int i = 0; i < rows - 1; i++) {
			if (nextPivotPoint == -1) {
				return null;
			} else if (nextPivotPoint != i) {
				double entry = ret.get(nextPivotPoint);
				ret.set(nextPivotPoint, ret.get(i));
				ret.set(i, entry);
				
				Vector row = m.getRow(nextPivotPoint);
				m.set(nextPivotPoint, m.getRow(i));
				m.set(i, row);
			}
			
			Vector pivotRow = m.getRow(i);
			double pivot = m.get(i, i);
			
			nextPivotPoint = -1;
			
			for (int k = i + 1; k < rows; k++) {
				ret.set(k, ret.get(k) - ret.get(i)*m.get(k, i)/pivot);
				m.getRow(k).add_modify(pivotRow.multiply(-m.get(k, i)/pivot));
				
				if (m.get(k, i + 1) != 0 && nextPivotPoint == -1) {
					nextPivotPoint = k;
				}
			}
		}
		
		// augmented matrix now in row echelon
		
		// put matrix in diagonal form
		for (int i = rows - 1; i >= 0; i--) {
			// cols in consideration are j=i+1 to rows + 1 (augmented)
			// for these, add -(i,j)/(j,j)*row j
			// don't really need echelon anymore, just diagonal and vector so ignore above diagonal
			// though technically abuse of notation
			for (int j = i + 1; j < rows; j++) {
				ret.set(i, ret.get(i) - m.get(i, j)*ret.get(j)/m.get(j, j));
			}
		}
		
		for (int i = 0; i < rows; i++) {
			ret.set(i, ret.get(i)/m.get(i, i));
		}
		
		return ret;
	}
	
	public String toString() {
		String ret = "";
		
		for (int i = 0; i < VALS.length; i++) {
			for (int j = 0; j < VALS[i].getDimension(); j++) {
				ret += get(i, j) + "   ";
			}
			ret += "\n";
		}
		
		return ret;
	}
}
