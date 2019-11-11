
public class EquationSolver {
	public static Vector solve(double[][] m, double[] res) {
		Matrix mat = new Matrix(m);
		Vector vec = new Vector(res);
		
		return mat.inv(vec);
		/*double det = mat.getDeterminant();
		
		if (res.length == 1) {
			return new double[] {res[0] / det};
		}
		
		if (res.length == 2) {
			return new double[] {
				new Matrix(new double[][] {
					new double[] {res[0], m[0][1]},
					new double[] {res[1], m[1][1]}
				}).getDeterminant() / det,
				new Matrix(new double[][] {
					new double[] {m[0][0], res[0]},
					new double[] {m[1][0], res[1]}
				}).getDeterminant() / det
			};
		}
		
		if (res.length == 3) {
			return new double[] {
				new Matrix(new double[][] {
					new double[] {res[0], m[0][1], m[0][2]},
					new double[] {res[1], m[1][1], m[1][2]},
					new double[] {res[2], m[2][1], m[2][2]}
				}).getDeterminant() / det,
				new Matrix(new double[][] {
					new double[] {m[0][0], res[0], m[0][2]},
					new double[] {m[1][0], res[1], m[1][2]},
					new double[] {m[2][0], res[2], m[2][2]}
				}).getDeterminant() / det,
				new Matrix(new double[][] {
					new double[] {m[0][0], m[0][1], res[0]},
					new double[] {m[1][0], m[1][1], res[1]},
					new double[] {m[2][0], m[2][1], res[2]}
				}).getDeterminant() / det
			};
		}
		
		return new double[] {};*/
	}
}
