
public class Matrix {
	private double[][] vals;
	
	public Matrix(double[][] vals) {
		this.vals = vals;
	}
	
	public double getDeterminant() {
		if (vals.length == 1) {
			return vals[0][0];
		}
		
		if (vals.length == 2) {
			return vals[0][0] * vals[1][1] - vals[0][1] * vals[1][0];
		}
		
		if (vals.length == 3) {
			/*
			 * 0,0   0,1   0,2
			 * 1,0   1,1   1,2
			 * 2,0   2,1   2,2
			 */
			
			return
				  vals[0][0] * (vals[1][1] * vals[2][2] - vals[1][2] * vals[2][1])
				- vals[0][1] * (vals[1][0] * vals[2][2] - vals[1][2] * vals[2][0])
				+ vals[0][2] * (vals[1][0] * vals[2][1] - vals[1][1] * vals[2][0]);
		}
		
		return 0; // shouldn't get to here anyways
	}
}
